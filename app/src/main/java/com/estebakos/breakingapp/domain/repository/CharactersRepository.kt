package com.estebakos.breakingapp.domain.repository

import com.estebakos.breakingapp.base.Output
import com.estebakos.breakingapp.ui.model.CharacterItemUI

interface CharactersRepository {

    suspend fun getCharacterList(limit: Int? = null, offset: Int? = null) : Output<List<CharacterItemUI>>
    suspend fun getLocalCharacterList(limit: Int, offset: Int): Output<List<CharacterItemUI>>
    suspend fun getFavoriteList(): Output<List<CharacterItemUI>>
    suspend fun insertCharacterList(characterList: List<CharacterItemUI>)
    suspend fun favorite(id: Int, favorite: Boolean) : Output<Boolean>
    suspend fun getCharacterById(id: Int) : Output<CharacterItemUI>
}