package com.villevalta.imgur.ui.list.viewholders

import android.view.View
import com.villevalta.imgur.databinding.ListItemStateBinding
import com.villevalta.imgur.ui.list.adaptermodels.StateAdapterItem
import com.villevalta.imgur.utils.Status

class StateViewHolder(view: View) :
  BaseViewHolder<StateAdapterItem>(view) {

  private val binding = ListItemStateBinding.bind(view)

  override fun bind(model: StateAdapterItem) {

    binding.message = model.item.second

    when (model.item.first) {
      Status.LOADING -> binding.loadingIndicator.show()
      else -> binding.loadingIndicator.hide()
    }

  }

}
