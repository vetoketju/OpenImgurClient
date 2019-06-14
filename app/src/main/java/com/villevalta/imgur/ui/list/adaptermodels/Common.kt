package com.villevalta.imgur.ui.list.adaptermodels

import androidx.annotation.LayoutRes

/**
 * Abstract class that should be used with BaseAdapter. This is where UI states should be kept
 */
abstract class AdapterItem<out T>(val item: T) {

  @LayoutRes
  abstract fun getLayoutId(): Int

  abstract fun getIdentifyingField(): Any

}

abstract class SpannableAdapterItem<out T>(item: T) : AdapterItem<T>(item) {
  abstract val spanSize: ListSpanSize

  enum class ListSpanSize {
    FULL,
    HALF,
    THIRD,
    QUARTER
  }
}