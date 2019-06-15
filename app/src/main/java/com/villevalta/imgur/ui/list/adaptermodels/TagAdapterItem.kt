package com.villevalta.imgur.ui.list.adaptermodels

import com.villevalta.imgur.R
import com.villevalta.imgur.model.Tag
import com.villevalta.imgur.ui.viewmodel.TagModel

class TagAdapterItem(tag: Tag) : AdapterItem<Tag>(tag) {

  val model = TagModel(tag)

  override fun getLayoutId() = R.layout.list_item_tag_half

  override fun getIdentifyingField() = item.name

  var callback: TagCallBacks? = null

  interface TagCallBacks {
    fun onClick(model: TagAdapterItem)
    fun onFollowClick(model: TagAdapterItem)
  }
}