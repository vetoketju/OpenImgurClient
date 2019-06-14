package com.villevalta.imgur.ui.list.adaptermodels

import com.villevalta.imgur.R
import com.villevalta.imgur.model.ImagePost

class ImagePostAdapterItem(val post: ImagePost) : PostAdapterItem(post) {

  override fun getLayoutId() = layoutID

  companion object {
    const val layoutID = R.layout.list_item_imagepost_full
  }

}