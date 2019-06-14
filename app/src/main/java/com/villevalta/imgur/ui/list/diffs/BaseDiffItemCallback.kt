package com.villevalta.imgur.ui.list.diffs

import androidx.recyclerview.widget.DiffUtil
import java.util.*

abstract class BaseDiffItemCallback<T> : DiffUtil.ItemCallback<T>() {
  override fun areContentsTheSame(oldItem: T, newItem: T): Boolean =
    Objects.equals(oldItem, newItem)
}