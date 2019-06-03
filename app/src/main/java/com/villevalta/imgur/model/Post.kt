package com.villevalta.imgur.model

import android.os.Parcelable
import com.villevalta.imgur.ui.fragment.common.BaseDiffItemCallback
import com.villevalta.imgur.utils.toUnitString
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GalleryPost(
  override val id: String,              // The ID for the image
  override val is_album: Boolean,       // if it's an album or image
  override val title: String,           // The title of the album in the gallery //The title of the image.
  override val description: String?,     // The description of the album in the gallery //Description of the image.
  override val datetime: Int,           // Time inserted into the gallery, epoch time
  override val link: String,            // The URL link to the album
  override val vote: String?,            // The current user's vote on the album. null if not signed in or if the user hasn't voted on it.
  override val favorite: Boolean,       // Indicates if the current user favorited the album. Defaults to false if not signed in.
  override val nsfw: Boolean?,           // Indicates if the album has been marked as nsfw or not. Defaults to null if information is not available.
  override val comment_count: Int,      // Number of comments on the gallery album.
  override val topic: String,           // Topic of the gallery album.
  override val topic_id: Int,           // Topic ID of the gallery album.
  override val account_url: String?,     // The account username or null if it's anonymous.
  override val account_id: Int?,         // The account ID of the account that uploaded it, or null.
  override val ups: Int,                // Upvotes for the image
  override val downs: Int,              // Number of downvotes for the image
  override val points: Int,             // Upvotes minus downvotes
  override val score: Int,              // Imgur popularity score
  override val in_most_viral: Boolean,  // Indicates if the album is in the most viral gallery or not

  val cover: String,                    // The ID of the album cover image
  val cover_width: Int,                 // The width, in pixels, of the album cover image
  val cover_height: Int,                // The height, in pixels, of the album cover image
  val privacy: String,                  // The privacy level of the album, you can only view public if not logged in as album owner
  val layout: String,                   // The view layout of the album.
  val views: Int,                       // The number of image views
  val images_count: Int,                // The total number of images in the album
  val images: List<Image>               // of Images	An array of all the images in the album (only available when requesting the direct album)
) : Post(), Parcelable

@Parcelize
data class ImagePost(
  override val id: String,              // The ID for the image
  override val is_album: Boolean,       // if it's an album or image
  override val title: String,           // The title of the album in the gallery //The title of the image.
  override val description: String?,     // The description of the album in the gallery //Description of the image.
  override val datetime: Int,           // Time inserted into the gallery, epoch time
  override val link: String,            // The URL link to the album
  override val vote: String?,            // The current user's vote on the album. null if not signed in or if the user hasn't voted on it.
  override val favorite: Boolean,       // Indicates if the current user favorited the album. Defaults to false if not signed in.
  override val nsfw: Boolean?,           // Indicates if the album has been marked as nsfw or not. Defaults to null if information is not available.
  override val comment_count: Int,      // Number of comments on the gallery album.
  override val topic: String,           // Topic of the gallery album.
  override val topic_id: Int,           // Topic ID of the gallery album.
  override val account_url: String?,     // The account username or null if it's anonymous.
  override val account_id: Int?,         // The account ID of the account that uploaded it, or null.
  override val ups: Int,                // Upvotes for the image
  override val downs: Int,              // Number of downvotes for the image
  override val points: Int,             // Upvotes minus downvotes
  override val score: Int,              // Imgur popularity score
  override val in_most_viral: Boolean,  // Indicates if the album is in the most viral gallery or not

  val type: String,                     // IMAGE: Image MIME type
  val animated: Boolean,                // IMAGE: is the image animated
  val width: Int,                       // IMAGE: The width of the image in pixels
  val height: Int,                      // IMAGE: The height of the image in pixels
  val size: Int,                        // IMAGE: The size of the image in bytes
  val bandwidth: Int,                   // IMAGE: Bandwidth consumed by the image in bytes
  val deletehash: String?,              // IMAGE: OPTIONAL, the deletehash, if you're logged in as the image owner
  val gifv: String?,                    // IMAGE: OPTIONAL, The .gifv link. Only available if the image is animated and type is 'image/gif'.
  val mp4: String?,                     // IMAGE: OPTIONAL, The direct link to the .mp4. Only available if the image is animated and type is 'image/gif'.
  val mp4_size: Int?,                   // IMAGE: OPTIONAL, The Content-Length of the .mp4. Only available if the image is animated and type is 'image/gif'. Note that a zero value (0) is possible if the video has not yet been generated
  val looping: Boolean?,                // IMAGE: OPTIONAL, Whether the image has a looping animation. Only available if the image is animated and type is 'image/gif'.
  val section: String                   // IMAGE: If the image has been categorized by our backend then this will contain the section the image belongs in. (funny, cats, adviceanimals, wtf, etc)
) : Post(), Parcelable {

  fun toImage(): Image {
    return Image(
      this.id,
      this.title,
      this.description,
      this.datetime,
      this.type,
      this.animated,
      this.width,
      this.height,
      this.size,
      0,
      this.bandwidth,
      this.deletehash,
      null,
      this.section,
      this.link,
      this.gifv,
      this.mp4,
      this.mp4_size,
      this.looping,
      this.favorite,
      this.nsfw,
      this.vote,
      false
    )
  }

}

abstract class Post {
  abstract val id: String               // The ID for the image
  abstract val is_album: Boolean        // if it's an album or image
  abstract val title: String            // The title of the album in the gallery //The title of the image.
  abstract val description: String?      // The description of the album in the gallery //Description of the image.
  abstract val datetime: Int            // Time inserted into the gallery epoch time
  abstract val link: String             // The URL link to the album
  abstract val vote: String?             // The current user's vote on the album. null if not signed in or if the user hasn't voted on it.
  abstract val favorite: Boolean        // Indicates if the current user favorited the album. Defaults to false if not signed in.
  abstract val nsfw: Boolean?            // Indicates if the album has been marked as nsfw or not. Defaults to null if information is not available.
  abstract val comment_count: Int       // Number of comments on the gallery album.
  abstract val topic: String            // Topic of the gallery album.
  abstract val topic_id: Int            // Topic ID of the gallery album.
  abstract val account_url: String?      // The account username or null if it's anonymous.
  abstract val account_id: Int?          // The account ID of the account that uploaded it or null.
  abstract val ups: Int                 // Upvotes for the image
  abstract val downs: Int               // Number of downvotes for the image
  abstract val points: Int              // Upvotes minus downvotes
  abstract val score: Int               // Imgur popularity score
  abstract val in_most_viral: Boolean   // Indicates if the album is in the most viral gallery or not

  fun pointsString() = points.toUnitString()
  fun commentsString() = comment_count.toUnitString()

  companion object {
    val DIFF = object : BaseDiffItemCallback<Post>() {
      override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
      }
    }
  }

}