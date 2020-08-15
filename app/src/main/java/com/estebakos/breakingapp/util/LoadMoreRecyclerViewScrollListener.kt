package com.estebakos.breakingapp.util

abstract class LoadMoreRecyclerViewScrollListener : EndlessRecyclerViewScrollListener,
    LoadMoreListener {
    private var hasMoreToLoad = true

    constructor(layoutManager: LinearLayoutManager?) : super(layoutManager) {}
    constructor(layoutManager: GridLayoutManager?) : super(layoutManager) {}
    constructor(layoutManager: StaggeredGridLayoutManager?) : super(layoutManager) {}

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