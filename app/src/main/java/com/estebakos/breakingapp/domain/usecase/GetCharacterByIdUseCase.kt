package com.estebakos.breakingapp.domain.usecase

import com.estebakos.breakingapp.base.Output
import com.estebakos.breakingapp.domain.repository.CharactersRepository
import com.estebakos.breakingapp.ui.model.CharacterItemUI
import java.io.IOException
import javax.inject.Inject

class GetCharacterByIdUseCase @Inject constructor(private val charactersRepository: CharactersRepository) {

    suspend fun execute(id: Int): Output<CharacterItemUI> =
        charactersRepository.getCharacterById(id).let { output ->
            if (output is Output.Success) {
                Output.Success(output.data)
            } else {
                Output.Error(IOException())
            }
        }
}