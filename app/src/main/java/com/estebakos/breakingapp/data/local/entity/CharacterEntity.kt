package com.estebakos.sunbelt.test.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "anime_entity")
data class AnimeEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo(name = "anime_id")
    var animeId: Int,
    @ColumnInfo(name = "url")
    var url: String?,
    @ColumnInfo(name = "image_url")
    var imageUrl: String? = null,
    @ColumnInfo(name = "title")
    var title: String? = null,
    @ColumnInfo(name = "airing")
    var airing: Boolean,
    @ColumnInfo(name = "synopsis")
    var synopsis: String? = null,
    @ColumnInfo(name = "type")
    var type: String? = null,
    @ColumnInfo(name = "episodes")
    var episodes: Int,
    @ColumnInfo(name = "score")
    var score: Double,
    @ColumnInfo(name = "start_date")
    var startDate: String? = null,
    @ColumnInfo(name = "end_date")
    var endDate: String? = null,
    @ColumnInfo(name = "rated")
    var rated: String? = null
)