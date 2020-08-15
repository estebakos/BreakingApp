package com.estebakos.sunbelt.test.data

import com.estebakos.sunbelt.test.base.Output
import com.estebakos.sunbelt.test.data.local.datasource.AnimeLocalDataSource
import com.estebakos.sunbelt.test.data.remote.datasource.AnimeRemoteDataSource
import com.estebakos.sunbelt.test.domain.repository.AnimeRepository
import com.estebakos.sunbelt.test.ui.model.AnimeDetailUI
import com.estebakos.sunbelt.test.ui.model.AnimeListUI
import javax.inject.Inject

class AnimeRepositoryImpl @Inject constructor(
    private val animeRemoteDataSource: AnimeRemoteDataSource,
    private val animeLocalDataSource: AnimeLocalDataSource
) : AnimeRepository {
    override suspend fun getAnimeList(query: String, page: Int): Output<List<AnimeListUI>> {
        return animeRemoteDataSource.getAnimeList(query, page)
    }

    override suspend fun getAnime(id: Int): Output<AnimeDetailUI> {
        return animeRemoteDataSource.getAnimeDetail(id)
    }

    override suspend fun getLocalAnimeList(): Output<List<AnimeListUI>> {
        return animeLocalDataSource.getAnimeList()
    }

    override suspend fun searchLocalAnimeList(query: String): Output<List<AnimeListUI>> {
        return animeLocalDataSource.searchByQuery(query)
    }

    override suspend fun insertAnimeList(animeList: List<AnimeListUI>) {
        animeLocalDataSource.insertItems(animeList)
    }
}