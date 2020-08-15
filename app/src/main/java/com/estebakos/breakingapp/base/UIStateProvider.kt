package com.estebakos.breakingapp.base

interface UIStateProvider<T> {

    fun updateUIState(newUIState: T)
}