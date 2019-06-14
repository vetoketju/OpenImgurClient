package com.villevalta.imgur.data.datasource

import androidx.lifecycle.MutableLiveData
import com.villevalta.imgur.model.ListFilter
import com.villevalta.imgur.model.Post
import com.villevalta.imgur.model.Tag
import com.villevalta.imgur.ui.list.adaptermodels.AdapterItem
import com.villevalta.imgur.utils.Coroutines
import com.villevalta.imgur.utils.Status

class TagDataSource(private val tagName: String, private val listFilter: ListFilter) :
  BaseDataSource<Post>() {

  val liveTag = MutableLiveData<Tag>()

  override fun loadInitial(
    params: LoadInitialParams<Int>,
    callback: LoadInitialCallback<Int, Post>
  ) {
    loadPage(1) { key, tag ->
      liveTag.postValue(tag)
      callback.onResult(tag.items ?: emptyList(), 1, key)
    }
  }

  override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Post>) {
    loadPage(params.key) { key, tag ->
      callback.onResult(tag.items ?: emptyList(), key)
    }
  }

  private fun loadPage(page: Int, callback: (Int, Tag) -> Unit) {
    status.postValue(Status.LOADING)
    Coroutines.io {
      try {
        val result = service.getTagPosts(
          tagName = tagName,
          listFilter = listFilter,
          page = page
        ).await()
        callback.invoke(page + 1, result.data)
        status.postValue(Status.SUCCESS)
      } catch (e: Throwable) {
        error.postValue(e)
        status.postValue(Status.ERROR)
      }
    }
  }

}

class TagDataSourceFactory(private val tagName: String, private val listFilter: ListFilter) :
  BaseDataSourceFactory<Post>() {
  override fun onCreate() = TagDataSource(tagName, listFilter)
}

class AdapterItemTagDataSourceFactory(
  private val tagName: String,
  private val listFilter: ListFilter,
  mapFunc: AdapterItemMapFunction<Post>
) :
  MappableBaseDataSourceFactory<Post, AdapterItem<Post>>(mapFunc) {
  override fun onCreate() = TagDataSource(tagName, listFilter)
}