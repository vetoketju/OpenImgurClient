package com.villevalta.imgur.ui.list.adaptermodels

import com.villevalta.imgur.R
import com.villevalta.imgur.utils.Status

// Pair = Status + possible error message
class StateAdapterItem(state: Pair<Status, String?>) : AdapterItem<Pair<Status, String?>>(state) {

  override fun getLayoutId() = layoutID

  override fun getIdentifyingField() = "LIST-STATE"

  companion object{
    const val layoutID = R.layout.list_item_state
  }

}