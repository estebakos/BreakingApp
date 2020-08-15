package com.estebakos.sunbelt.test.base

interface NavigationProvider<in A> {

    fun navigateTo(originView: A, destinationView: A, params: Any? = null)
}
