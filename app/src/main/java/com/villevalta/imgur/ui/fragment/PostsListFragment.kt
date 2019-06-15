package com.villevalta.imgur.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.villevalta.imgur.ui.list.ListFragment
import com.villevalta.imgur.ui.list.adapter.StatefulCommonLinearPagedAdapter
import com.villevalta.imgur.ui.list.adaptermodels.AdapterItem
import com.villevalta.imgur.viewmodel.GalleryPostsViewModel
import kotlinx.android.synthetic.main.fragment_list.*

class PostsListFragment : ListFragment() {

  private val vm: GalleryPostsViewModel by lazy {
    ViewModelProviders.of(requireActivity()).get(GalleryPostsViewModel::class.java)
  }

  private val adapter = StatefulCommonLinearPagedAdapter()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    recycler.layoutManager = LinearLayoutManager(view.context)
    recycler.adapter = adapter

    vm.postsResultMapped.observe(viewLifecycleOwner, Observer {
      it?.let { res ->
        adapter.state = res.status to res.error?.message
      }

      it?.data?.let { posts ->
        @Suppress("UNCHECKED_CAST")
        adapter.submitList(posts as PagedList<AdapterItem<Any>>)
      }
    })
  }

}