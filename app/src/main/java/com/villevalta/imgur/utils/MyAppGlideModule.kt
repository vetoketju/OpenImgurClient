package com.villevalta.imgur.utils

import android.content.Context
import android.util.Log
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.villevalta.imgur.BuildConfig

@GlideModule
class MyAppGlideModule : AppGlideModule() {

  override fun applyOptions(context: Context, builder: GlideBuilder) {
    if (BuildConfig.DEBUG) builder.setLogLevel(Log.DEBUG)
    super.applyOptions(context, builder)
  }
}
