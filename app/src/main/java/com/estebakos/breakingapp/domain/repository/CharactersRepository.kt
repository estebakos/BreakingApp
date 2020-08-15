package com.estebakos.sunbelt.test.domain.repository

import com.estebakos.sunbelt.test.base.Output
import com.estebakos.sunbelt.test.ui.model.AnimeDetailUI
import com.estebakos.sunbelt.test.ui.model.AnimeListUI

interface AnimeRepository {

    suspend fun getAnimeList(query: String, page: Int = 1): Output<List<AnimeListUI>>
    suspend fun getAnime(id: Int): Output<AnimeDetailUI>
    suspend fun getLocalAnimeList(): Output<List<AnimeListUI>>
    suspend fun searchLocalAnimeList(query: String): Output<List<AnimeListUI>>
    suspend fun insertAnimeList(animeList: List<AnimeListUI>)
}