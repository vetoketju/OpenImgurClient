package com.villevalta.imgur.ui.viewmodel

import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.annotation.Nullable
import com.villevalta.imgur.model.Tag
import com.villevalta.imgur.utils.tryLet

class TagModel(val tag: Tag) {

  val title = with(tag) { display_name ?: name }

  @ColorInt
  fun getAccentColorDefaultBlack(): Int {
    return getAccentColorWithFallback(Color.BLACK)
  }

  @ColorInt
  fun getAccentColorDefaultTransparent(): Int {
    return getAccentColorWithFallback(Color.TRANSPARENT)
  }

  @ColorInt
  fun getAccentColorWithFallback(@ColorInt default: Int = Color.TRANSPARENT): Int {
    return getAccentColor() ?: default
  }

  @ColorInt
  @Nullable
  fun getAccentColor(): Int? {
    return tag.accent?.tryLet {
      Color.parseColor("#$it")
    }
  }

}