package com.villevalta.imgur.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.villevalta.imgur.R
import com.villevalta.imgur.databinding.FragmentTagBinding
import com.villevalta.imgur.model.ListFilter
import com.villevalta.imgur.model.Sort
import com.villevalta.imgur.model.Thumb
import com.villevalta.imgur.model.Window
import com.villevalta.imgur.ui.dialog.ListFilterEditDialog
import com.villevalta.imgur.ui.dialog.ListFilterEditDialogListener
import com.villevalta.imgur.ui.fragment.common.BaseFragment
import com.villevalta.imgur.ui.list.ListFragment
import com.villevalta.imgur.ui.list.adapter.StatefulCommonLinearPagedAdapter
import com.villevalta.imgur.ui.list.adaptermodels.AdapterItem
import com.villevalta.imgur.ui.viewmodel.TagModel
import com.villevalta.imgur.utils.GlideApp
import com.villevalta.imgur.utils.UrlUtils
import com.villevalta.imgur.viewmodel.TagViewModel
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.fragment_viral.*

class TagFragment : BaseFragment(R.layout.fragment_tag), ListFilterEditDialogListener {

  var bgLoadStarted = false
  lateinit var binding: FragmentTagBinding
  lateinit var listFragment: TagPostsFragment

  val args by navArgs<TagFragmentArgs>()

  private val vm: TagViewModel by lazy {
    ViewModelProviders.of(this).get(TagViewModel::class.java)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    binding = FragmentTagBinding.bind(view)

    binding.vm = vm
    binding.lifecycleOwner = viewLifecycleOwner

    fragmentManager?.commit {
      listFragment = TagPostsFragment()
      replace(R.id.listFragment, listFragment)
    }

    fab_filter.setOnClickListener {
      fragmentManager?.let {
        ListFilterEditDialog.newInstance(vm.tagParams.value?.second)
          .apply { listener = this@TagFragment }
          .show(it, "dialog")
      }
    }

    vm.tagModel.observe(viewLifecycleOwner, Observer {
      it?.let {
        if (!bgLoadStarted) {
          bgLoadStarted = true
          setBackGround(it.tag.background_hash)
        }
      }
    })

    vm.tagPosts.observe(viewLifecycleOwner, Observer {
      it?.let { res ->
        listFragment.adapter.state = res.status to res.error?.message
      }
      it?.data?.let { tagListPair ->
        @Suppress("UNCHECKED_CAST")
        val posts = tagListPair.second as PagedList<AdapterItem<Any>>?
        posts?.let {
          listFragment.adapter.submitList(posts)
        }
      }
    })

    args.tag?.let {
      vm.tagModel.value = TagModel(it)
    }

    vm.tagParams.value =
      (args.name ?: args.tag?.name ?: throw IllegalArgumentException("no name")) to ListFilter(
        sort = Sort.viral,
        window = Window.day
      )
  }

  override fun onListFilterApply(newFilter: ListFilter) {
    vm.tagParams.postValue(vm.tagParams.value?.copy(second = newFilter))
  }

  fun setBackGround(hash: String?) {
    hash?.let {
      GlideApp
        .with(requireContext())
        .load(UrlUtils.getImageUrlForHash(it, Thumb.l))
        .transition(withCrossFade())
        .into(binding.tagBackground)
    }

  }

}

class TagPostsFragment : ListFragment() {

  val adapter = StatefulCommonLinearPagedAdapter()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    recycler.layoutManager = LinearLayoutManager(view.context)
    recycler.adapter = adapter
  }

}