package com.estebakos.breakingapp.data.remote.model

import com.squareup.moshi.Json

data class CharacterItemResponse(
    @field:Json(name = "char_id")
    val id: Int,
    @field:Json(name = "name")
    val name: String,
    @field:Json(name = "occupation")
    val occupation: List<String>,
    @field:Json(name = "img")
    val imageUrl: String,
    @field:Json(name = "status")
    val status: String,
    @field:Json(name = "nickname")
    val nickname: String,
    @field:Json(name = "portrayed")
    val portrayed: String
)