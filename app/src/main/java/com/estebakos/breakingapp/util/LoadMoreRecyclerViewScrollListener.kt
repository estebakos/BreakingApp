package com.estebakos.breakingapp.util

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class LoadMoreRecyclerViewScrollListener(layoutManager: LinearLayoutManager) : EndlessRecyclerViewScrollListener(
    layoutManager
), LoadMoreListener {
    private var hasMoreToLoad = true

    fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
        if (hasMoreToLoad()) {
            fetchNextPage()
        }
    }

    override fun hasMoreToLoad(): Boolean {
        return hasMoreToLoad
    }

    fun setHasMoreToLoad(hasMore: Boolean) {
        hasMoreToLoad = hasMore
    }
}