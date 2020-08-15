package com.estebakos.breakingapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "character_entity")
data class CharacterEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo(name = "character_id")
    var characterId: Int,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "occupation")
    var occupation: List<String>,
    @ColumnInfo(name = "image_url")
    var imageUrl: String,
    @ColumnInfo(name = "status")
    var status: String,
    @ColumnInfo(name = "nickname")
    var nickname: String,
    @ColumnInfo(name = "portrayed")
    var portrayed: String,
    @ColumnInfo(name = "favorite")
    var favorite: Boolean = false
)