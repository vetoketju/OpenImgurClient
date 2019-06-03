package com.villevalta.imgur.data.api

import com.villevalta.imgur.model.ListFilter
import com.villevalta.imgur.model.Post
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

typealias WrappedResponse<T> = Deferred<ApiResponse<T>>

interface ImgurApi {

  @GET("tags")
  fun getTags(): WrappedResponse<TagsResponse>

  @GET("gallery/{listFilter}/{page}")
  fun getPosts(
    @Path("listFilter") listFilter: ListFilter,
    @Path("page") page: Int,
    @Query("showViral") showViral: Boolean? = null,
    @Query("mature") mature: Boolean? = null,
    @Query("album_previews") albumPreviews: Boolean? = true
  ): WrappedResponse<List<Post>>


  @GET("gallery/t/{tagName}/{listFilter}")
  fun getTagPosts(
    @Path("tagName") tagName: String,
    @Path("listFilter") listFilter: ListFilter
  ): WrappedResponse<List<Post>>

}