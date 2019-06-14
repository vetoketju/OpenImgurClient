package com.villevalta.imgur.ui.list.viewholders

import android.view.View
import androidx.viewpager.widget.ViewPager
import com.villevalta.imgur.databinding.ListItemGallerypostFullBinding
import com.villevalta.imgur.ui.list.adaptermodels.GalleryPostAdapterItem
import kotlinx.android.synthetic.main.list_item_gallerypost_full.view.*

class GalleryPostViewHolder(view: View) :
  BaseViewHolder<GalleryPostAdapterItem>(view), ViewPager.OnPageChangeListener {

  private val binding = ListItemGallerypostFullBinding.bind(view)

  private var model: GalleryPostAdapterItem? = null

  override fun onPageScrollStateChanged(state: Int) {
  }

  override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
  }

  override fun onPageSelected(position: Int) {
    binding.currentImage = position + 1
    model?.pagerPosition = position
  }

  init {
    binding.root.imagePager.addOnPageChangeListener(this)
  }

  override fun bind(model: GalleryPostAdapterItem) {
    this.model = model
    binding.post = model.post
    binding.imagePager.currentItem = model.pagerPosition
    binding.currentImage = model.pagerPosition + 1
  }

}