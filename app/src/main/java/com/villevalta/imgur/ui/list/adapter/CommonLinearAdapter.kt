package com.villevalta.imgur.ui.list.adapter

import android.view.View
import com.villevalta.imgur.ui.list.adaptermodels.AdapterItem
import com.villevalta.imgur.ui.list.adaptermodels.GalleryPostAdapterItem
import com.villevalta.imgur.ui.list.adaptermodels.ImagePostAdapterItem
import com.villevalta.imgur.ui.list.adaptermodels.StateAdapterItem
import com.villevalta.imgur.ui.list.viewholders.BaseViewHolder
import com.villevalta.imgur.ui.list.viewholders.GalleryPostViewHolder
import com.villevalta.imgur.ui.list.viewholders.ImagePostViewHolder
import com.villevalta.imgur.ui.list.viewholders.StateViewHolder
import com.villevalta.imgur.utils.Status
import com.villevalta.imgur.utils.toInt

open class CommonLinearAdapter : BaseListAdapter<AdapterItem<Any>>() {

  override fun getViewHolder(view: View, viewType: Int): BaseViewHolder<AdapterItem<Any>> {
    return when (viewType) {
      GalleryPostAdapterItem.layoutID -> GalleryPostViewHolder(view)
      ImagePostAdapterItem.layoutID -> ImagePostViewHolder(view)
      StateAdapterItem.layoutID -> StateViewHolder(view)
      else -> throw IllegalArgumentException("Unknown viewType $viewType")
    } as BaseViewHolder<AdapterItem<Any>>
  }
}

open class CommonLinearPagedAdapter : BasePagedListAdapter<AdapterItem<Any>>() {

  override fun getViewHolder(view: View, viewType: Int): BaseViewHolder<AdapterItem<Any>> {
    return when (viewType) {
      GalleryPostAdapterItem.layoutID -> GalleryPostViewHolder(view)
      ImagePostAdapterItem.layoutID -> ImagePostViewHolder(view)
      StateAdapterItem.layoutID -> StateViewHolder(view)
      else -> throw IllegalArgumentException("Unknown viewType $viewType")
    } as BaseViewHolder<AdapterItem<Any>>
  }
}

class StatefulCommonLinearAdapter : CommonLinearAdapter() {

  var state: Pair<Status, String>? = null
    set(value) {
      field = value
      notifyDataSetChanged()
    }

  val stateItem by lazy {
    StateAdapterItem(state!!)
  }

  private fun showState(): Boolean = state != null && state!!.first != Status.SUCCESS

  private fun isStatePosition(position: Int): Boolean {
    return showState() && position == itemCount - 1
  }

  override fun getItemCount(): Int {
    return super.getItemCount() + showState().toInt()
  }

  override fun getItem(position: Int): AdapterItem<Any> {
    return if (isStatePosition(position)) stateItem else super.getItem(position)
  }

  override fun getItemViewType(position: Int): Int {
    if (isStatePosition(position)) return StateAdapterItem.layoutID
    return super.getItemViewType(position)
  }

}

class StatefulCommonLinearPagedAdapter : CommonLinearPagedAdapter() {

  var state: Pair<Status, String?>? = null
    set(value) {
      field = value
      notifyDataSetChanged()
    }

  val stateItem by lazy {
    StateAdapterItem(state!!)
  }

  private fun showState(): Boolean = state != null && state!!.first != Status.SUCCESS

  private fun isStatePosition(position: Int): Boolean {
    return showState() && position == itemCount - 1
  }

  override fun getItemCount(): Int {
    return super.getItemCount() + showState().toInt()
  }

  override fun getItem(position: Int): AdapterItem<Any>? {
    return if (isStatePosition(position)) stateItem else super.getItem(position)
  }

  override fun getItemViewType(position: Int): Int {
    if (isStatePosition(position)) return StateAdapterItem.layoutID
    return super.getItemViewType(position)
  }

}