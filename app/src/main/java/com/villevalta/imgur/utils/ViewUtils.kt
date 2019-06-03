package com.villevalta.imgur.utils

import android.view.View
import android.view.ViewGroup

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