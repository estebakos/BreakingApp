package com.estebakos.breakingapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.estebakos.breakingapp.data.local.entity.CharacterEntity

@Dao
interface CharacterDao {
    @Query("SELECT * FROM character_entity")
    suspend fun getAll(): List<CharacterEntity>

    @Query("SELECT * FROM character_entity WHERE character_id =:id")
    suspend fun getById(id: Int): CharacterEntity

    @Query("SELECT * FROM character_entity WHERE favorite = 1")
    suspend fun getFavorites(): List<CharacterEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(item: List<CharacterEntity>)

    @Query("UPDATE character_entity SET favorite =:favorite WHERE character_id =:characterId")
    suspend fun favorite(characterId: Int, favorite: Boolean)

    @Query("DELETE FROM character_entity")
    suspend fun deleteAll()
}