package com.estebakos.sunbelt.test.base

import java.util.*

interface BaseMapper<in A, out B> {
    fun map(type: A): B
}