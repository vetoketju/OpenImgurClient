package com.villevalta.imgur.data.datasource

import com.villevalta.imgur.model.ListFilter
import com.villevalta.imgur.model.Post
import com.villevalta.imgur.utils.Coroutines
import com.villevalta.imgur.utils.Status

class GalleryDataSource(private val listFilter: ListFilter) : BaseDataSource<Post>() {

  override fun loadInitial(
    params: LoadInitialParams<Int>,
    callback: LoadInitialCallback<Int, Post>
  ) {
    loadPage(1) { key, posts ->
      callback.onResult(posts, 1, key)
    }
  }

  override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Post>) {
    loadPage(params.key) { key, posts ->
      callback.onResult(posts, key)
    }
  }

  private fun loadPage(page: Int, callback: (Int, List<Post>) -> Unit) {
    status.postValue(Status.LOADING)
    Coroutines.io {
      try {
        val result = service.getPosts(
          listFilter = listFilter,
          page = page,
          showViral = listFilter.showViral,
          mature = listFilter.mature,
          albumPreviews = listFilter.albumPreviews
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

class GalleryDataSourceFactory(private val filter: ListFilter) : BaseDataSourceFactory<Post>() {
  override fun onCreate(): BaseDataSource<Post> = GalleryDataSource(filter)
}