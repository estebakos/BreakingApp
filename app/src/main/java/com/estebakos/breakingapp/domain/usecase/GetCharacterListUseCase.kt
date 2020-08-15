package com.estebakos.sunbelt.test.domain.usecase

import com.estebakos.sunbelt.test.base.Constants
import com.estebakos.sunbelt.test.base.Output
import com.estebakos.sunbelt.test.domain.repository.AnimeRepository
import com.estebakos.sunbelt.test.ui.model.AnimeListUI
import java.io.IOException
import javax.inject.Inject

class GetAnimeListUseCase @Inject constructor(private val animeRepository: AnimeRepository) {

    suspend fun execute(): Output<List<AnimeListUI>> {
        var animeListOutput: Output<List<AnimeListUI>> = Output.Success((listOf()))

        animeRepository.getAnimeList(Constants.DEFAULT_ANIME_QUERY).let { output ->
            animeListOutput = if (output is Output.Success) {
                animeRepository.insertAnimeList(output.data)
                Output.Success(output.data)
            } else {
                Output.Error(IOException())
            }
        }

        if (animeListOutput is Output.Error) {
            animeRepository.getLocalAnimeList().let { output ->
                animeListOutput = if (output is Output.Success) {
                    Output.Success(output.data)
                } else {
                    Output.Error(IOException())
                }
            }
        }

        return animeListOutput
    }
}