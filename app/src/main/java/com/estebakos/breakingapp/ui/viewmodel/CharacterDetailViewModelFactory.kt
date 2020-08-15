package com.estebakos.breakingapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.estebakos.breakingapp.domain.usecase.SetFavoriteUseCase
import javax.inject.Inject

class CharacterDetailViewModelFactory @Inject constructor(
    private val setFavoriteUseCase: SetFavoriteUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CharacterDetailViewModel::class.java)) {
            return CharacterDetailViewModel(
                setFavoriteUseCase
            ) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}