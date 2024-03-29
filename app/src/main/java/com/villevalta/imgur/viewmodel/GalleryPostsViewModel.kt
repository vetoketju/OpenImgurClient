package com.villevalta.imgur.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.villevalta.imgur.data.datasource.PostAdapterItemMapFunction
import com.villevalta.imgur.data.repository.GalleryListRepository
import com.villevalta.imgur.model.ListFilter

class GalleryPostsViewModel(app: Application) : AndroidViewModel(app) {

  private val postsRepo: GalleryListRepository by lazy {
    GalleryListRepository.getInstance()
  }

  val params = MutableLiveData<ListFilter>(null)

  val postsResult = Transformations.switchMap(params) { filter ->
    filter?.let { it ->
      postsRepo.getPosts(it)
    }
  }

  val postsResultMapped = Transformations.switchMap(params) { filter ->
    filter?.let {
      postsRepo.getPostsMappedForAdapter(filter, PostAdapterItemMapFunction())
    }
  }

}