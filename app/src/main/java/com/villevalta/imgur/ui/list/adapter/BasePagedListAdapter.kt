package com.villevalta.imgur.ui.list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import com.villevalta.imgur.ui.list.adaptermodels.AdapterItem
import com.villevalta.imgur.ui.list.diffs.ListViewModelDiffer
import com.villevalta.imgur.ui.list.viewholders.BaseViewHolder

// Does not support DataSources with null as placeholder!
abstract class BasePagedListAdapter<VM : AdapterItem<Any>> :
  PagedListAdapter<VM, BaseViewHolder<VM>>(ListViewModelDiffer()) {

  override fun getItemViewType(position: Int): Int {
    return getItem(position)?.getLayoutId()!!
  }

  override fun onBindViewHolder(holder: BaseViewHolder<VM>, position: Int) {
    getItem(position)?.let {
      holder.bind(it)
    }
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