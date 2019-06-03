package com.villevalta.imgur.ui.widget

import android.content.Context
import android.util.AttributeSet
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.villevalta.imgur.model.Image
import com.villevalta.imgur.ui.fragment.ImageFragment

class ImagesViewPager @kotlin.jvm.JvmOverloads constructor(
  context: Context, attrs: AttributeSet? = null
) : ViewPager(context, attrs) {

  var showTitles = false
    set(value) {
      field = value
      if (adapter is ImagesViewPagerAdapter) {
        (adapter as ImagesViewPagerAdapter).showTitles = value
      }
    }

  var images = emptyList<Image>()
    set(value) {
      field = value
      if (adapter is ImagesViewPagerAdapter) {
        (adapter as ImagesViewPagerAdapter).images = value
      }
    }

  // TODO: swipable mode / buttons prev < next > -mode

  override fun onAttachedToWindow() {
    super.onAttachedToWindow()
    if (context is FragmentActivity) {

    }
  }

  class ImagesViewPagerAdapter(fm: FragmentManager) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    var images = emptyList<Image>()
    var showTitles = false

    override fun getCount() = images.size

    override fun getItem(position: Int): Fragment {
      return ImageFragment.newInstance(images[position])
    }

  }

}

@BindingAdapter("showTitles")
fun imagesViewPagerShowTitles(view: ImagesViewPager, show: Boolean?) {
  view.showTitles = show ?: false
}

@BindingAdapter("images")
fun imagesViewPagerSetImages(view: ImagesViewPager, images: List<Image>?) {
  view.images = images ?: emptyList()
}