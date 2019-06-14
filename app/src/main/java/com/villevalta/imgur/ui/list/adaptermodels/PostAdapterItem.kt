package com.villevalta.imgur.ui.list.adaptermodels

import com.villevalta.imgur.model.Post

abstract class PostAdapterItem(item: Post) : AdapterItem<Post>(item) {

  override fun getIdentifyingField() = item.id

}