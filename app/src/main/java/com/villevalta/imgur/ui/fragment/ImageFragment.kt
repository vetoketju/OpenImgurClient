package com.villevalta.imgur.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import com.villevalta.imgur.R
import com.villevalta.imgur.databinding.FragmentImageBinding
import com.villevalta.imgur.model.Image
import com.villevalta.imgur.ui.fragment.common.BaseFragment

// TODO split to ImagesViewPager internal class and a feature full imagefragment
class ImageFragment : BaseFragment(R.layout.fragment_image) {

  lateinit var image: Image
  lateinit var binding: FragmentImageBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    image = requireArguments().getParcelable(KEY_IMAGE)!!
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding = FragmentImageBinding.bind(view)
    binding.image = image
  }

  companion object {

    private const val KEY_IMAGE = "image"

    // TODO: Add "imageFragmentConfiguration" parcelable object with eg. show title option?
    fun newInstance(image: Image): ImageFragment {
      return ImageFragment().apply {
        arguments = bundleOf(
          KEY_IMAGE to image
        )
      }
    }
  }

}