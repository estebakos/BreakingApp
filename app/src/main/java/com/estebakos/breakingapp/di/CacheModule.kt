package com.estebakos.sunbelt.test.di

import android.content.Context
import androidx.room.Room
import com.estebakos.sunbelt.test.data.local.SunbeltDatabase
import com.estebakos.sunbelt.test.data.local.dao.AnimeDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object CacheModule {

    @Singleton
    @Provides
    @JvmStatic
    fun provideRoomDatabase(context: Context): SunbeltDatabase {
        return Room.databaseBuilder(context, SunbeltDatabase::class.java, "sunbelt.db")
            .build()
    }

    @Singleton
    @Provides
    fun provideStorageInfoDao(database: SunbeltDatabase): AnimeDao {
        return database.animeDao()
    }
}