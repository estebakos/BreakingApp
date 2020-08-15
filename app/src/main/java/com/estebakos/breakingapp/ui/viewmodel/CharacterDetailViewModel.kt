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
    private val getFavoriteListUseCase: GetFavoriteListUseCase
) : ViewModel(), UIStateProvider<UIState<CharactersUIState>> {

    private val uiStateMutableLiveData = MutableLiveData<UIState<CharactersUIState>>()
    val uiStateLiveData: LiveData<UIState<CharactersUIState>>
        get() = uiStateMutableLiveData

    private var characters: MutableList<CharacterItemUI> = mutableListOf()

    fun loadCharacterList(offset: Int? = 0) {
        if (characters.isEmpty()) {
            updateUIState(Loading())
        }

        viewModelScope.launch {
            val output = getCharacterListUseCase.execute(offset = offset)
            if (output is Output.Success) {
                if (characters.isEmpty() && output.data.isEmpty()) {
                    characters = output.data.toMutableList()
                    updateUIState(UIState.Empty(R.string.empty_message))
                } else {
                    characters.addAll(output.data)
                    characters = characters.distinct().toMutableList()
                    updateUIState(UIState.Data(CharactersUIState.CharactersLoadedState(output.data.distinct())))
                }
            } else {
                if(characters.isEmpty()) {
                    updateUIState(UIState.Error(R.string.error_message))
                }
            }
        }
    }

    fun loadFavorites() {
        viewModelScope.launch {
            val output = getFavoriteListUseCase.execute()
            if (output is Output.Success) {
                updateUIState(UIState.Data(CharactersUIState.FavoritesLoadedState(output.data)))
                val listTransformed: MutableList<CharacterItemUI> = mutableListOf()

                output.data.forEach { character ->
                    listTransformed.addAll(characters.filter { character.id != it.id })
                }

                characters = listTransformed
                updateUIState(UIState.Data(CharactersUIState.CharactersResetState(characters)))
            }
        }
    }

    fun hasMoreData(): Boolean = characters.size < Constants.CHARACTERS_LENGTH - 1

    override fun updateUIState(newUIState: UIState<CharactersUIState>) {
        uiStateMutableLiveData.value = newUIState
    }

    fun favoriteCharacter(character: CharacterItemUI) {
        viewModelScope.launch {
            val output = setFavoriteUseCase.execute(character.id, !character.favorite)
            if (output is Output.Success) {
                loadFavorites()
            } else {
                updateUIState(UIState.Error(R.string.error_message))
            }
        }
    }
}
