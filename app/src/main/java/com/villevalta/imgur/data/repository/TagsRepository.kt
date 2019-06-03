package com.villevalta.imgur.data.repository

import androidx.lifecycle.LiveData
import com.villevalta.imgur.data.api.TagsResponse
import com.villevalta.imgur.data.api.WrappedResponse
import com.villevalta.imgur.data.db.AppDatabase
import com.villevalta.imgur.data.db.TagsDao
import com.villevalta.imgur.model.Tag
import com.villevalta.imgur.utils.NetworkBoundResource
import com.villevalta.imgur.utils.Resource

class TagsRepository : BaseRepository() {

  val tagsDao: TagsDao by lazy {
    AppDatabase.getInstance().tagsDao()
  }

  fun getTags(forceUpdate: Boolean = false): LiveData<Resource<List<Tag>, Throwable>> {
    return object : NetworkBoundResource<List<Tag>, TagsResponse>() {

      override fun saveCallResult(item: TagsResponse) {
        tagsDao.insertAll(item.tags)
      }

      // TODO: Fetch if cache is too old (10min?, 1h?, 1d?)
      override fun shouldFetch(data: List<Tag>?): Boolean = data.isNullOrEmpty() || forceUpdate

      override fun loadFromDb(): LiveData<List<Tag>> = tagsDao.getAll()

      override fun createCall(): WrappedResponse<TagsResponse> = service.getTags()

    }.asLiveData()
  }

  companion object {

    @Volatile
    private var instance: TagsRepository? = null

    fun getInstance() = instance ?: synchronized(this) {
      instance ?: TagsRepository().also {
        instance = it
      }
    }
  }

}