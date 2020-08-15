package com.estebakos.breakingapp.domain.usecase

import com.estebakos.breakingapp.base.Output
import com.estebakos.breakingapp.domain.repository.CharactersRepository
import com.estebakos.breakingapp.ui.model.CharacterItemUI
import java.io.IOException
import javax.inject.Inject

class GetFavoriteListUseCase @Inject constructor(private val charactersRepository: CharactersRepository) {

    suspend fun execute(): Output<List<CharacterItemUI>> =
        charactersRepository.getFavoriteList().let { output ->
            if (output is Output.Success) {
                Output.Success(output.data.distinct())
            } else {
                Output.Error(IOException())
            }
        }
}