package com.villevalta.imgur.data.api

import com.villevalta.imgur.model.Tag

data class ApiResponse<T>(val data: T, val success: Boolean, val status: Int)

data class TagsResponse(
  val tags: List<Tag>,
  val featured: String/*,
  val galleries: List<Post>*/
)