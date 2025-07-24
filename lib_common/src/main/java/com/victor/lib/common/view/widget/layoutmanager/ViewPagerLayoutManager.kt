package com.victor.lib.common.view.widget.layoutmanager

import android.content.Context
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnChildAttachStateChangeListener
import com.victor.lib.common.base.BaseFragment
import com.victor.lib.common.base.BaseFragment.Companion


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: ViewPagerLayoutManager
 * Author: Victor
 * Date: 2025/7/24 11:18
 * Description: 
 * -----------------------------------------------------------------
 */
class ViewPagerLayoutManager: LinearLayoutManager {

    private val TAG: String = "ViewPagerLayoutManager"
    private var mPagerSnapHelper: PagerSnapHelper? = null
    private var mOnViewPagerListener: OnViewPagerListener? = null
    private var mRecyclerView: RecyclerView? = null
    private var mDrift = 0 //位移，用来判断移动方向
    private var mLastSelectPosition = 0 //上一次选中位置

    constructor(context: Context?, orientation: Int): this(context, orientation, false)

    constructor(context: Context?, orientation: Int, reverseLayout: Boolean):super(context, orientation, reverseLayout) {
        init()
    }

    private fun init() {
        mPagerSnapHelper = PagerSnapHelper()
    }

    override fun onAttachedToWindow(view: RecyclerView?) {
        super.onAttachedToWindow(view)
        mPagerSnapHelper?.attachToRecyclerView(view)
        this.mRecyclerView = view
        mRecyclerView?.addOnChildAttachStateChangeListener(mChildAttachStateChangeListener)
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        super.onLayoutChildren(recycler, state)
    }

    /**
     * 滑动状态的改变
     * 缓慢拖拽-> SCROLL_STATE_DRAGGING
     * 快速滚动-> SCROLL_STATE_SETTLING
     * 空闲状态-> SCROLL_STATE_IDLE
     * @param state
     */
    override fun onScrollStateChanged(state: Int) {
        when (state) {
            RecyclerView.SCROLL_STATE_IDLE -> {
                val viewIdle = mPagerSnapHelper?.findSnapView(this)
                val positionIdle = getPosition(viewIdle!!)
                if (positionIdle != mLastSelectPosition) {
                    val isBottom = positionIdle == itemCount - 1
                    mOnViewPagerListener?.onPageSelected(positionIdle, isBottom)
                }

                mLastSelectPosition = positionIdle
            }

            RecyclerView.SCROLL_STATE_DRAGGING -> {
                val viewDrag = mPagerSnapHelper?.findSnapView(this)
                val positionDrag = viewDrag?.let { getPosition(it) }
            }

            RecyclerView.SCROLL_STATE_SETTLING -> {
                val viewSettling = mPagerSnapHelper?.findSnapView(this)
                val positionSettling = viewSettling?.let { getPosition(it) }
            }
        }
    }


    /**
     * 监听竖直方向的相对偏移量
     * @param dy
     * @param recycler
     * @param state
     * @return
     */
    override fun scrollVerticallyBy(
        dy: Int,
        recycler: RecyclerView.Recycler?,
        state: RecyclerView.State?
    ): Int {
        this.mDrift = dy
        return super.scrollVerticallyBy(dy, recycler, state)
    }


    /**
     * 监听水平方向的相对偏移量
     * @param dx
     * @param recycler
     * @param state
     * @return
     */
    override fun scrollHorizontallyBy(
        dx: Int,
        recycler: RecyclerView.Recycler?,
        state: RecyclerView.State?
    ): Int {
        this.mDrift = dx
        return super.scrollHorizontallyBy(dx, recycler, state)
    }

    /**
     * 设置监听
     * @param listener
     */
    fun setOnViewPagerListener(listener: OnViewPagerListener?) {
        this.mOnViewPagerListener = listener
    }

    private val mChildAttachStateChangeListener: OnChildAttachStateChangeListener =
        object : OnChildAttachStateChangeListener {
            override fun onChildViewAttachedToWindow(view: View) {
                if (childCount == 1) {
                    mOnViewPagerListener?.onPageSelected(0, false)
                    mLastSelectPosition = 0
                }
            }

            override fun onChildViewDetachedFromWindow(view: View) {
                if (mDrift >= 0) {
                    mOnViewPagerListener?.onPageRelease(true, getPosition(view))
                } else {
                    mOnViewPagerListener?.onPageRelease(false, getPosition(view))
                }
            }
        }

    interface OnViewPagerListener {
        /*释放的监听*/
        fun onPageRelease(isNext: Boolean, position: Int)

        /*选中的监听以及判断是否滑动到底部*/
        fun onPageSelected(position: Int, isBottom: Boolean)
    }
}