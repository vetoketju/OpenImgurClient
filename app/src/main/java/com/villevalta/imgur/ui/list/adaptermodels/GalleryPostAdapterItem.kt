package com.villevalta.imgur.ui.list.adaptermodels

import com.villevalta.imgur.R
import com.villevalta.imgur.model.GalleryPost

class GalleryPostAdapterItem(val post: GalleryPost) : PostAdapterItem(post) {

  override fun getLayoutId() = layoutID

  var pagerPosition = 0

  companion object {
    const val layoutID = R.layout.list_item_gallerypost_full
  }

}