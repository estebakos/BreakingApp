package com.estebakos.breakingapp.ui.state

import androidx.annotation.StringRes
import com.estebakos.breakingapp.ui.model.CharacterItemUI

sealed class CharacterDetailUIState {
    data class CharactersLoadedState(@StringRes val message: Int) : CharacterDetailUIState()
}