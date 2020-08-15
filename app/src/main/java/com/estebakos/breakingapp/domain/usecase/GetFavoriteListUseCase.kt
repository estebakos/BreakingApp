package com.estebakos.breakingapp.domain.usecase

import com.estebakos.breakingapp.base.Constants
import com.estebakos.breakingapp.base.Output
import com.estebakos.breakingapp.domain.repository.CharactersRepository
import com.estebakos.breakingapp.ui.model.CharacterItemUI
import java.io.IOException
import javax.inject.Inject

class GetCharacterListUseCase @Inject constructor(private val charactersRepository: CharactersRepository) {

    suspend fun execute(offset: Int? = 0): Output<List<CharacterItemUI>> {
        var characterListOutput: Output<List<CharacterItemUI>>
        var favorites: MutableList<CharacterItemUI> = mutableListOf()

        charactersRepository.getFavoriteList().let { output ->
            if (output is Output.Success) {
                favorites = output.data.toMutableList()
            }
        }

        charactersRepository.getCharacterList(Constants.LIMIT_CHARACTERS_API, offset)
            .let { output ->
                characterListOutput = if (output is Output.Success) {
                    if (favorites.isEmpty()) {
                        charactersRepository.insertCharacterList(output.data)
                        Output.Success(output.data)
                    } else {
                        favorites.forEach {character ->
                            favorites.addAll(output.data.filter { character.id != it.id })
                        }

                        charactersRepository.insertCharacterList(output.data)
                        Output.Success(favorites.distinct())
                    }
                } else {
                    Output.Error(IOException())
                }
            }

        if (characterListOutput is Output.Error) {
            charactersRepository.getLocalCharacterList().let { output ->
                characterListOutput = if (output is Output.Success) {
                    Output.Success(output.data)
                } else {
                    Output.Error(IOException())
                }
            }
        }

        return characterListOutput
    }
}