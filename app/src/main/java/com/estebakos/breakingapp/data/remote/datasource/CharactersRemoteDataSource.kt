package com.estebakos.breakingapp.data.remote.datasource

import com.estebakos.breakingapp.base.Output
import com.estebakos.breakingapp.data.CharactersDataMapper
import com.estebakos.breakingapp.data.remote.api.BreakingAppApi
import com.estebakos.breakingapp.ui.model.CharacterItemUI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CharactersRemoteDataSource @Inject constructor(
    private val breakingAppApi: BreakingAppApi
) {

    suspend fun getCharacterLists(
        limit: Int? = null,
        offset: Int? = null
    ): Output<List<CharacterItemUI>> =
        try {
            val charactersResponse = withContext(Dispatchers.IO) {
                breakingAppApi.getCharacters(limit, offset)
            }

            val characters = CharactersDataMapper.CharacterListRemoteToUI.map(charactersResponse)
            Output.Success(characters)
        } catch (e: Exception) {
            Output.Error(e)
        }
}