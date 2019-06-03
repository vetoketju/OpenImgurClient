package com.villevalta.imgur.data.repository

import com.villevalta.imgur.data.api.ImgurApi
import com.villevalta.imgur.data.api.ImgurApiClient

abstract class BaseRepository {
  val service: ImgurApi by lazy {
    ImgurApiClient.getInstance().service
  }
}