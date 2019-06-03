package com.villevalta.imgur.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.villevalta.imgur.App
import com.villevalta.imgur.model.Tag

@Database(entities = [Tag::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

  abstract fun tagsDao(): TagsDao

  companion object {

    @Volatile
    private var instance: AppDatabase? = null

    fun getInstance(): AppDatabase {
      return instance ?: synchronized(this) {
        instance
          ?: buildDatabase(App.instance.applicationContext).also { instance = it }
      }
    }

    private fun buildDatabase(context: Context): AppDatabase {
      return Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        AppDatabase::class.java.simpleName
      )
        .fallbackToDestructiveMigration()
        .build()
    }
  }
}