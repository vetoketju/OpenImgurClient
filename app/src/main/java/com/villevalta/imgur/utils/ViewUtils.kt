package com.villevalta.imgur.utils

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

fun View.gone() {
  this.visibility = View.GONE
}

fun View.visible() {
  this.visibility = View.VISIBLE
}

fun View.invisible() {
  this.visibility = View.INVISIBLE
}

fun View.visibleOrGone(isVisible: Boolean) {
  this.visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun ViewGroup.gone() {
  this.visibility = View.GONE
}

fun ViewGroup.visible() {
  this.visibility = View.VISIBLE
}

fun ViewGroup.invisible() {
  this.visibility = View.INVISIBLE
}

fun ViewGroup.visibleOrGone(isVisible: Boolean) {
  this.visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun RecyclerView.forEachViewHolderIndexed(function: (index: Int, viewHolder: RecyclerView.ViewHolder) -> Unit) {
  for (i in 0 until childCount) {
    function(i, getChildViewHolder(getChildAt(i)))
  }
}