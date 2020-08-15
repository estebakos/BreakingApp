package com.estebakos.sunbelt.test.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.estebakos.sunbelt.test.data.local.entity.AnimeEntity
import com.estebakos.sunbelt.test.data.local.entity.AnimeListEntity

@Dao
interface AnimeDao {
    @Query("SELECT * FROM anime_list_entity")
    suspend fun getAll(): List<AnimeListEntity>

    @Query("SELECT * FROM anime_list_entity WHERE title LIKE :query")
    suspend fun findByQuery(query: String): List<AnimeListEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(item: List<AnimeListEntity>)

    @Query("DELETE FROM anime_list_entity")
    suspend fun deleteAll()
}