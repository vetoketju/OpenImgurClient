package com.villevalta.imgur.data.datasource

import com.villevalta.imgur.model.GalleryPost
import com.villevalta.imgur.model.ImagePost
import com.villevalta.imgur.model.Post
import com.villevalta.imgur.ui.list.adaptermodels.AdapterItem
import com.villevalta.imgur.ui.list.adaptermodels.GalleryPostAdapterItem
import com.villevalta.imgur.ui.list.adaptermodels.ImagePostAdapterItem

class PostAdapterItemMapFunction : AdapterItemMapFunction<Post>() {
  override fun apply(input: Post?): AdapterItem<Post> {
    return when (input) {
      is GalleryPost -> GalleryPostAdapterItem(input)
      is ImagePost -> ImagePostAdapterItem(input)
      else -> throw UnsupportedOperationException("not supported")
    }
  }
}