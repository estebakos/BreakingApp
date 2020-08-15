package com.estebakos.breakingapp.di

import android.content.Context
import androidx.room.Room
import com.estebakos.breakingapp.data.local.BreakingAppDatabase
import com.estebakos.breakingapp.data.local.dao.CharacterDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object CacheModule {

    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext context: Context): BreakingAppDatabase {
        return Room.databaseBuilder(context, BreakingAppDatabase::class.java, "breaking_app_db.db")
            .build()
    }

    @Singleton
    @Provides
    fun provideCharactersDao(database: BreakingAppDatabase): CharacterDao {
        return database.charactersDao()
    }
}