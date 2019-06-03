package com.villevalta.imgur.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "tags")
data class Tag(
  @PrimaryKey var name: String,
  val followers: Int,
  val display_name: String?,
  val total_items: Int,
  val following: Boolean,
  val background_hash: String?,
  val thumbnail_hash: String?,
  val accent: String?,
  val background_is_animated: Boolean,
  val thumbnail_is_animated: Boolean,
  val is_promoted: Boolean,
  val description: String?,
  val logo_hash: String?,
  val logo_destination_url: String?
  //val description_annotations: {}
) {
  @Ignore
  var items: List<Post>? = null
}