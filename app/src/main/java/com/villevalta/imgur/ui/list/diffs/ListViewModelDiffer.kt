package com.villevalta.imgur.ui.list.diffs

import com.villevalta.imgur.ui.list.adaptermodels.AdapterItem
import java.util.*

class ListViewModelDiffer<T : AdapterItem<Any>> : BaseDiffItemCallback<T>() {
  override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
    return Objects.equals(oldItem.getIdentifyingField(), newItem.getIdentifyingField())
  }
}