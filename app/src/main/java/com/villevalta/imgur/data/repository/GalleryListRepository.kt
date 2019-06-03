package com.villevalta.imgur.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.PagedList
import com.villevalta.imgur.data.datasource.GalleryDataSourceFactory
import com.villevalta.imgur.model.ListFilter
import com.villevalta.imgur.model.Post
import com.villevalta.imgur.utils.LiveDatas
import com.villevalta.imgur.utils.Resource

class GalleryListRepository : BaseRepository() {

  fun getPosts(filter: ListFilter): LiveData<Resource<PagedList<Post>, Throwable>?> {
    val factory = GalleryDataSourceFactory(filter)

    val liveList = factory.liveData

    val liveStatus = Transformations.switchMap(factory.sourceLiveData) {
      it.status
    }

    val liveError = Transformations.switchMap(factory.sourceLiveData) {
      it.error
    }

    return LiveDatas.mapMultiple(
      liveList,
      liveStatus,
      liveError
    ) {
      liveStatus.value?.let {
        Resource(it, liveList.value, liveError.value)
      }
    }
  }

  companion object {
    @Volatile
    private var instance: GalleryListRepository? = null

    fun getInstance() = instance ?: synchronized(this) {
      instance ?: GalleryListRepository().also {
        instance = it
      }
    }
  }
}