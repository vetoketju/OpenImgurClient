package com.villevalta.imgur.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.PagedList
import com.villevalta.imgur.data.datasource.*
import com.villevalta.imgur.model.ListFilter
import com.villevalta.imgur.model.Post
import com.villevalta.imgur.model.Tag
import com.villevalta.imgur.ui.list.adaptermodels.AdapterItem
import com.villevalta.imgur.utils.LiveDatas
import com.villevalta.imgur.utils.Resource
import com.villevalta.imgur.utils.switchMap

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

  fun getPostsMappedForAdapter(
    filter: ListFilter,
    mapper: AdapterItemMapFunction<Post>
  ): LiveData<Resource<PagedList<AdapterItem<Post>>, Throwable>?> {
    val factory = AdapterItemGalleryDataSourceFactory(filter, mapper)

    val liveList = factory.liveData

    val liveStatus = factory.sourceLiveData.switchMap {
      it.status
    }

    val liveError = factory.sourceLiveData.switchMap {
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

  fun getTagPosts(
    tagName: String,
    filter: ListFilter,
    mapper: AdapterItemMapFunction<Post>
  ): LiveData<Resource<Pair<Tag, PagedList<AdapterItem<Post>>?>, Throwable>?> {
    val factory = AdapterItemTagDataSourceFactory(tagName, filter, mapper)
    val liveList = factory.liveData

    val liveStatus = factory.sourceLiveData.switchMap {
      it.status
    }

    val liveError = factory.sourceLiveData.switchMap {
      it.error
    }

    val liveTag = factory.sourceLiveData.switchMap {
      (it as TagDataSource).liveTag
    }

    return LiveDatas.mapMultiple(
      liveList,
      liveStatus,
      liveError,
      liveTag
    ) {
      liveStatus.value?.let {
        val pair = if (
          liveTag.value != null && liveList.value != null
        ) {
          liveTag.value!! to liveList.value!!
        } else null

        Resource(it, pair, liveError.value)
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