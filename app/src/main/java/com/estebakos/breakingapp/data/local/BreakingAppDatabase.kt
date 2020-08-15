package com.estebakos.breakingapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.estebakos.breakingapp.data.local.dao.CharacterDao
import com.estebakos.breakingapp.data.local.entity.CharacterEntity
import com.estebakos.breakingapp.util.StringListConverter

@Database(
    entities = [CharacterEntity::class],
    version = 1
)
@TypeConverters(StringListConverter::class)
abstract class BreakingAppDatabase : RoomDatabase() {
    abstract fun charactersDao(): CharacterDao
}