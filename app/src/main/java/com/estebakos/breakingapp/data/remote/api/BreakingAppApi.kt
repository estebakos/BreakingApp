package com.estebakos.sunbelt.test.data.remote.api

import com.estebakos.sunbelt.test.data.remote.model.AnimeDetailResponse
import com.estebakos.sunbelt.test.data.remote.model.AnimeListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AnimeApi {

    @GET("anime/{id}")
    suspend fun getAnimeDetail(
        @Path("id") id: Int
    ): AnimeDetailResponse

    @GET("search/anime")
    suspend fun getAnimeList(
        @Query("q") query: String,
        @Query("page") page: Int = 1
    ): AnimeListResponse
}