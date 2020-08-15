package com.estebakos.breakingapp.ui.model

data class CharactersItemUI(
    val id: Int,
    val name: String,
    val occupation: List<String>,
    val imageUrl: String,
    val status: String,
    val nickname: String,
    val portrayed: String
)