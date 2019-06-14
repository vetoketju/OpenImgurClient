package com.villevalta.imgur.data.datasource

import androidx.arch.core.util.Function
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import com.villevalta.imgur.Constants
import com.villevalta.imgur.data.api.ImgurApi
import com.villevalta.imgur.data.api.ImgurApiClient
import com.villevalta.imgur.ui.list.adaptermodels.AdapterItem
import com.villevalta.imgur.utils.Status

abstract class BaseDataSource<T> : PageKeyedDataSource<Int, T>() {

  override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
    /* no-op */
  }

  val status = MutableLiveData<Status>()
  val error = MutableLiveData<Throwable>()

  val service: ImgurApi by lazy {
    ImgurApiClient.getInstance().service
  }

}

abstract class BaseDataSourceFactory<T> : DataSource.Factory<Int, T>() {
  val sourceLiveData = MutableLiveData<BaseDataSource<T>>()

  val liveData by lazy {
    LivePagedListBuilder<Int, T>(
      this, Constants.API_DEFAULT_PAGE_SIZE
    ).setInitialLoadKey(1).build()
  }

  abstract fun onCreate(): BaseDataSource<T>

  override fun create(): DataSource<Int, T> {
    return onCreate().also { sourceLiveData.postValue(it) }
  }

}

abstract class MappableBaseDataSourceFactory<T, W>(private val mapFunction: Function<T, W>) :
  DataSource.Factory<Int, W>() {
  val sourceLiveData = MutableLiveData<BaseDataSource<T>>()

  val liveData by lazy {
    LivePagedListBuilder<Int, W>(
      this, Constants.API_DEFAULT_PAGE_SIZE
    ).setInitialLoadKey(1).build()
  }

  abstract fun onCreate(): BaseDataSource<T>

  override fun create(): DataSource<Int, W> {
    return onCreate().also { sourceLiveData.postValue(it) }.map(mapFunction)
  }
}

abstract class AdapterItemMapFunction<T> : Function<T, AdapterItem<T>>