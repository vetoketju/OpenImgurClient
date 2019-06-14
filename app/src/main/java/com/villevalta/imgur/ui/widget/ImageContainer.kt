package com.villevalta.imgur.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.core.view.updateLayoutParams
import androidx.databinding.BindingAdapter
import com.villevalta.imgur.R
import com.villevalta.imgur.model.Image
import com.villevalta.imgur.utils.GlideApp
import com.villevalta.imgur.utils.gone
import com.villevalta.imgur.utils.visible
import kotlinx.android.synthetic.main.widget_image_container.view.*
import timber.log.Timber

class ImageContainer @kotlin.jvm.JvmOverloads constructor(
  context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

  var image: Image? = null
    set(value) {
      field = value
      setup()
    }

  // TODO: Play only one video at a time, do not play until scroll has settled

  init {
    View.inflate(context, R.layout.widget_image_container, this)
    widget_image_container_video.setOnPreparedListener { mp ->
      Timber.d("Setting loop(${image?.looping} for ${image?.id} ")
      mp.isLooping = image?.looping ?: false // TODO: is this called before every play?
    }
  }

  override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    updateSize()
  }

  private fun updateSize() {

    // TODO
    return

    image?.let {
      if (measuredWidth > 0) {
        val toHeight = (measuredWidth / it.width) * it.height
        if (measuredHeight != toHeight) {
          Timber.d("container size: $measuredHeight vs $toHeight")
          updateLayoutParams {
            height = toHeight
          }
        }
      }
    }
  }

  fun setup() {
    resetViews()
    updateSize()
    image?.let {
      val isVideo = it.animated && it.mp4 != null

      if (isVideo) {
        //  Use surfaceview for better performance
        // Timber.d("Starting video: ${it.mp4}")
        // widget_image_container_video.visible()
        // widget_image_container_video.setVideoURI(Uri.parse(it.mp4))
        // widget_image_container_video.start()
        widget_image_container_image.visible()
        widget_image_container_image.setImageResource(R.drawable.ic_launcher_background)

      } else {
        widget_image_container_image.visible()
        GlideApp.with(context)
          .load(it.link)
          .into(widget_image_container_image)
      }


    }
  }

  fun resetViews() {
    widget_image_container_video.stopPlayback()
    widget_image_container_video.setVideoURI(null)
    widget_image_container_video.gone()

    widget_image_container_image.setImageDrawable(null)
    widget_image_container_image.gone()
  }

}


@BindingAdapter("image")
fun setImage(container: ImageContainer, image: Image?) {
  container.image = image
}