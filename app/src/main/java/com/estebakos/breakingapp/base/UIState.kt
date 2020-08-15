package com.estebakos.breakingapp.base

import androidx.annotation.StringRes

sealed class UIState<out T : Any> {
    data class Loading(@StringRes val message: Int? = null) : UIState<Nothing>()
    data class Data<T : Any>(val data: T) : UIState<T>()
    data class Error(@StringRes val message: Int) : UIState<Nothing>()
    data class Empty(@StringRes val message: Int? = null) : UIState<Nothing>()
}