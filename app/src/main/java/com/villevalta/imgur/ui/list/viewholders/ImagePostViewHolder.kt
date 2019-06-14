package com.villevalta.imgur.ui.list.viewholders

import android.view.View
import com.villevalta.imgur.databinding.ListItemImagepostFullBinding
import com.villevalta.imgur.ui.list.adaptermodels.ImagePostAdapterItem

class ImagePostViewHolder(view: View) : BaseViewHolder<ImagePostAdapterItem>(view) {

  private val binding = ListItemImagepostFullBinding.bind(view)

  override fun bind(model: ImagePostAdapterItem) {
    binding.post = model.post
  }
}