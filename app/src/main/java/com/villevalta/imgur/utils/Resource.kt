package com.villevalta.imgur.utils

import androidx.lifecycle.LiveData
import androidx.paging.PagedList

open class Resource<out T, E>(val status: Status, val data: T?, val error: E?) {

  companion object {

    fun <T, E> success(data: T? = null): Resource<T, E> {
      return Resource(
        Status.SUCCESS,
        data,
        null
      )
    }

    fun <T, E> error(err: E?, data: T? = null): Resource<T, E> {
      return Resource(
        Status.ERROR,
        data,
        err
      )
    }

    fun <T, E> loading(data: T? = null): Resource<T, E> {
      return Resource(
        Status.LOADING,
        data,
        null
      )
    }

    fun <T, E> from(data: T? = null, err: E?): Resource<T, E> {
      return Resource(
        if (err == null) Status.SUCCESS else Status.ERROR,
        data,
        err
      )
    }

  }

  fun isLoading() = status == Status.LOADING
  fun isError() = status == Status.ERROR
  fun isSuccess() = status == Status.SUCCESS

}

enum class Status {
  SUCCESS,
  ERROR,
  LOADING
}

class LiveListResource<T>(
  val status: LiveData<Status>,
  val items: LiveData<PagedList<T>>
)