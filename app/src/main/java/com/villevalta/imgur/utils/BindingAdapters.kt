package com.villevalta.imgur.utils

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("hideIfEmpty")
fun hideTextViewIfEmpty(view: TextView, text: String?){
  view.visibleOrGone(!text.isNullOrEmpty())
}
@BindingAdapter("hideIfBlank")
fun hideTextViewIfBlank(view: TextView, text: String?){
  view.visibleOrGone(!text.isNullOrBlank())
}