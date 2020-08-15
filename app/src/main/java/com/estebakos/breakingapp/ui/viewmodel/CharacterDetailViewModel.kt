package com.estebakos.breakingapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.estebakos.breakingapp.R
import com.estebakos.breakingapp.base.Output
import com.estebakos.breakingapp.base.UIState
import com.estebakos.breakingapp.base.UIState.Loading
import com.estebakos.breakingapp.base.UIStateProvider
import com.estebakos.breakingapp.domain.usecase.SetFavoriteUseCase
import com.estebakos.breakingapp.ui.model.CharacterItemUI
import com.estebakos.breakingapp.ui.state.CharacterDetailUIState
import kotlinx.coroutines.launch
import javax.inject.Inject


class CharacterDetailViewModel @Inject constructor(
    private val setFavoriteUseCase: SetFavoriteUseCase
) : ViewModel(), UIStateProvider<UIState<CharacterDetailUIState>> {

    private val uiStateMutableLiveData = MutableLiveData<UIState<CharacterDetailUIState>>()
    val uiStateLiveData: LiveData<UIState<CharacterDetailUIState>>
        get() = uiStateMutableLiveData

    fun favoriteCharacter(character: CharacterItemUI, favorite: Boolean) {
        updateUIState(Loading())
        viewModelScope.launch {
            val output = setFavoriteUseCase.execute(character.id, !favorite)
            if (output is Output.Success) {
                updateUIState(UIState.Data(CharacterDetailUIState.CharactersLoadedState(if (!favorite) R.string.character_favorite_success else R.string.character_unfavorite_success)))
            } else {
                updateUIState(UIState.Error(R.string.error_message))
            }
        }
    }

    override fun updateUIState(newUIState: UIState<CharacterDetailUIState>) {
        uiStateMutableLiveData.value = newUIState
    }
}
