package com.estebakos.breakingapp.data.remote.api

import com.estebakos.breakingapp.base.Constants
import com.estebakos.breakingapp.data.remote.model.CharacterItemResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface BreakingAppApi {

    @GET("characters")
    suspend fun getCharacters(
        @Query("limit") limit: Int? = null,
        @Query("offset") offset: Int? = null
    ): List<CharacterItemResponse>
}