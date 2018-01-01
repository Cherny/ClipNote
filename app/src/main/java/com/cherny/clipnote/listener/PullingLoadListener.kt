package com.cherny.clipnote.listener

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

/**
 * Created by cherny on 1/1/18.
 */
abstract class PullingLoadListener(var layoutManager:LinearLayoutManager): RecyclerView.OnScrollListener() {

    private var currentPage = 0
    private var totalCount = 0
    private var previousTotal = 0

    private var visiableCount = 0
    private var firstVisiablePosition = 0
    private var loading = false

    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        this.visiableCount = recyclerView!!.childCount
        this.totalCount = layoutManager.itemCount
        this.firstVisiablePosition = layoutManager.findFirstVisibleItemPosition()

        if (this.loading){
            if (this.totalCount > this.previousTotal){
                this.loading = false
                previousTotal = totalCount
            }

        }

        if (!this.loading && (this.totalCount - this.visiableCount) <= this.firstVisiablePosition){

            this.currentPage ++
            this.loading = true
            this.onLoadMore(this.visiableCount)
        }
    }

    abstract fun onLoadMore(loadCount: Int)

}