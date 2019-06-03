package com.villevalta.imgur.model

data class ListFilter(
  var section: Section? = null,
  var sort: Sort? = null,
  var window: Window? = null,
  var showViral: Boolean? = null,
  var mature: Boolean? = null,
  var albumPreviews: Boolean? = true
) {

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