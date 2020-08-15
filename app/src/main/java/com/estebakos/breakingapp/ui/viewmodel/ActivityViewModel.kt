package com.estebakos.breakingapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.estebakos.breakingapp.base.NavigationProvider


class ActivityViewModel : ViewModel(), NavigationProvider<ActivityViewModel.CharacterView> {

    private val currentViewMutableLiveData =
        MutableLiveData<Triple<CharacterView, CharacterView, Any?>>()

    val currentViewLiveData: LiveData<Triple<CharacterView, CharacterView, Any?>>
        get() = currentViewMutableLiveData

    override fun navigateTo(
        originView: CharacterView,
        destinationView: CharacterView,
        params: Any?
    ) {
        currentViewMutableLiveData.value = Triple(originView, destinationView, params)
    }

    sealed class CharacterView {
        object CharacterListFragment : CharacterView()
        object CharacterDetailFragment : CharacterView()
    }
}