package com.estebakos.breakingapp.util

interface LoadMoreListener {
    fun hasMoreToLoad(): Boolean
    fun fetchNextPage()
}