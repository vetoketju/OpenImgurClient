package com.villevalta.imgur.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.villevalta.imgur.data.repository.TagsRepository
import com.villevalta.imgur.model.Tag
import com.villevalta.imgur.utils.Resource

class TagsViewModel(application: Application) : AndroidViewModel(application) {

  // https://proandroiddev.com/5-common-mistakes-when-using-architecture-components-403e9899f4cb

  private val requestReload = MutableLiveData(false)

  val tagsRepo: TagsRepository by lazy {
    TagsRepository.getInstance()
  }

  val tags: LiveData<Resource<List<Tag>, Throwable>> =
    Transformations.switchMap(requestReload) { force ->
      tagsRepo.getTags(force)
    }

  fun updateTags() {
    requestReload.value = true
  }

}


