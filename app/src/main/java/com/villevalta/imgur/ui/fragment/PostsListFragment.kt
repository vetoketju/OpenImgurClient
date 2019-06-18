package com.villevalta.imgur.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.villevalta.imgur.ui.list.ListFragment
import com.villevalta.imgur.ui.list.adapter.StatefulCommonLinearPagedAdapter
import com.villevalta.imgur.ui.list.viewholders.GalleryPostViewHolder
import com.villevalta.imgur.ui.list.viewholders.ImagePostViewHolder
import kotlinx.android.synthetic.main.fragment_list.*
import timber.log.Timber

open class PostsListFragment : ListFragment() {

  val adapter = StatefulCommonLinearPagedAdapter()
  private lateinit var layoutManager: LinearLayoutManager

  var autoPlayGifs = true

  private var firstVisibleItem = -1
    set(value) {
      if (value != field && value >= 0) {
        Timber.d("firstVisibleItem: $value")
        field = value

        var started: RecyclerView.ViewHolder? = null

        if (autoPlayGifs) {
          layoutManager.findViewByPosition(firstVisibleItem)?.let {
            recycler.findContainingViewHolder(it)?.let { viewHolder ->
              started = viewHolder
              (viewHolder as? GalleryPostViewHolder)?.setPlayingIfGif(true)
              (viewHolder as? ImagePostViewHolder)?.setPlayingIfGif(true)
            }
          }
        }

        for (i in 0..layoutManager.childCount) {
          layoutManager.getChildAt(i)?.let {
            recycler.findContainingViewHolder(it)?.let { viewHolder ->
              if (viewHolder != started) {
                (viewHolder as? GalleryPostViewHolder)?.setPlayingIfGif(false)
                (viewHolder as? ImagePostViewHolder)?.setPlayingIfGif(false)
              }
            }
          }
        }
      }
    }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    layoutManager = LinearLayoutManager(view.context)

    recycler.layoutManager = layoutManager
    recycler.adapter = adapter

    recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
      override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        firstVisibleItem = layoutManager.findFirstCompletelyVisibleItemPosition()
      }
    })
  }

}