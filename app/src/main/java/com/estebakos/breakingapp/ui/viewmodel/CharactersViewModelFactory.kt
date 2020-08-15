package com.estebakos.breakingapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.estebakos.breakingapp.domain.usecase.GetCharacterByIdUseCase
import com.estebakos.breakingapp.domain.usecase.GetCharacterListUseCase
import com.estebakos.breakingapp.domain.usecase.GetFavoriteListUseCase
import com.estebakos.breakingapp.domain.usecase.SetFavoriteUseCase
import javax.inject.Inject

class CharactersViewModelFactory @Inject constructor(
    private val getCharacterListUseCase: GetCharacterListUseCase,
    private val setFavoriteUseCase: SetFavoriteUseCase,
    private val getFavoriteListUseCase: GetFavoriteListUseCase,
    private val getCharacterByIdUseCase: GetCharacterByIdUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CharactersViewModel::class.java)) {
            return CharactersViewModel(
                getCharacterListUseCase,
                setFavoriteUseCase,
                getFavoriteListUseCase,
                getCharacterByIdUseCase
            ) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}