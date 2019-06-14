package com.villevalta.imgur.ui.list.viewholders

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.villevalta.imgur.ui.list.adaptermodels.AdapterItem

abstract class BaseViewHolder<T : AdapterItem<*>>(view: View) : RecyclerView.ViewHolder(view) {
  val context: Context = view.context
  abstract fun bind(model: T)
  open fun recycle() {}
}