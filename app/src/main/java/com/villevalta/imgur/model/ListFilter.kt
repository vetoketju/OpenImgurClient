package com.villevalta.imgur.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import com.villevalta.imgur.R
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ListFilter(
  var section: Section? = null,
  var sort: Sort? = null,
  var window: Window? = null,
  var showViral: Boolean? = null,
  var mature: Boolean? = null,
  var albumPreviews: Boolean? = true
) : Parcelable {

  // {{section}}/{{sort}}/{{window}}
  override fun toString(): String {
    return listOf(section, sort, window)
      .filterNotNull()
      .map { it.toString() }
      .joinToString("/")
  }
}

enum class Section {
  hot,
  top,
  user
}

enum class Sort {
  viral,
  top,
  time,
  rising
}

enum class Window {
  day,
  week,
  month,
  year,
  all
}

@DrawableRes
fun Section?.icon(): Int {
  return when (this) {
    Section.hot -> R.drawable.ic_whatshot_black_24dp
    Section.top -> R.drawable.ic_mood_black_24dp
    Section.user -> R.drawable.ic_person_black_24dp
    null -> R.drawable.ic_grid_on_black_24dp
  }
}

@DrawableRes
fun Sort?.icon(): Int {
  return when (this) {
    Sort.viral -> R.drawable.ic_new_releases_black_24dp
    Sort.top -> R.drawable.ic_mood_black_24dp
    Sort.time -> R.drawable.ic_fiber_new_black_24dp
    Sort.rising -> R.drawable.ic_trending_up_black_24dp
    null -> R.drawable.ic_format_line_spacing_black_24dp
  }
}

/*
@DrawableRes
fun Window?.icon(): Int {
  return when (this) {
    Window.day -> TODO()
    Window.week -> TODO()
    Window.month -> TODO()
    Window.year -> TODO()
    Window.all -> TODO()
    null -> R.drawable.ic_date_range_black_24dp
  }
}*/
