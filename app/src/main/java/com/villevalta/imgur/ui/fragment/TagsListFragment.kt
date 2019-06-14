package com.villevalta.imgur.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.villevalta.imgur.databinding.ListItemTagHalfBinding
import com.villevalta.imgur.model.Thumb
import com.villevalta.imgur.ui.list.ListFragment
import com.villevalta.imgur.ui.list.adapter.BaseListAdapter
import com.villevalta.imgur.ui.list.adaptermodels.TagAdapterItem
import com.villevalta.imgur.ui.list.viewholders.BaseViewHolder
import com.villevalta.imgur.utils.GlideApp
import com.villevalta.imgur.utils.Status
import com.villevalta.imgur.utils.UrlUtils
import com.villevalta.imgur.utils.toastError
import com.villevalta.imgur.viewmodel.TagsViewModel
import kotlinx.android.synthetic.main.fragment_list.*

class TagsListFragment : ListFragment(), TagAdapterItem.TagCallBacks {

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
          TagAdapterItem(tag).also { it.callback = this }
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

  override fun onClick(model: TagAdapterItem) {
    view?.findNavController()
      ?.navigate(TagsListFragmentDirections.actionTagsListToTagDetailFragment(tag = model.item))
  }

  override fun onFollowClick(model: TagAdapterItem) {

  }

}

class TagsAdapter : BaseListAdapter<TagAdapterItem>() {
  override fun getViewHolder(view: View, viewType: Int): BaseViewHolder<TagAdapterItem> {
    return TagViewHolder(ListItemTagHalfBinding.bind(view))
  }
}

class TagViewHolder(val binding: ListItemTagHalfBinding) :
  BaseViewHolder<TagAdapterItem>(binding.root) {

  override fun bind(model: TagAdapterItem) {
    binding.item = model
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