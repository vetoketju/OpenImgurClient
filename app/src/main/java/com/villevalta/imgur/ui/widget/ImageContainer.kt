package com.villevalta.imgur.ui.widget

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.villevalta.imgur.R
import com.villevalta.imgur.model.Image
import com.villevalta.imgur.model.Thumb
import com.villevalta.imgur.utils.GlideApp
import com.villevalta.imgur.utils.UrlUtils
import com.villevalta.imgur.utils.gone
import com.villevalta.imgur.utils.visible
import kotlinx.android.synthetic.main.widget_image_container.view.*

class ImageContainer @kotlin.jvm.JvmOverloads constructor(
  context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

  init {
    View.inflate(context, R.layout.widget_image_container, this)
  }

  var image: Image? = null
    set(value) {
      field = value
      setup()
    }

  var playing = false
    set(value) {
      if (field != value) {
        field = value
        if (value)
          startIfAnimated()
        else
          stopIfAnimated()
      }
    }

  fun startIfAnimated() {
    debug_indicator.backgroundTintList = ColorStateList.valueOf(Color.GREEN)
    if (image?.animated == true) {
      widget_image_container_video.visible()
      widget_image_container_video.play()
    }
  }

  fun stopIfAnimated() {
    debug_indicator.backgroundTintList = ColorStateList.valueOf(Color.RED)
    if (image?.animated == true) {
      widget_image_container_video.pause()
      widget_image_container_video.gone()
    }
  }

  fun setup() {
    resetViews()
    image?.let {

      debug_indicator.text = it.type

      if (it.animated && it.mp4 != null) {
        GlideApp.with(context)
          .load(UrlUtils.getImageUrlForHash(it.id, Thumb.t))
          .into(widget_image_container_image)
        widget_image_container_video.visible()
        widget_image_container_video.create(it.mp4)

      } else {
        GlideApp.with(context)
          .load(it.link)
          .into(widget_image_container_image)
          .waitForLayout()
      }


    }
  }

  fun resetViews() {
    widget_image_container_video.gone()
    widget_image_container_video.reset()
    widget_image_container_image.setImageDrawable(null)
  }

}


@BindingAdapter("image")
fun setImage(container: ImageContainer, image: Image?) {
  container.image = image
}



