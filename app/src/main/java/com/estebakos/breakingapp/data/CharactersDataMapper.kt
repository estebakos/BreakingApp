package com.estebakos.sunbelt.test.data

import com.estebakos.sunbelt.test.base.BaseMapper
import com.estebakos.sunbelt.test.data.local.entity.AnimeListEntity
import com.estebakos.sunbelt.test.data.remote.model.AnimeDetailResponse
import com.estebakos.sunbelt.test.data.remote.model.AnimeListResponseItem
import com.estebakos.sunbelt.test.ui.model.AnimeDetailUI
import com.estebakos.sunbelt.test.ui.model.AnimeListUI

object AnimeDataMapper {

    object AnimeListRemoteToUI :
        BaseMapper<List<AnimeListResponseItem>, List<AnimeListUI>> {
        override fun map(type: List<AnimeListResponseItem>): List<AnimeListUI> {
            return type.map {
                AnimeListUI(
                    animeId = it.animeId,
                    title = it.title,
                    synopsis = it.synopsis,
                    imageUrl = it.imageUrl
                )
            }
        }
    }

    object AnimeListUIToCache : BaseMapper<List<AnimeListUI>, List<AnimeListEntity>> {
        override fun map(type: List<AnimeListUI>): List<AnimeListEntity> {
            return type.map {
                AnimeListEntity(
                    animeId = it.animeId,
                    title = it.title,
                    synopsis = it.synopsis,
                    imageUrl = it.imageUrl
                )
            }
        }
    }

    object AnimeListCacheToUI : BaseMapper<List<AnimeListEntity>, List<AnimeListUI>> {
        override fun map(type: List<AnimeListEntity>): List<AnimeListUI> {
            return type.map {
                AnimeListUI(
                    animeId = it.animeId,
                    title = it.title,
                    synopsis = it.synopsis,
                    imageUrl = it.imageUrl
                )
            }
        }
    }

    object AnimeDetailRemoteToUI : BaseMapper<AnimeDetailResponse, AnimeDetailUI> {
        override fun map(type: AnimeDetailResponse): AnimeDetailUI {
            return with(type) {
                AnimeDetailUI(
                    animeId = animeId,
                    title = title,
                    rated = rated,
                    imageUrl = imageUrl,
                    airing = airing,
                    synopsis = synopsis,
                    type = this.type,
                    episodes = if (episodes != null) {
                        episodes!!
                    } else {
                        0
                    },
                    score = score,
                    startDate = startDate,
                    endDate = endDate,
                    url = url
                )
            }
        }
    }
}