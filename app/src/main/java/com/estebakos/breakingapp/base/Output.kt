package com.estebakos.breakingapp.base

sealed class Output<out T : Any> {
    data class Success<out T : Any>(val data: T) : Output<T>()
    data class Error(val exception: Exception) : Output<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
        }
    }
}