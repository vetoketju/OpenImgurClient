package com.villevalta.imgur.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.villevalta.imgur.model.Tag

@Dao
interface TagsDao {

  @Query("SELECT * FROM tags")
  fun getAll(): LiveData<List<Tag>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insert(vararg tag: Tag)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertAll(tags: List<Tag>)

}