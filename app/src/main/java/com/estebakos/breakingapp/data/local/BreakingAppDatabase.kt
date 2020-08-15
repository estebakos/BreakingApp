package com.estebakos.sunbelt.test.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.estebakos.sunbelt.test.data.local.dao.AnimeDao
import com.estebakos.sunbelt.test.data.local.entity.AnimeEntity
import com.estebakos.sunbelt.test.data.local.entity.AnimeListEntity

@Database(
    entities = [AnimeListEntity::class,
        AnimeEntity::class],
    version = 1
)
abstract class SunbeltDatabase : RoomDatabase() {
    abstract fun animeDao(): AnimeDao
}