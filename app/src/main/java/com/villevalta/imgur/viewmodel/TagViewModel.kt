package com.villevalta.imgur.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.villevalta.imgur.data.datasource.PostAdapterItemMapFunction
import com.villevalta.imgur.data.repository.GalleryListRepository
import com.villevalta.imgur.model.ListFilter
import com.villevalta.imgur.ui.viewmodel.TagModel
import com.villevalta.imgur.utils.LiveDatas
import com.villevalta.imgur.utils.switchMap

class TagViewModel(application: Application) : AndroidViewModel(application) {

  private val postsRepo: GalleryListRepository by lazy {
    GalleryListRepository.getInstance()
  }

  val tagModel = MutableLiveData<TagModel>()

  val tagParams = MutableLiveData<Pair<String, ListFilter>>()

  val tagName = LiveDatas.mapMultiple(
    tagModel, tagParams
  ) {
    return@mapMultiple tagModel.value?.title ?: tagParams.value?.first ?: ""
  }

  val tagPosts = tagParams.switchMap {
    postsRepo.getTagPosts(it.first, it.second, PostAdapterItemMapFunction())
  }

}