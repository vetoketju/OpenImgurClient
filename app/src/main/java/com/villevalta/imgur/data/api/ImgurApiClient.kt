package com.villevalta.imgur.data.api

import com.google.gson.*
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.villevalta.imgur.BuildConfig
import com.villevalta.imgur.Constants
import com.villevalta.imgur.model.GalleryPost
import com.villevalta.imgur.model.ImagePost
import com.villevalta.imgur.model.Post
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type

class ImgurApiClient {

  val service: ImgurApi

  init {
    service = Retrofit.Builder()
      .baseUrl(Constants.URL_API_BASE)
      .client(OkHttpClient.Builder().addNetworkInterceptor {
        return@addNetworkInterceptor it.proceed(
          it.request().newBuilder().addHeader(
            "Authorization",
            "Client-ID ${BuildConfig.IMGUR_CLIENT_ID}"
          ).build()
        )
      }.addNetworkInterceptor(HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
      }).build()
      ).addConverterFactory(GsonConverterFactory.create(buildGson()))
      .addCallAdapterFactory(CoroutineCallAdapterFactory())
      .build().create(ImgurApi::class.java)
  }

  companion object {

    @Volatile
    private var instance: ImgurApiClient? = null

    fun getInstance() = instance ?: synchronized(this) {
      instance ?: ImgurApiClient().also {
        instance = it
      }
    }
  }

  //https://github.com/google/gson/blob/master/UserGuide.md#TOC-Serializing-and-Deserializing-Generic-Types
  private fun buildGson(): Gson {
    return GsonBuilder()
      .registerTypeAdapter(Post::class.java, object : JsonDeserializer<Post> {
        override fun deserialize(
          json: JsonElement?, typeOfT: Type?,
          context: JsonDeserializationContext?
        ): Post {
          return with(Gson()) {
            if (json!!.asJsonObject!!.get("is_album")!!.asBoolean) {
              fromJson(json, GalleryPost::class.java)
            } else {
              fromJson(json, ImagePost::class.java)
            }
          }
        }
      }).create()
  }
}