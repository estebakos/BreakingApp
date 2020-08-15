package com.estebakos.breakingapp.domain.usecase

import com.estebakos.breakingapp.base.Output
import com.estebakos.breakingapp.domain.repository.CharactersRepository
import java.io.IOException
import javax.inject.Inject

class SetFavoriteUseCase @Inject constructor(private val charactersRepository: CharactersRepository) {

    suspend fun execute(characterId: Int, favorite: Boolean): Output<Boolean> {
        var characterListOutput: Output<Boolean>


        charactersRepository.favorite(characterId, favorite)
            .let { output ->
                characterListOutput = if (output is Output.Success) {
                    Output.Success(output.data)
                } else {
                    Output.Error(IOException())
                }
            }

        return characterListOutput
    }
}