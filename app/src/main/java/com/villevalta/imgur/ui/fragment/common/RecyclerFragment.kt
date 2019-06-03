package com.villevalta.imgur.ui.fragment.common

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.villevalta.imgur.R
import com.villevalta.imgur.ui.listviewmodels.ListViewModel
import java.util.*

open class RecyclerFragment : BaseFragment(R.layout.fragment_list) {}

abstract class BaseListAdapter<VM : ListViewModel<Any>> :
  ListAdapter<VM, BaseViewHolder<VM>>(ListViewModelDiffer()) {

  override fun getItemViewType(position: Int): Int {
    return getItem(position).getLayoutId()
  }

  override fun onBindViewHolder(holder: BaseViewHolder<VM>, position: Int) {
    holder.bind(getItem(position))
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<VM> {
    return getViewHolder(LayoutInflater.from(parent.context).inflate(viewType, parent, false))
  }

  abstract fun getViewHolder(view: View): BaseViewHolder<VM>

}

abstract class BasePagedListAdapter<T : Any>(differ: DiffUtil.ItemCallback<T>) :
  PagedListAdapter<T, BaseViewHolder<T>>(differ) {
}


abstract class BaseViewHolder<in T>(view: View) : RecyclerView.ViewHolder(view) {
  val context : Context = view.context
  abstract fun bind(model: T)
}

// Diffs

class ListViewModelDiffer<T : ListViewModel<Any>> : BaseDiffItemCallback<T>() {
  override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
    return Objects.equals(oldItem.getIdentifyingField(), newItem.getIdentifyingField())
  }
}

abstract class BaseDiffItemCallback<T> : DiffUtil.ItemCallback<T>() {
  override fun areContentsTheSame(oldItem: T, newItem: T): Boolean =
    Objects.equals(oldItem, newItem)
}