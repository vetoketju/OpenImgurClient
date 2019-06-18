package com.villevalta.imgur.ui.list.viewholders

import android.view.View
import com.villevalta.imgur.databinding.ListItemImagepostFullBinding
import com.villevalta.imgur.ui.list.adaptermodels.ImagePostAdapterItem

class ImagePostViewHolder(view: View) : BaseViewHolder<ImagePostAdapterItem>(view) {

  private val binding = ListItemImagepostFullBinding.bind(view)

  fun setPlayingIfGif(playing: Boolean){
    binding.image.playing = playing
  }

  override fun bind(model: ImagePostAdapterItem) {
    binding.post = model.post
  }
}