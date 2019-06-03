package com.villevalta.imgur.model

import android.os.Parcelable
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Image(
  val id: String, //The ID for the image
  val title: String,  //The title of the image.
  val description: String?,  //Description of the image.
  val datetime: Int,  //Time uploaded, epoch time
  val type: String, //Image MIME type.
  val animated: Boolean,  //is the image animated
  val width: Int, //The width of the image in pixels
  val height: Int,  //The height of the image in pixels
  val size: Int,  //The size of the image in bytes
  val views: Int, //The number of image views
  val bandwidth: Int, //Bandwidth consumed by the image in bytes
  val deletehash: String?,  //OPTIONAL, the deletehash, if you're logged in as the image owner
  val name: String?,  //OPTIONAL, the original filename, if you're logged in as the image owner
  val section: String,  //If the image has been categorized by our backend then this will contain the section the image belongs in. (funny, cats, adviceanimals, wtf, etc)
  val link: String, //The direct link to the the image. (Note: if fetching an animated GIF that was over 20MB in original size, a .gif thumbnail will be returned)
  val gifv: String?,  //OPTIONAL, The .gifv link. Only available if the image is animated and type is 'image/gif'.
  val mp4: String?, //OPTIONAL, The direct link to the .mp4. Only available if the image is animated and type is 'image/gif'.
  val mp4_size: Int?, //OPTIONAL, The Content-Length of the .mp4. Only available if the image is animated and type is 'image/gif'. Note that a zero value (0) is possible if the video has not yet been generated
  val looping: Boolean?,  //OPTIONAL, Whether the image has a looping animation. Only available if the image is animated and type is 'image/gif'.
  val favorite: Boolean,  //Indicates if the current user favorited the image. Defaults to false if not signed in.
  val nsfw: Boolean?,  //Indicates if the image has been marked as nsfw or not. Defaults to null if information is not available.
  val vote: String?, //The current user's vote on the album. null if not signed in, if the user hasn't voted on it, or if not submitted to the gallery.
  val in_gallery: Boolean     //True if the image has been submitted to the gallery, false if otherwise.
) : Parcelable {

  @IgnoredOnParcel
  val isVideo = mp4 != null && animated

}

enum class Thumb {
  s,  // Small Square	  90x90	    resized
  b,  // Big Square	      160x160	resized
  t,  // Small Thumbnail  160x160	original-portions
  m,  // Medium Thumbnail 320x320	original-portions
  l,  // Large Thumbnail  640x640	original-portions
  h   // Huge Thumbnail	  1024x1024	original-portions
}