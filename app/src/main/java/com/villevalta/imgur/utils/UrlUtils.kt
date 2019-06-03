package com.villevalta.imgur.utils

import com.villevalta.imgur.Constants
import com.villevalta.imgur.model.Thumb
import okhttp3.HttpUrl

object UrlUtils {

  fun getPngUrlForHash(hash: String) =
    "${Constants.URL_RESOURCE_BASE}$hash${Constants.FILE_ENDING_PNG}"

  fun getJpgUrlForHash(hash: String) =
    "${Constants.URL_RESOURCE_BASE}$hash${Constants.FILE_ENDING_JPG}"

  fun getGifUrlForHash(hash: String) =
    "${Constants.URL_RESOURCE_BASE}$hash${Constants.FILE_ENDING_GIF}"

  fun getImageUrlForHash(hash: String, thumb: Thumb? = null): String {
    val hashWithThumb = thumb?.let { hash + it } ?: hash
    return HttpUrl.parse(getJpgUrlForHash(hashWithThumb))!!.toString()
  }

}