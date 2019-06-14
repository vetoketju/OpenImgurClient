package com.villevalta.imgur.ui.list.adapter

import android.view.View
import com.villevalta.imgur.ui.list.adaptermodels.AdapterItem
import com.villevalta.imgur.ui.list.adaptermodels.GalleryPostAdapterItem
import com.villevalta.imgur.ui.list.adaptermodels.ImagePostAdapterItem
import com.villevalta.imgur.ui.list.viewholders.BaseViewHolder
import com.villevalta.imgur.ui.list.viewholders.GalleryPostViewHolder
import com.villevalta.imgur.ui.list.viewholders.ImagePostViewHolder

class CommonLinearAdapter : BaseListAdapter<AdapterItem<Any>>() {

  override fun getViewHolder(view: View, viewType: Int): BaseViewHolder<AdapterItem<Any>> {
    return when (viewType) {
      GalleryPostAdapterItem.layoutID -> GalleryPostViewHolder(view)
      ImagePostAdapterItem.layoutID -> ImagePostViewHolder(view)
      else -> throw IllegalArgumentException("Unknown viewType $viewType")
    } as BaseViewHolder<AdapterItem<Any>>
  }
}

class CommonLinearPagedAdapter : BasePagedListAdapter<AdapterItem<Any>>() {

  override fun getViewHolder(view: View, viewType: Int): BaseViewHolder<AdapterItem<Any>> {
    return when (viewType) {
      GalleryPostAdapterItem.layoutID -> GalleryPostViewHolder(view)
      ImagePostAdapterItem.layoutID -> ImagePostViewHolder(view)
      else -> throw IllegalArgumentException("Unknown viewType $viewType")
    } as BaseViewHolder<AdapterItem<Any>>
  }
}