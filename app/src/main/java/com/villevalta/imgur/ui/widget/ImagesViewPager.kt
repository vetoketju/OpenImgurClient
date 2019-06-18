package com.villevalta.imgur.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.villevalta.imgur.model.Image
import com.villevalta.imgur.ui.fragment.ImageFragment

class ImagesViewPager @kotlin.jvm.JvmOverloads constructor(
  context: Context, attrs: AttributeSet? = null
) : ViewPager(context, attrs) {

  var modeFragments = false

  var playing = false
    set(value) {
      field = value
      (adapter as? ImagesViewAdapter)?.views?.forEach {
        it.playing = playing
      }
    }

  init {
    if (modeFragments) {
      if (context is FragmentActivity) {
        adapter = ImagesViewFragmentPagerAdapter(context.supportFragmentManager)
      }
    } else {
      adapter = ImagesViewAdapter(context)
    }
  }

  fun setImages(images: List<Image>?) {
    (images ?: emptyList()).let { list ->
      (adapter as? ImagesViewFragmentPagerAdapter)?.let {
        it.images.clear()
        it.images.addAll(list)
        it.notifyDataSetChanged()
      }
      (adapter as? ImagesViewAdapter)?.let {
        it.images.clear()
        it.images.addAll(list)
        it.notifyDataSetChanged()
      }
    }
  }

  // TODO: swipable mode / buttons prev < next > -mode

  class ImagesViewFragmentPagerAdapter(
    fm: FragmentManager,
    val images: MutableList<Image> = mutableListOf()
  ) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount() = images.size

    override fun getItem(position: Int): Fragment {
      return ImageFragment.newInstance(images[position])
    }
  }

  class ImagesViewAdapter(
    private val context: Context,
    val images: MutableList<Image> = mutableListOf()
  ) : PagerAdapter() {

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
      return view == `object`
    }

    val views = mutableListOf<ImageContainer>()

    override fun getCount() = images.size

    override fun instantiateItem(container: ViewGroup, position: Int): View {

      val imageContainer = ImageContainer(context).also {
        it.image = images[position]
      }

      container.addView(
        imageContainer,
        -1,
        LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
      )

      views.add(imageContainer)
      return imageContainer
    }

    override fun destroyItem(container: ViewGroup, position: Int, item: Any) {
      views.remove(item)
      container.removeView(item as? View)
    }

    override fun getPageTitle(position: Int): CharSequence? {
      return images[position].title
    }

    override fun getItemPosition(`object`: Any) = POSITION_NONE

  }

}

@BindingAdapter("images")
fun imagesViewPagerSetImages(view: ImagesViewPager, images: List<Image>?) {
  view.setImages(images)
}