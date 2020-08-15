package com.estebakos.breakingapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.estebakos.breakingapp.R
import com.estebakos.breakingapp.base.Constants
import com.estebakos.breakingapp.base.Output
import com.estebakos.breakingapp.base.UIState
import com.estebakos.breakingapp.base.UIState.Loading
import com.estebakos.breakingapp.base.UIStateProvider
import com.estebakos.breakingapp.domain.usecase.GetCharacterByIdUseCase
import com.estebakos.breakingapp.domain.usecase.GetCharacterListUseCase
import com.estebakos.breakingapp.domain.usecase.GetFavoriteListUseCase
import com.estebakos.breakingapp.domain.usecase.SetFavoriteUseCase
import com.estebakos.breakingapp.ui.model.CharacterItemUI
import com.estebakos.breakingapp.ui.state.CharactersUIState
import kotlinx.coroutines.launch
import javax.inject.Inject


class CharactersViewModel @Inject constructor(
    private val getCharacterListUseCase: GetCharacterListUseCase,
    private val setFavoriteUseCase: SetFavoriteUseCase,
    private val getFavoriteListUseCase: GetFavoriteListUseCase,
    private val getCharacterByIdUseCase: GetCharacterByIdUseCase
) : ViewModel(), UIStateProvider<UIState<CharactersUIState>> {

    private val uiStateMutableLiveData = MutableLiveData<UIState<CharactersUIState>>()
    val uiStateLiveData: LiveData<UIState<CharactersUIState>>
        get() = uiStateMutableLiveData

    private var characters: MutableList<CharacterItemUI> = mutableListOf()
    private var favorites: MutableList<CharacterItemUI> = mutableListOf()
    private var offset = -Constants.LIMIT_CHARACTERS_API

    fun loadCharacterList() {
        if (characters.isEmpty()) {
            updateUIState(Loading())
        }

        viewModelScope.launch {
            offset += Constants.LIMIT_CHARACTERS_API
            val output = getCharacterListUseCase.execute(offset)
            if (output is Output.Success) {
                if (characters.isEmpty() && output.data.isEmpty()) {
                    characters = output.data.toMutableList()
                    updateUIState(UIState.Empty(R.string.empty_message))
                } else {
                    val distinct =
                        characters.filterNot { data -> output.data.any { data.id == it.id } }

                    characters.addAll(output.data)
                    updateUIState(UIState.Data(CharactersUIState.CharactersLoadedState(output.data)))
                }
            } else {
                if (characters.isEmpty()) {
                    updateUIState(UIState.Error(R.string.error_message))
                }
            }
        }
    }

    fun loadFavorites(id: Int? = null) {
        viewModelScope.launch {
            val output = getFavoriteListUseCase.execute()
            if (output is Output.Success) {
                val listTransformed: MutableList<CharacterItemUI> = mutableListOf()

                characters.forEach { character ->
                    output.data.forEach { favorite ->
                        if (character.id == favorite.id) {
                            listTransformed.add(character)
                        }
                    }
                }

                if (id != null) {
                    val character = getCharacterByIdUseCase.execute(id)
                    if (character is Output.Success) {
                        characters.add(character.data)
                    }
                }

                favorites = output.data.toMutableList()
                characters.removeAll(listTransformed)
                characters.sortBy { it.id }

                updateUIState(UIState.Data(CharactersUIState.CharactersResetState(characters)))
                updateUIState(UIState.Data(CharactersUIState.FavoritesLoadedState(output.data)))
            }
        }
    }

    fun hasMoreData(): Boolean =
        (characters.size + favorites.size) < Constants.CHARACTERS_LENGTH - 1

    override fun updateUIState(newUIState: UIState<CharactersUIState>) {
        uiStateMutableLiveData.value = newUIState
    }

    fun favoriteCharacter(character: CharacterItemUI) {
        viewModelScope.launch {
            val output = setFavoriteUseCase.execute(character.id, !character.favorite)
            if (output is Output.Success) {
                loadFavorites(if (character.favorite) character.id else null)
            } else {
                updateUIState(UIState.Error(R.string.error_message))
            }
        }
    }
}
