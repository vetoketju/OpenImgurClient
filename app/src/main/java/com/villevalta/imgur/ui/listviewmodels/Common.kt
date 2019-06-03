package com.villevalta.imgur.ui.listviewmodels

import androidx.annotation.LayoutRes

abstract class ListViewModel<out T>(val item: T) {

  @LayoutRes
  abstract fun getLayoutId(): Int

  abstract fun getIdentifyingField(): Any

}

abstract class SpannableListViewModel<out T>(item: T) : ListViewModel<T>(item) {
  abstract val spanSize: ListSpanSize

  enum class ListSpanSize {
    FULL,
    HALF,
    THIRD,
    QUARTER
  }
}