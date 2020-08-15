package com.estebakos.sunbelt.test.data.local.datasource

import com.estebakos.sunbelt.test.base.Output
import com.estebakos.sunbelt.test.data.AnimeDataMapper
import com.estebakos.sunbelt.test.data.local.dao.AnimeDao
import com.estebakos.sunbelt.test.ui.model.AnimeListUI
import java.io.IOException
import javax.inject.Inject

class AnimeLocalDataSource @Inject constructor(
    private val dao: AnimeDao
) {

    suspend fun getAnimeList(): Output<List<AnimeListUI>> =
        try {
            val itemDomain = AnimeDataMapper.AnimeListCacheToUI.map(dao.getAll())
            Output.Success(itemDomain)
        } catch (e: Throwable) {
            Output.Error(IOException("Exception ${e.message}"))
        }

    suspend fun searchByQuery(query: String): Output<List<AnimeListUI>> =
        try {
            val itemDomain = AnimeDataMapper.AnimeListCacheToUI.map(dao.findByQuery(query))
            Output.Success(itemDomain)
        } catch (e: Throwable) {
            Output.Error(IOException("Exception ${e.message}"))
        }

    suspend fun insertItems(item: List<AnimeListUI>) {
        try {
            dao.deleteAll()
            dao.insertAll(AnimeDataMapper.AnimeListUIToCache.map(item))
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }
}