package com.victor.lib.common.view.widget.layoutmanager

import android.graphics.Rect
import android.util.ArrayMap
import android.util.SparseArray
import android.util.SparseBooleanArray
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: VegaLayoutManager
 * Author: Victor
 * Date: 2023/07/26 19:06
 * Description: 
 * -----------------------------------------------------------------
 */

class VegaLayoutManager: RecyclerView.LayoutManager {
    companion object {
        const val HORIZONTAL = RecyclerView.HORIZONTAL
        const val VERTICAL = RecyclerView.VERTICAL
    }

    var mOrientation = HORIZONTAL

    private var mHorizontalOffset = 0
    private var mVerticalOffset = 0
    private val locationRects = SparseArray<Rect>()
    private val attachedItems = SparseBooleanArray()
    private val viewTypeWidthMap: ArrayMap<Int, Int> = ArrayMap()

    private var needSnap = false
    private var lastDx = 0
    private var lastDy = 0
    private var maxHorizontalScrollOffset = -1
    private var maxVerticalScrollOffset = -1
    private var adapter: RecyclerView.Adapter<*>? = null
    private var recycler: RecyclerView.Recycler? = null

    var mStartSnapHelper: StartSnapHelper? = null

    constructor(orientation: Int) {
        this.mOrientation = orientation
    }

    override fun isAutoMeasureEnabled(): Boolean {
        return true
    }

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun canScrollHorizontally(): Boolean {
        return mOrientation == HORIZONTAL
    }

    override fun canScrollVertically(): Boolean {
        return mOrientation == VERTICAL
    }

    override fun onAdapterChanged(oldAdapter: RecyclerView.Adapter<*>?, newAdapter: RecyclerView.Adapter<*>?) {
        super.onAdapterChanged(oldAdapter, newAdapter)
        adapter = newAdapter
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State) {
        this.recycler = recycler // 二话不说，先把recycler保存了

        if (state.isPreLayout) {
            return
        }

        buildLocationRects()

        // 先回收放到缓存，后面会再次统一layout
        detachAndScrapAttachedViews(recycler!!)
        layoutItemsOnCreate(recycler)
    }

