package com.villevalta.imgur.ui.list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.villevalta.imgur.ui.list.adaptermodels.AdapterItem
import com.villevalta.imgur.ui.list.diffs.ListViewModelDiffer
import com.villevalta.imgur.ui.list.viewholders.BaseViewHolder

abstract class BaseListAdapter<VM : AdapterItem<Any>> :
  ListAdapter<VM, BaseViewHolder<VM>>(ListViewModelDiffer()) {

  override fun getItemViewType(position: Int): Int {
    return getItem(position).getLayoutId()
  }

  override fun onBindViewHolder(holder: BaseViewHolder<VM>, position: Int) {
    holder.bind(getItem(position))
  }

  override fun onViewRecycled(holder: BaseViewHolder<VM>) {
    holder.recycle()
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<VM> {
    return getViewHolder(inflate(parent, viewType), viewType)
  }

  abstract fun getViewHolder(view: View, viewType: Int): BaseViewHolder<VM>

  fun inflate(parent: ViewGroup, viewRes: Int): View {
    return LayoutInflater.from(parent.context).inflate(viewRes, parent, false)
  }

}