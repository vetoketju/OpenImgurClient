package com.villevalta.imgur.ui.widget

import android.content.Context
import android.graphics.Matrix
import android.graphics.SurfaceTexture
import android.media.MediaPlayer
import android.net.Uri
import android.util.AttributeSet
import android.view.Surface
import android.view.TextureView
import timber.log.Timber

class VideoSurface @kotlin.jvm.JvmOverloads constructor(
  context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : TextureView(context, attrs, defStyleAttr), TextureView.SurfaceTextureListener {

  private var mediaPlayer: MediaPlayer? = null
  private var playRequested = false

  var requestedUri: String? = null

  init {
    surfaceTextureListener = this
  }

  fun create(uri: String) {
    if (isAvailable) {
      requestedUri = null
      try {
        if (mediaPlayer != null) {
          mediaPlayer!!.stop()
          mediaPlayer!!.reset()
        } else {
          mediaPlayer = MediaPlayer()
        }
        mediaPlayer?.let {
          it.setOnPreparedListener { mp ->
            if (mp.videoWidth > 0 && mp.videoHeight > 0) {
              adjustAspectRatio(mp.videoWidth, mp.videoHeight)
            }
          }
          it.setOnVideoSizeChangedListener { mp, width, height ->
            if (width > 0 && height > 0) {
              adjustAspectRatio(width, height)
            }
          }
          it.setVolume(0f, 0f)
          it.isLooping = true
          it.setDataSource(context, Uri.parse(uri))
          it.setSurface(Surface(surfaceTexture))
          it.prepareAsync()
        }
      } catch (e: Exception) {
        Timber.e(e)
      }
    } else {
      requestedUri = uri
    }
  }

  fun play() {
    if (mediaPlayer != null) {
      mediaPlayer!!.start()
    } else {
      playRequested = true
    }
  }

  fun pause() {
    mediaPlayer?.pause()
    playRequested = false
  }

  fun reset() {
    pause()
    requestedUri = null
    mediaPlayer?.stop()
    mediaPlayer?.reset()
  }

  fun release() {
    reset()
    mediaPlayer?.release()
    mediaPlayer = null
  }

  private fun adjustAspectRatio(videoWidth: Int, videoHeight: Int) {

    val aspectRatio = videoHeight.toDouble() / videoWidth

    val newWidth: Int
    val newHeight: Int
    if (height > (width * aspectRatio).toInt()) {
      newWidth = width
      newHeight = (width * aspectRatio).toInt()
    } else {
      newWidth = (height / aspectRatio).toInt()
      newHeight = height
    }
    val xoff = (width - newWidth) / 2f
    val yoff = (height - newHeight) / 2f

    val txform = Matrix()
    getTransform(txform)
    txform.setScale(newWidth.toFloat() / width, newHeight.toFloat() / height)
    txform.postTranslate(xoff, yoff)
    setTransform(txform)
  }

  override fun onSurfaceTextureUpdated(surface: SurfaceTexture?) {
  }

  override fun onSurfaceTextureDestroyed(surface: SurfaceTexture?): Boolean {
    release()
    return true
  }

  override fun onSurfaceTextureAvailable(
    surfaceTexture: SurfaceTexture,
    textureWidth: Int,
    textureHeight: Int
  ) {
    Timber.d("onSurfaceTextureAvailable($textureWidth, $textureHeight)")
    requestedUri?.let { create(it) }
    if (playRequested) play()
  }

  override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture, width: Int, height: Int) {
    Timber.d("onSurfaceTextureSizeChanged($width, $height)")
  }
}