package com.estebakos.breakingapp.base

interface NavigationProvider<in A> {

    fun navigateTo(originView: A, destinationView: A, params: Any? = null)
}
