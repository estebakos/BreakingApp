package com.estebakos.breakingapp.base

interface BaseMapper<in A, out B> {
    fun map(type: A): B
}