package com.villevalta.imgur.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.annotation.Nullable
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.villevalta.imgur.R
import com.villevalta.imgur.databinding.ListItemTagHalfBinding
import com.villevalta.imgur.model.Tag
import com.villevalta.imgur.model.Thumb
import com.villevalta.imgur.ui.fragment.common.BaseListAdapter
import com.villevalta.imgur.ui.fragment.common.BaseViewHolder
import com.villevalta.imgur.ui.fragment.common.RecyclerFragment
import com.villevalta.imgur.ui.listviewmodels.ListViewModel
import com.villevalta.imgur.utils.*
import com.villevalta.imgur.viewmodel.TagsViewModel
import kotlinx.android.synthetic.main.fragment_list.*

class TagsRecyclerFragment : RecyclerFragment(), TagListViewModel.TagCallBacks {

  private val vm: TagsViewModel by lazy {
    ViewModelProviders.of(this).get(TagsViewModel::class.java)
  }

  private val adapter = TagsAdapter()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    recycler.adapter = adapter
    recycler.layoutManager = GridLayoutManager(view.context, 2)

    swipeRefresh.isEnabled = true

    vm.tags.observe(viewLifecycleOwner, Observer {
      it?.data?.let { tags ->
        adapter.submitList(tags.mapIndexed { index, tag ->
          TagListViewModel(tag).also { it.callback = this }
        })
      }

      it?.let { resource ->
        when (resource.status) {
          Status.LOADING -> {
            setLoading(true)
          }
          Status.ERROR -> {
            setLoading(false)
            context?.let { ctx ->
              resource.error?.message?.let {
                ctx.toastError(it)
              }
            }
          }
          else -> {
            setLoading(false)
          }
        }
      }
    })

    swipeRefresh.setOnRefreshListener {
      vm.updateTags()
    }

  }

  private fun setLoading(isLoading: Boolean) {
    swipeRefresh.isRefreshing = isLoading
  }

  override fun onClick(model: TagListViewModel) {

    var msg =
      "${model.item.thumbnail_hash} -> ${model.item.thumbnail_is_animated}, " +
          "${model.item.background_hash} -> ${model.item.background_is_animated}, " +
          "${model.item.logo_hash} -> ${model.item.logo_destination_url}"

    Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
  }

  override fun onFollowClick(model: TagListViewModel) {

  }

}

class TagsAdapter : BaseListAdapter<TagListViewModel>() {
  override fun getViewHolder(view: View): BaseViewHolder<TagListViewModel> {
    return TagViewHolder(ListItemTagHalfBinding.bind(view))
  }
}

class TagViewHolder(val binding: ListItemTagHalfBinding) :
  BaseViewHolder<TagListViewModel>(binding.root) {

  override fun bind(model: TagListViewModel) {
    binding.tag = model
    binding.logoImage.setImageDrawable(null)
    model.item.background_hash?.let { hash ->
      GlideApp.with(context)
        .load(UrlUtils.getImageUrlForHash(hash, Thumb.m))
        .transition(withCrossFade())
        .into(binding.logoImage)
    }

    binding.root.setOnClickListener {
      model.callback?.onClick(model)
    }
  }

}

class TagListViewModel(tag: Tag) : ListViewModel<Tag>(tag) {

  override fun getLayoutId(): Int {
    return R.layout.list_item_tag_half
  }

  override fun getIdentifyingField(): Any = item.name

  var callback: TagCallBacks? = null

  val title = with(item) { display_name ?: name }

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
    return item.accent?.tryLet {
      Color.parseColor("#$it")
    }
  }

  interface TagCallBacks {
    fun onClick(model: TagListViewModel)
    fun onFollowClick(model: TagListViewModel)
  }
}