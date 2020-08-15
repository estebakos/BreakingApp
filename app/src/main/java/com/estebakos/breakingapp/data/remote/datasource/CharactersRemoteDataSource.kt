package com.estebakos.sunbelt.test.data.remote.datasource

import com.estebakos.sunbelt.test.base.Output
import com.estebakos.sunbelt.test.data.AnimeDataMapper
import com.estebakos.sunbelt.test.data.remote.api.AnimeApi
import com.estebakos.sunbelt.test.ui.model.AnimeDetailUI
import com.estebakos.sunbelt.test.ui.model.AnimeListUI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AnimeRemoteDataSource @Inject constructor(
    private val animeApi: AnimeApi
) {

    suspend fun getAnimeList(query: String, pages: Int = 1): Output<List<AnimeListUI>> =
        try {
            val recipesResponse = withContext(Dispatchers.IO) {
                animeApi.getAnimeList(query, pages)
            }

            val animeList = AnimeDataMapper.AnimeListRemoteToUI.map(recipesResponse.results)
            Output.Success(animeList)
        } catch (e: Exception) {
            Output.Error(e)
        }

    suspend fun getAnimeDetail(id: Int): Output<AnimeDetailUI> =
        try {
            val animeDetailResponse = withContext(Dispatchers.IO) {
                animeApi.getAnimeDetail(id)
            }

            val animeDetail = AnimeDataMapper.AnimeDetailRemoteToUI.map(animeDetailResponse)
            Output.Success(animeDetail)
        } catch (e: Exception) {
            Output.Error(e)
        }
}