package com.villevalta.imgur.utils

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.villevalta.imgur.data.api.ApiResponse
import kotlinx.coroutines.Deferred
import kotlin.math.absoluteValue

fun Context.toastError(errorMessage: String) {
  // TODO: Make it a snackbar
  // TODO: Use custom style
  Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
}

fun Int.toUnitString(): String {
  val value =  when (this.absoluteValue) {
    in 1_000 .. 9999 -> "${"%.1f".format(this / 1000f)} k"
    in 1_000_000 .. 9999999 -> "${"%.2f".format(this / 1_000_000f)} m"
    in 1_000_000_000 .. 9999999999 -> "${"%.3f".format(this / 1_000_000_000f)} b"
    else -> this.toString()
  }

  return (if (this < 0) "-" else "") + value
}

fun <T> Deferred<ApiResponse<T>>.toLiveDataResource(): LiveData<Resource<T, Throwable>> {
  val result = MutableLiveData<Resource<T, Throwable>>(Resource.loading(null))
  Coroutines.ioThenMain({
    try {
      Resource.success<T, Throwable>(await().data)
    } catch (e: Throwable) {
      Resource.error<T, Throwable>(e)
    }
  }, {
    result.value = it
  })
  return result
}