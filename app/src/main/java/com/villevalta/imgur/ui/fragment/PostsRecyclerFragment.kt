package com.villevalta.imgur.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.villevalta.imgur.R
import com.villevalta.imgur.databinding.ListItemGallerypostFullBinding
import com.villevalta.imgur.databinding.ListItemImagepostFullBinding
import com.villevalta.imgur.model.GalleryPost
import com.villevalta.imgur.model.ImagePost
import com.villevalta.imgur.model.ListFilter
import com.villevalta.imgur.model.Post
import com.villevalta.imgur.ui.fragment.common.BasePagedListAdapter
import com.villevalta.imgur.ui.fragment.common.BaseViewHolder
import com.villevalta.imgur.ui.fragment.common.RecyclerFragment
import com.villevalta.imgur.viewmodel.GalleryPostsViewModel
import kotlinx.android.synthetic.main.fragment_list.*

class PostsRecyclerFragment(val filter: ListFilter? = null) : RecyclerFragment() {

  private val vm: GalleryPostsViewModel by lazy {
    ViewModelProviders.of(this).get(GalleryPostsViewModel::class.java)
  }

  private val adapter = PostsAdapter()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    recycler.layoutManager = LinearLayoutManager(view.context)
    recycler.adapter = adapter

    vm.repoResult.observe(viewLifecycleOwner, Observer {
      it?.data?.let { posts ->
        adapter.submitList(posts)
      }
    })

    filter?.let {
      vm.params.value = it
    }
  }

}

class PostsAdapter : BasePagedListAdapter<Post>(Post.DIFF) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Post> {
    with(LayoutInflater.from(parent.context)) {
      return when (viewType) {
        R.layout.list_item_gallerypost_full -> {
          GalleryPostViewHolder(ListItemGallerypostFullBinding.inflate(this, parent, false))
        }
        R.layout.list_item_imagepost_full -> {
          ImagePostViewHolder(ListItemImagepostFullBinding.inflate(this, parent, false))
        }
        else -> throw Exception("incorrect type")
      }
    }
  }

  override fun onBindViewHolder(holder: BaseViewHolder<Post>, position: Int) {
    getItem(position)?.let {
      holder.bind(it)
    }
  }


  override fun getItemViewType(position: Int): Int {
    return when (getItem(position)) {
      is GalleryPost -> R.layout.list_item_gallerypost_full
      is ImagePost -> R.layout.list_item_imagepost_full
      else -> throw Exception("incorrect type")
    }
  }

}

class GalleryPostViewHolder(val binding: ListItemGallerypostFullBinding) :
  BaseViewHolder<Post>(binding.root) {

  override fun bind(model: Post) {
    binding.post = model as? GalleryPost
  }
}

class ImagePostViewHolder(val binding: ListItemImagepostFullBinding) :
  BaseViewHolder<Post>(binding.root) {

  override fun bind(model: Post) {
    binding.post = model as? ImagePost
  }
}