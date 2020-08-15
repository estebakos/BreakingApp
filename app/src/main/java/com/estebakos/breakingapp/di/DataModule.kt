package com.estebakos.breakingapp.di

import com.estebakos.breakingapp.data.CharactersRepositoryImpl
import com.estebakos.breakingapp.data.local.datasource.CharactersLocalDataSource
import com.estebakos.breakingapp.data.remote.datasource.CharactersRemoteDataSource
import com.estebakos.breakingapp.domain.repository.CharactersRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module(includes = [NetworkModule::class])
@InstallIn(ApplicationComponent::class)
object DataModule {

    @Provides
    internal fun providesCharactersRepository(
        charactersRemoteDataSource: CharactersRemoteDataSource,
        charactersLocalDataSource: CharactersLocalDataSource
    ): CharactersRepository =
        CharactersRepositoryImpl(charactersRemoteDataSource, charactersLocalDataSource)
}