    private fun buildLocationRects() {
        if (adapter != null && recycler != null) {
            locationRects.clear()
            attachedItems.clear()
            var tempPosition = if (mOrientation == HORIZONTAL) paddingStart else paddingTop
            val itemCount = itemCount
            for (i in 0 until itemCount) {
                // 1. 先计算出itemWidth和itemHeight
                val viewType = adapter?.getItemViewType(i)
                var itemWH: Int
                if (viewTypeWidthMap.containsKey(viewType)) {
                    itemWH = viewTypeWidthMap[viewType] ?: 0
                } else {
                    val itemView = recycler!!.getViewForPosition(i)
                    addView(itemView)
                    measureChildWithMargins(itemView, View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
                    itemWH = if (mOrientation == HORIZONTAL) getDecoratedMeasuredWidth(itemView) else getDecoratedMeasuredHeight(itemView)
                    viewTypeWidthMap[viewType] = itemWH
                }

                // 2. 组装Rect并保存
                val startRect = Rect()
                startRect.left = tempPosition
                startRect.top = paddingTop
                startRect.right = startRect.left + itemWH
                startRect.bottom = height - paddingBottom

                val topRect = Rect()
                topRect.left = paddingLeft
                topRect.top = tempPosition
                topRect.right = width - paddingRight
                topRect.bottom = topRect.top + itemWH

                if (mOrientation == HORIZONTAL) {
                    locationRects.put(i, startRect)
                } else {
                    locationRects.put(i, topRect)
                }

                attachedItems.put(i, false)
                tempPosition += itemWH
            }
            if (itemCount == 0) {
                maxHorizontalScrollOffset = 0
                maxVerticalScrollOffset = 0
            } else {
                computeMaxScroll()
            }
        }
    }

    /**
     * 对外提供接口，找到第一个可视view的index
     */
    fun findFirstVisibleItemPosition(): Int {
        val count = locationRects.size()
        val startRect = Rect(mHorizontalOffset, 0, width + mHorizontalOffset, height)
        val topRect = Rect(0, mVerticalOffset, width, height + mVerticalOffset)
        val displayRect = if (mOrientation == HORIZONTAL) startRect else topRect
        for (i in 0 until count) {
            if (Rect.intersects(displayRect, locationRects[i]) &&
                attachedItems[i]
            ) {
                return i
            }
        }
        return 0
    }

    /**
     * 计算可滑动的最大值
     */
    private fun computeMaxScroll() {
        if (mOrientation == HORIZONTAL) {
            computeMaxHorizontalScroll()
        } else {
            computeMaxVerticalScroll()
        }
    }

    private fun computeMaxHorizontalScroll() {
        maxHorizontalScrollOffset = locationRects[locationRects.size() - 1].right - width
        if (maxHorizontalScrollOffset < 0) {
            maxHorizontalScrollOffset = 0
            return
        }
        val itemCount = itemCount
        var screenFilledWidth = 0
        for (i in itemCount - 1 downTo 0) {
            val rect = locationRects[i]
            screenFilledWidth += (rect.right - rect.left)
            if (screenFilledWidth > width) {
                val extraSnapWidth = width - (screenFilledWidth - (rect.right - rect.left))
                maxHorizontalScrollOffset += extraSnapWidth
                break
            }
        }
    }
    private fun computeMaxVerticalScroll() {
        maxVerticalScrollOffset = locationRects[locationRects.size() - 1].bottom - height
        if (maxVerticalScrollOffset < 0) {
            maxVerticalScrollOffset = 0
            return
        }
        val itemCount = itemCount
        var screenFilledHeight = 0
        for (i in itemCount - 1 downTo 0) {
            val rect = locationRects[i]
            screenFilledHeight += (rect.bottom - rect.top)
            if (screenFilledHeight > height) {
                val extraSnapHeight = height - (screenFilledHeight - (rect.bottom - rect.top))
                maxVerticalScrollOffset += extraSnapHeight
                break
            }
        }
    }

    /**
     * 初始化的时候，layout子View
     */
    private fun layoutItemsOnCreate(recycler: RecyclerView.Recycler) {
        val itemCount = itemCount
        val startRect = Rect(mHorizontalOffset, 0, width + mHorizontalOffset, height)
        val topRect = Rect(0, mVerticalOffset, width, height + mVerticalOffset)
        val displayRect = if (mOrientation == HORIZONTAL) startRect else topRect
        for (i in 0 until itemCount) {
            val thisRect = locationRects[i]
            if (Rect.intersects(displayRect, thisRect)) {
                val childView = recycler.getViewForPosition(i)
                addView(childView)
                measureChildWithMargins(
                    childView,
                    View.MeasureSpec.UNSPECIFIED,
                    View.MeasureSpec.UNSPECIFIED
                )
                layoutItem(childView, locationRects[i])
                attachedItems.put(i, true)
                childView.pivotY = if (mOrientation == HORIZONTAL) (childView.measuredHeight / 2).toFloat() else 0f
                childView.pivotX = if (mOrientation == HORIZONTAL) 0f else (childView.measuredWidth / 2).toFloat()

                if (mOrientation == HORIZONTAL && thisRect.left - mHorizontalOffset > width) {
                    break
                }
                if (mOrientation == VERTICAL && thisRect.top - mVerticalOffset > height) {
                    break
                }
            }
        }
    }

    /**
     * 初始化的时候，layout子View
     */
    private fun layoutItemsOnScroll() {
        val childCount = childCount
        // 1. 已经在屏幕上显示的child
        val itemCount = itemCount
        val startRect = Rect(mHorizontalOffset, 0, width + mHorizontalOffset, height)
        val topRect = Rect(0, mVerticalOffset, width, height + mVerticalOffset)
        val displayRect = if (mOrientation == HORIZONTAL) startRect else topRect
        var firstVisiblePosition = -1
        var lastVisiblePosition = -1
        for (i in childCount - 1 downTo 0) {
            val child = getChildAt(i) ?: continue
            val position = getPosition(child)
            if (!Rect.intersects(displayRect, locationRects[position])) {
                // 回收滑出屏幕的View
                removeAndRecycleView(child, recycler!!)
                attachedItems.put(position, false)
            } else {
                // Item还在显示区域内，更新滑动后Item的位置
                if (lastVisiblePosition < 0) {
                    lastVisiblePosition = position
                }
                firstVisiblePosition = if (firstVisiblePosition < 0) {
                    position
                } else {
                    Math.min(firstVisiblePosition, position)
                }
                layoutItem(child, locationRects[position]) //更新Item位置
            }
        }

        // 2. 复用View处理
        if (firstVisiblePosition > 0) {
            // 往前搜索复用
            for (i in firstVisiblePosition - 1 downTo 0) {
                if (Rect.intersects(displayRect, locationRects[i]) &&
                    !attachedItems[i]
                ) {
                    reuseItemOnSroll(i, true)
                } else {
                    break
                }
            }
        }
        // 往后搜索复用
        for (i in lastVisiblePosition + 1 until itemCount) {
            if (Rect.intersects(displayRect, locationRects[i]) &&
                !attachedItems[i]
            ) {
                reuseItemOnSroll(i, false)
            } else {
                break
            }
        }
    }

    /**
     * 复用position对应的View
     */
    private fun reuseItemOnSroll(position: Int, addViewFromTop: Boolean) {
        val scrap = recycler!!.getViewForPosition(position)
        measureChildWithMargins(scrap, 0, 0)
        scrap.pivotY = if (mOrientation == HORIZONTAL) (scrap.measuredHeight / 2).toFloat() else 0f
        scrap.pivotX = if (mOrientation == HORIZONTAL) 0f else (scrap.measuredWidth / 2).toFloat()
        if (addViewFromTop) {
            addView(scrap, 0)
        } else {
            addView(scrap)
        }
        // 将这个Item布局出来
        layoutItem(scrap, locationRects[position])
        attachedItems.put(position, true)
    }

    private fun layoutItem(child: View, rect: Rect) {
        if (mOrientation == HORIZONTAL) {
            layoutHorizontalItem(child, rect)
        } else {
            layoutVericalItem(child, rect)
        }
    }

    private fun layoutHorizontalItem(child: View, rect: Rect) {
        val leftDistance = mHorizontalOffset - rect.left
        val layoutLeft: Int
        val layoutRight: Int
        val itemWidth = rect.right - rect.left
        if (leftDistance < itemWidth && leftDistance > 0) {
            val rate1 = leftDistance.toFloat() / itemWidth
            val rate2 = 1 - rate1 * rate1 / 3
            val rate3 = 1 - rate1 * rate1
            child.scaleX = rate2
            child.scaleY = rate2
            child.alpha = rate3
            layoutLeft = 0
            layoutRight = itemWidth
        } else {
            child.scaleX = 1f
            child.scaleY = 1f
            child.alpha = 1f
            layoutLeft = rect.left - mHorizontalOffset
            layoutRight = rect.right - mHorizontalOffset
        }
        //会裁减itemView 宽
//        layoutDecoratedWithMargins(child, layoutLeft, rect.top, layoutRight, rect.bottom)
        layoutDecorated(child, layoutLeft, rect.top, layoutRight, rect.bottom)
    }

    private fun layoutVericalItem(child: View, rect: Rect) {
        val topDistance: Int = mVerticalOffset - rect.top
        val layoutTop: Int
        val layoutBottom: Int
        val itemHeight = rect.bottom - rect.top
        if (topDistance < itemHeight && topDistance > 0) {
            val rate1 = topDistance.toFloat() / itemHeight
            val rate2 = 1 - rate1 * rate1 / 3
            val rate3 = 1 - rate1 * rate1
            child.scaleX = rate2
            child.scaleY = rate2
            child.alpha = rate3
            layoutTop = 0
            layoutBottom = itemHeight
        } else {
            child.scaleX = 1f
            child.scaleY = 1f
            child.alpha = 1f
            layoutTop = rect.top - mVerticalOffset
            layoutBottom = rect.bottom - mVerticalOffset
        }
        layoutDecoratedWithMargins(child, rect.left, layoutTop, rect.right, layoutBottom)
    }

    override fun scrollHorizontallyBy(
        dx: Int,
        recycler: RecyclerView.Recycler?,
        state: RecyclerView.State
    ): Int {
        if (itemCount == 0 || dx == 0) {
            return 0
        }
        var travel = dx
        if (dx + mHorizontalOffset < 0) {
            travel = -mHorizontalOffset
        } else if (dx + mHorizontalOffset > maxHorizontalScrollOffset) {
            travel = maxHorizontalScrollOffset - mHorizontalOffset
        }
        mHorizontalOffset += travel //累计偏移量
        lastDx = dx
        if (!state.isPreLayout && childCount > 0) {
            layoutItemsOnScroll()
        }
        return travel
    }

    override fun scrollVerticallyBy(dy: Int, recycler: RecyclerView.Recycler?, state: RecyclerView.State): Int {
        if (itemCount == 0 || dy == 0) {
            return 0
        }
        var travel = dy
        if (dy + mVerticalOffset < 0) {
            travel = -mVerticalOffset
        } else if (dy + mVerticalOffset > maxVerticalScrollOffset) {
            travel = maxVerticalScrollOffset - mVerticalOffset
        }
        mVerticalOffset += travel //累计偏移量

        lastDy = dy
        if (!state.isPreLayout && childCount > 0) {
            layoutItemsOnScroll()
        }

        return travel
    }

    override fun onAttachedToWindow(view: RecyclerView?) {
        super.onAttachedToWindow(view)
        try {
            view?.onFlingListener = null
            getStartSnapHelper()?.attachToRecyclerView(view)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onScrollStateChanged(state: Int) {
        if (state == RecyclerView.SCROLL_STATE_DRAGGING) {
            needSnap = true
        }
        super.onScrollStateChanged(state)
    }

    fun getSnapWidth(): Int {
        if (!needSnap) {
            return 0
        }
        needSnap = false
        val displayRect = Rect(0, mHorizontalOffset, width + mHorizontalOffset, 0)
        val itemCount = itemCount
        for (i in 0 until itemCount) {
            val itemRect = locationRects[i]
            if (displayRect.intersect(itemRect)) {
                if (lastDx > 0) {
                    // scroll变大，属于列表往下走，往下找下一个为snapView
                    if (i < itemCount - 1) {
                        val nextRect = locationRects[i + 1]
                        return nextRect.left - displayRect.left
                    }
                }
                return itemRect.left - displayRect.left
            }
        }
        return 0
    }

    fun getSnapHeight(): Int {
        if (!needSnap) {
            return 0
        }
        needSnap = false
        val displayRect = Rect(0, mVerticalOffset, width, height + mVerticalOffset)
        val itemCount = itemCount
        for (i in 0 until itemCount) {
            val itemRect = locationRects[i]
            if (displayRect.intersect(itemRect)) {
                if (lastDy > 0) {
                    // scroll变大，属于列表往下走，往下找下一个为snapView
                    if (i < itemCount - 1) {
                        val nextRect = locationRects[i + 1]
                        return nextRect.top - displayRect.top
                    }
                }
                return itemRect.top - displayRect.top
            }
        }
        return 0
    }

    fun findSnapView(): View? {
        return if (childCount > 0) {
            getChildAt(0)
        } else null
    }

    fun getStartSnapHelper(): StartSnapHelper? {
        if (mStartSnapHelper == null) {
            mStartSnapHelper = StartSnapHelper()
        }
        return mStartSnapHelper
    }

    inner class StartSnapHelper(): LinearSnapHelper() {
        override fun calculateDistanceToFinalSnap(
            layoutManager: RecyclerView.LayoutManager,
            targetView: View
        ): IntArray? {
            val out = IntArray(2)
            out[0] = if (mOrientation == HORIZONTAL)  getSnapWidth() else 0
            out[1] = if (mOrientation == HORIZONTAL)  0 else getSnapHeight()
            return out
        }

        override fun findSnapView(layoutManager: RecyclerView.LayoutManager?): View? {
            return findSnapView()
        }
    }
}