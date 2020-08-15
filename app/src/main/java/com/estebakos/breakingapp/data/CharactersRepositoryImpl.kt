package com.estebakos.breakingapp.data

import com.estebakos.breakingapp.base.Output
import com.estebakos.breakingapp.data.local.datasource.CharactersLocalDataSource
import com.estebakos.breakingapp.data.remote.datasource.CharactersRemoteDataSource
import com.estebakos.breakingapp.domain.repository.CharactersRepository
import com.estebakos.breakingapp.ui.model.CharacterItemUI
import javax.inject.Inject

class CharactersRepositoryImpl @Inject constructor(
    private val charactersRemoteDataSource: CharactersRemoteDataSource,
    private val charactersLocalDataSource: CharactersLocalDataSource
) : CharactersRepository {

    override suspend fun getCharacterList(
        limit: Int?,
        offset: Int?
    ): Output<List<CharacterItemUI>> {
        return charactersRemoteDataSource.getCharacterLists(limit, offset)
    }

    override suspend fun getLocalCharacterList(): Output<List<CharacterItemUI>> {
        return charactersLocalDataSource.getCharacterList()
    }

    override suspend fun getFavoriteList(): Output<List<CharacterItemUI>> {
        return charactersLocalDataSource.getFavoriteList()
    }

    override suspend fun insertCharacterList(characterList: List<CharacterItemUI>) {
        charactersLocalDataSource.insertItems(characterList)
    }

    override suspend fun favorite(id: Int, favorite: Boolean): Output<Boolean> =
        charactersLocalDataSource.favorite(id, favorite)

    override suspend fun getCharacterById(id: Int): Output<CharacterItemUI> {
        return charactersLocalDataSource.getCharacterById(id)
    }
}