package com.estebakos.breakingapp.base.extensions

fun List<String>?.toSeparatedString() : String {
    return this?.joinToString(separator = ",") ?: ""
}