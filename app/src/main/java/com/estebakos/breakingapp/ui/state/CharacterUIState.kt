package com.estebakos.breakingapp.ui.state

import com.estebakos.breakingapp.ui.model.CharacterItemUI

sealed class CharactersUIState {
    data class CharactersLoadedState(val characters: List<CharacterItemUI>) : CharactersUIState()
    data class FavoritesLoadedState(val favorites: List<CharacterItemUI>) : CharactersUIState()
    data class CharactersResetState(val characters: List<CharacterItemUI>) : CharactersUIState()
}