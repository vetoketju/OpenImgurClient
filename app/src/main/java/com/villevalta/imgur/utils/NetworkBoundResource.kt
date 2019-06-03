/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.villevalta.imgur.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.villevalta.imgur.data.api.WrappedResponse

/*
* Referenced from https://developer.android.com/jetpack/docs/guide
* Modified from https://github.com/googlesamples/android-architecture-components/blob/master/GithubBrowserSample/app/src/main/java/com/android/example/github/repository/NetworkBoundResource.kt
* */

/**
 * A generic class that can provide a resource backed by both the sqlite database and the network.
 *
 *
 * You can read more about it in the [Architecture
 * Guide](https://developer.android.com/arch).
 * @param <ResultType>
 * @param <RequestType>
</RequestType></ResultType> */
abstract class NetworkBoundResource<ResultType, RequestType> {

  private val result = MediatorLiveData<Resource<ResultType, Throwable>>()

  init {
    result.value = Resource.loading()
    val dbSource = loadFromDb()
    result.addSource(dbSource) { data ->
      result.removeSource(dbSource)
      if (shouldFetch(data)) {
        fetchFromNetwork(dbSource)
      } else {
        result.addSource(dbSource) { newData ->
          setValue(Resource.success(newData))
        }
      }
    }
  }

  private fun setValue(newValue: Resource<ResultType, Throwable>) {
    if (result.value != newValue) {
      result.value = newValue
    }
  }

  private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
    val netResponse = createCall().toLiveDataResource()
    // we re-attach dbSource as a new source, it will dispatch its latest value quickly
    result.addSource(dbSource) { newData ->
      setValue(Resource.loading(newData))
    }
    result.addSource(netResponse) { networkResponse ->
      if(networkResponse.isLoading()) return@addSource
      result.removeSource(netResponse)
      result.removeSource(dbSource)

      if (networkResponse.isSuccess()) {
        Coroutines.ioThenMain({
          saveCallResult(networkResponse.data!!)
        }, {
          result.addSource(loadFromDb()) { newData ->
            setValue(Resource.success(newData))
          }
        })
      } else {
        onFetchFailed()
        result.addSource(dbSource) { newData ->
          setValue(Resource.error(networkResponse.error, newData))
        }
      }

    }
  }

  protected open fun onFetchFailed() {}

  fun asLiveData() = result as LiveData<Resource<ResultType, Throwable>>

  protected abstract fun saveCallResult(item: RequestType)

  protected abstract fun shouldFetch(data: ResultType?): Boolean

  protected abstract fun loadFromDb(): LiveData<ResultType>

  protected abstract fun createCall(): WrappedResponse<RequestType>
}