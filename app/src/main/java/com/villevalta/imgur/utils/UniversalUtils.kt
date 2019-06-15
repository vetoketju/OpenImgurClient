package com.villevalta.imgur.utils

import androidx.lifecycle.*
import kotlinx.coroutines.*

/**
 * Universal utilities for architecture components and kotlin
 */

object Coroutines {

  fun <T : Any> io(doWork: suspend (() -> T?)): Job =
    CoroutineScope(Dispatchers.IO).launch {
      doWork()
    }

  fun <T : Any> main(doWork: suspend (() -> T?)): Job =
    CoroutineScope(Dispatchers.Main).launch {
      doWork()
    }

  fun <T : Any> ioThenMain(doWork: suspend (() -> T?), callback: ((T?) -> Unit)? = null): Job =
    CoroutineScope(Dispatchers.Main).launch {
      val data = CoroutineScope(Dispatchers.IO).async {
        return@async doWork()
      }.await()
      callback?.let {
        it(data)
      }
    }
}

fun <T : Any> Deferred<T>.toLiveData(): LiveData<T> {
  val result = MutableLiveData<T>()
  Coroutines.ioThenMain({
    await()
  }, {
    result.value = it
  })
  return result
}

/*
* LIVEDATA
* */

fun <IN, OUT> LiveData<IN>.map(function: (IN) -> OUT): LiveData<OUT> =
  Transformations.map(this, function)

fun <IN, OUT> LiveData<IN>.switchMap(function: (IN) -> LiveData<OUT>): LiveData<OUT> {
  return Transformations.switchMap(this, function)
}

fun MediatorLiveData<*>.addSources(vararg sources: LiveData<*>, observer: Observer<Any>) {
  addSources(sources, observer)
}

@JvmName("addSourcesArray")
fun MediatorLiveData<*>.addSources(sources: Array<out LiveData<*>>, observer: Observer<Any>) {
  sources.forEach {
    addSource(it as LiveData<Any>, observer)
  }
}

object LiveDatas {
  inline fun <T> mapMultiple(vararg liveDatas: LiveData<*>, crossinline f: () -> T): LiveData<T> {
    return object : MediatorLiveData<T>(), Observer<Any> {
      init {
        addSources(liveDatas, this)
      }

      override fun onChanged(t: Any?) {
        value = f()
      }
    }
  }
}

/*
*  KOTLIN
* */

inline fun <T, R> T.tryLet(block: (T) -> R): R? = try {
  block(this)
} catch (_: Exception) {
  null
}

inline fun <T> tryOrNull(f: () -> T) = try {
  f()
} catch (_: Exception) {
  null
}

inline fun <T : Any> ifLet(vararg elements: T?, block: (List<T>) -> Unit) {
  if (elements.all { it != null }) {
    block(elements.filterNotNull())
  }
}

fun Boolean?.toInt(): Int {
  return if (this == true) 1 else 0
}