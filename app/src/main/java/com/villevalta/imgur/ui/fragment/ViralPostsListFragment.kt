package com.villevalta.imgur.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.villevalta.imgur.R
import com.villevalta.imgur.model.ListFilter
import com.villevalta.imgur.model.Section
import com.villevalta.imgur.model.Sort
import com.villevalta.imgur.model.Window
import com.villevalta.imgur.ui.dialog.ListFilterEditDialog
import com.villevalta.imgur.ui.dialog.ListFilterEditDialogListener
import com.villevalta.imgur.ui.fragment.common.BaseFragment
import com.villevalta.imgur.viewmodel.GalleryPostsViewModel
import kotlinx.android.synthetic.main.fragment_viral.*

class ViralPostsListFragment : BaseFragment(R.layout.fragment_viral),
  ListFilterEditDialogListener {

  private val vm: GalleryPostsViewModel by lazy {
    ViewModelProviders.of(requireActivity()).get(GalleryPostsViewModel::class.java)
  }

  private var filter: ListFilter = ListFilter(
    Section.hot,
    Sort.viral,
    Window.day,
    showViral = true,
    mature = false,
    albumPreviews = true
  )
    set(value) {
      field = value
      vm.params.postValue(filter)
    }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    fab_filter.setOnClickListener {
      fragmentManager?.let {
        ListFilterEditDialog.newInstance(filter).apply { listener = this@ViralPostsListFragment }
          .show(it, "dialog")
      }
    }
    vm.params.postValue(filter)
  }

  override fun onAttach(context: Context) {
    super.onAttach(context)
    (fragmentManager?.findFragmentByTag("dialog") as? ListFilterEditDialog)?.listener = this
  }

  override fun onListFilterApply(newFilter: ListFilter) {
    filter = newFilter
  }


}