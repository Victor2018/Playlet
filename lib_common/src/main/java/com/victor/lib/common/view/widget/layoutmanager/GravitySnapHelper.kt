package com.victor.lib.common.view.widget.layoutmanager

import android.util.DisplayMetrics
import android.view.Gravity
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.Scroller
import androidx.annotation.Px
import androidx.core.text.TextUtilsCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.*
import java.util.*

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: GravitySnapHelper
 * Author: Victor
 * Date: 2023/08/04 15:23
 * Description: 
 * -----------------------------------------------------------------
 */

class GravitySnapHelper : LinearSnapHelper {
    val FLING_DISTANCE_DISABLE = -1
    val FLING_SIZE_FRACTION_DISABLE = -1f
    private var gravity = 0
    private var isRtl = false
    private var snapLastItem = false
    private var nextSnapPosition = 0
    private var isScrolling = false
    private var snapToPadding = false
    private var scrollMsPerInch = 100f
    private var maxFlingDistance = FLING_DISTANCE_DISABLE
    private var maxFlingSizeFraction = FLING_SIZE_FRACTION_DISABLE
    private var verticalHelper: OrientationHelper? = null
    private var horizontalHelper: OrientationHelper? = null
    private var listener: SnapListener? = null
    private var mRecyclerView: RecyclerView? = null

    private val scrollListener: RecyclerView.OnScrollListener =
        object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                onScrollStateChanged(newState)
            }
        }

    constructor(gravity: Int): this(gravity, false, null)

    constructor(gravity: Int, snapListener: SnapListener): this(gravity, false, snapListener)

    constructor(gravity: Int, enableSnapLastItem: Boolean): this(gravity, enableSnapLastItem, null)

    constructor(
        gravity: Int, enableSnapLastItem: Boolean,
        snapListener: SnapListener?
    ) {
        require(gravity != Gravity.START && gravity != Gravity.END
                && gravity != Gravity.BOTTOM && gravity != Gravity.TOP
                && gravity == Gravity.CENTER) {
            "Invalid gravity value. Use START " +
                    "| END | BOTTOM | TOP | CENTER constants"
        }
        snapLastItem = enableSnapLastItem
        this.gravity = gravity
        listener = snapListener
    }

    @Throws(IllegalStateException::class)
    override fun attachToRecyclerView(recyclerView: RecyclerView?) {
        if (this.mRecyclerView != null) {
            this.mRecyclerView?.removeOnScrollListener(scrollListener)
        }
        if (recyclerView != null) {
            recyclerView.onFlingListener = null
            if (gravity == Gravity.START || gravity == Gravity.END) {
                isRtl = (TextUtilsCompat.getLayoutDirectionFromLocale(Locale.getDefault())
                        == ViewCompat.LAYOUT_DIRECTION_RTL)
            }
            recyclerView.addOnScrollListener(scrollListener)
            this.mRecyclerView = recyclerView
        } else {
            this.mRecyclerView = null
        }
        super.attachToRecyclerView(recyclerView)
    }

    override fun findSnapView(lm: RecyclerView.LayoutManager): View? {
        return findSnapView(lm, true)
    }

    fun findSnapView(lm: RecyclerView.LayoutManager, checkEdgeOfList: Boolean): View? {
        var snapView: View? = null
        when (gravity) {
            Gravity.START -> snapView =
                findView(lm, getHorizontalHelper(lm), Gravity.START, checkEdgeOfList)
            Gravity.END -> snapView =
                findView(lm, getHorizontalHelper(lm), Gravity.END, checkEdgeOfList)
            Gravity.TOP -> snapView =
                findView(lm, getVerticalHelper(lm), Gravity.START, checkEdgeOfList)
            Gravity.BOTTOM -> snapView =
                findView(lm, getVerticalHelper(lm), Gravity.END, checkEdgeOfList)
            Gravity.CENTER -> snapView = if (lm.canScrollHorizontally()) {
                findView(
                    lm, getHorizontalHelper(lm), Gravity.CENTER,
                    checkEdgeOfList
                )
            } else {
                findView(
                    lm, getVerticalHelper(lm), Gravity.CENTER,
                    checkEdgeOfList
                )
            }
        }
        nextSnapPosition = if (snapView != null) {
            mRecyclerView!!.getChildAdapterPosition(snapView)
        } else {
            RecyclerView.NO_POSITION
        }
        return snapView
    }

    override fun calculateDistanceToFinalSnap(
        layoutManager: RecyclerView.LayoutManager,
        targetView: View
    ): IntArray {
        if (gravity == Gravity.CENTER) {
            return super.calculateDistanceToFinalSnap(layoutManager, targetView)!!
        }
        val out = IntArray(2)
        if (layoutManager !is LinearLayoutManager) {
            return out
        }
        val lm = layoutManager
        if (lm.canScrollHorizontally()) {
            if ((isRtl && gravity == Gravity.END || !isRtl) && gravity == Gravity.START) {
                out[0] = getDistanceToStart(targetView, getHorizontalHelper(lm))
            } else {
                out[0] = getDistanceToEnd(targetView, getHorizontalHelper(lm))
            }
        } else if (lm.canScrollVertically()) {
            if (gravity == Gravity.TOP) {
                out[1] = getDistanceToStart(targetView, getVerticalHelper(lm))
            } else {
                out[1] = getDistanceToEnd(targetView, getVerticalHelper(lm))
            }
        }
        return out
    }

    override fun calculateScrollDistance(velocityX: Int, velocityY: Int): IntArray {
        if (mRecyclerView == null || verticalHelper == null && horizontalHelper == null
            || (maxFlingDistance == FLING_DISTANCE_DISABLE
                    && maxFlingSizeFraction == FLING_SIZE_FRACTION_DISABLE)
        ) {
            return super.calculateScrollDistance(velocityX, velocityY)
        }
        val out = IntArray(2)
        val scroller = Scroller(
            mRecyclerView!!.context,
            DecelerateInterpolator()
        )
        val maxDistance = getFlingDistance()
        scroller.fling(
            0, 0, velocityX, velocityY,
            -maxDistance, maxDistance,
            -maxDistance, maxDistance
        )
        out[0] = scroller.finalX
        out[1] = scroller.finalY
        return out
    }

    override fun createScroller(layoutManager: RecyclerView.LayoutManager): RecyclerView.SmoothScroller? {
        return if (layoutManager !is RecyclerView.SmoothScroller.ScrollVectorProvider
            || mRecyclerView == null
        ) {
            null
        } else object : LinearSmoothScroller(mRecyclerView?.context) {
            override fun onTargetFound(
                targetView: View,
                state: RecyclerView.State,
                action: Action
            ) {
                if (mRecyclerView == null || mRecyclerView?.layoutManager == null) {
                    // The associated RecyclerView has been removed so there is no action to take.
                    return
                }
                val snapDistances = calculateDistanceToFinalSnap(
                    mRecyclerView!!.layoutManager!!,
                    targetView
                )
                val dx = snapDistances[0]
                val dy = snapDistances[1]
                val time = calculateTimeForDeceleration(Math.max(Math.abs(dx), Math.abs(dy)))
                if (time > 0) {
                    action.update(dx, dy, time, mDecelerateInterpolator)
                }
            }

            override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float {
                return scrollMsPerInch / displayMetrics.densityDpi
            }
        }
    }

    /**
     * Sets a [SnapListener] to listen for snap events
     *
     * @param listener a [SnapListener] that'll receive snap events or null to clear it
     */
    fun setSnapListener(listener: SnapListener?) {
        this.listener = listener
    }

    /**
     * Changes the gravity of this [GravitySnapHelper]
     * and dispatches a smooth scroll for the new snap position.
     *
     * @param newGravity one of the following: [Gravity.START], [Gravity.TOP],
     * [Gravity.END], [Gravity.BOTTOM], [Gravity.CENTER]
     * @param smooth     true if we should smooth scroll to new edge, false otherwise
     */
    fun setGravity(newGravity: Int, smooth: Boolean) {
        if (gravity != newGravity) {
            gravity = newGravity
            updateSnap(smooth, false)
        }
    }

    /**
     * Updates the current view to be snapped
     *
     * @param smooth          true if we should smooth scroll, false otherwise
     * @param checkEdgeOfList true if we should check if we're at an edge of the list
     * and snap according to [GravitySnapHelper.getSnapLastItem],
     * or false to force snapping to the nearest view
     */
    fun updateSnap(smooth: Boolean, checkEdgeOfList: Boolean) {
        if (mRecyclerView == null || mRecyclerView?.layoutManager == null) {
            return
        }
        val lm = mRecyclerView?.layoutManager
        val snapView = findSnapView(lm!!, checkEdgeOfList)
        if (snapView != null) {
            val out = calculateDistanceToFinalSnap(lm, snapView)
            if (smooth) {
                mRecyclerView?.smoothScrollBy(out[0], out[1])
            } else {
                mRecyclerView?.scrollBy(out[0], out[1])
            }
        }
    }

    /**
     * This method will only work if there's a ViewHolder for the given position.
     *
     * @return true if scroll was successful, false otherwise
     */
    fun scrollToPosition(position: Int): Boolean {
        return if (position == RecyclerView.NO_POSITION) {
            false
        } else scrollTo(position, false)
    }

    /**
     * Unlike [GravitySnapHelper.scrollToPosition],
     * this method will generally always find a snap view if the position is valid.
     *
     *
     * The smooth scroller from [GravitySnapHelper.createScroller]
     * will be used, and so will [GravitySnapHelper.scrollMsPerInch] for the scroll velocity
     *
     * @return true if scroll was successful, false otherwise
     */
    fun smoothScrollToPosition(position: Int): Boolean {
        return if (position == RecyclerView.NO_POSITION) {
            false
        } else scrollTo(position, true)
    }

    /**
     * Get the current gravity being applied
     *
     * @return one of the following: [Gravity.START], [Gravity.TOP], [Gravity.END],
     * [Gravity.BOTTOM], [Gravity.CENTER]
     */
    fun getGravity(): Int {
        return gravity
    }

    /**
     * Changes the gravity of this [GravitySnapHelper]
     * and dispatches a smooth scroll for the new snap position.
     *
     * @param newGravity one of the following: [Gravity.START], [Gravity.TOP],
     * [Gravity.END], [Gravity.BOTTOM], [Gravity.CENTER]
     */
    fun setGravity(newGravity: Int) {
        setGravity(newGravity, true)
    }

    /**
     * @return true if this SnapHelper should snap to the last item
     */
    fun getSnapLastItem(): Boolean {
        return snapLastItem
    }

    /**
     * Enable snapping of the last item that's snappable.
     * The default value is false, because you can't see the last item completely
     * if this is enabled.
     *
     * @param snap true if you want to enable snapping of the last snappable item
     */
    fun setSnapLastItem(snap: Boolean) {
        snapLastItem = snap
    }

    /**
     * @return last distance set through [GravitySnapHelper.setMaxFlingDistance]
     * or [GravitySnapHelper.FLING_DISTANCE_DISABLE] if we're not limiting the fling distance
     */
    fun getMaxFlingDistance(): Int {
        return maxFlingDistance
    }

    /**
     * Changes the max fling distance in absolute values.
     *
     * @param distance max fling distance in pixels
     * or [GravitySnapHelper.FLING_DISTANCE_DISABLE]
     * to disable fling limits
     */
    fun setMaxFlingDistance(@Px distance: Int) {
        maxFlingDistance = distance
        maxFlingSizeFraction = FLING_SIZE_FRACTION_DISABLE
    }

    /**
     * @return last distance set through [GravitySnapHelper.setMaxFlingSizeFraction]
     * or [GravitySnapHelper.FLING_SIZE_FRACTION_DISABLE]
     * if we're not limiting the fling distance
     */
    fun getMaxFlingSizeFraction(): Float {
        return maxFlingSizeFraction
    }

    /**
     * Changes the max fling distance depending on the available size of the RecyclerView.
     *
     *
     * Example: if you pass 0.5f and the RecyclerView measures 600dp,
     * the max fling distance will be 300dp.
     *
     * @param fraction size fraction to be used for the max fling distance
     * or [GravitySnapHelper.FLING_SIZE_FRACTION_DISABLE]
     * to disable fling limits
     */
    fun setMaxFlingSizeFraction(fraction: Float) {
        maxFlingDistance = FLING_DISTANCE_DISABLE
        maxFlingSizeFraction = fraction
    }

    /**
     * @return last scroll speed set through [GravitySnapHelper.setScrollMsPerInch]
     * or 100f
     */
    fun getScrollMsPerInch(): Float {
        return scrollMsPerInch
    }

    /**
     * Sets the scroll duration in ms per inch.
     *
     *
     * Default value is 100.0f
     *
     *
     * This value will be used in
     * [GravitySnapHelper.createScroller]
     *
     * @param ms scroll duration in ms per inch
     */
    fun setScrollMsPerInch(ms: Float) {
        scrollMsPerInch = ms
    }

    /**
     * @return true if this SnapHelper should snap to the padding. Defaults to false.
     */
    fun getSnapToPadding(): Boolean {
        return snapToPadding
    }

    /**
     * If true, GravitySnapHelper will snap to the gravity edge
     * plus any amount of padding that was set in the RecyclerView.
     *
     *
     * The default value is false.
     *
     * @param snapToPadding true if you want to snap to the padding
     */
    fun setSnapToPadding(snapToPadding: Boolean) {
        this.snapToPadding = snapToPadding
    }

    /**
     * @return the position of the current view that's snapped
     * or [RecyclerView.NO_POSITION] in case there's none.
     */
    fun getCurrentSnappedPosition(): Int {
        if (mRecyclerView != null && mRecyclerView?.layoutManager != null) {
            val snappedView = findSnapView(mRecyclerView!!.layoutManager!!)
            if (snappedView != null) {
                return mRecyclerView!!.getChildAdapterPosition(snappedView)
            }
        }
        return RecyclerView.NO_POSITION
    }

    private fun getFlingDistance(): Int {
        return if (maxFlingSizeFraction != FLING_SIZE_FRACTION_DISABLE) {
            if (verticalHelper != null) {
                (mRecyclerView!!.height * maxFlingSizeFraction).toInt()
            } else if (horizontalHelper != null) {
                (mRecyclerView!!.width * maxFlingSizeFraction).toInt()
            } else {
                Int.MAX_VALUE
            }
        } else if (maxFlingDistance != FLING_DISTANCE_DISABLE) {
            maxFlingDistance
        } else {
            Int.MAX_VALUE
        }
    }

    /**
     * @return true if the scroll will snap to a view, false otherwise
     */
    private fun scrollTo(position: Int, smooth: Boolean): Boolean {
        if (mRecyclerView!!.layoutManager != null) {
            if (smooth) {
                val smoothScroller = createScroller(
                    mRecyclerView!!.layoutManager!!
                )
                if (smoothScroller != null) {
                    smoothScroller.targetPosition = position
                    mRecyclerView!!.layoutManager!!.startSmoothScroll(smoothScroller)
                    return true
                }
            } else {
                val viewHolder = mRecyclerView!!.findViewHolderForAdapterPosition(position)
                if (viewHolder != null) {
                    val distances = calculateDistanceToFinalSnap(
                        mRecyclerView!!.layoutManager!!,
                        viewHolder.itemView
                    )
                    mRecyclerView?.scrollBy(distances[0], distances[1])
                    return true
                }
            }
        }
        return false
    }

    private fun getDistanceToStart(targetView: View, helper: OrientationHelper): Int {
        val distance: Int
        // If we don't care about padding, just snap to the start of the view
        distance = if (!snapToPadding) {
            val childStart = helper.getDecoratedStart(targetView)
            if (childStart >= helper.startAfterPadding / 2) {
                childStart - helper.startAfterPadding
            } else {
                childStart
            }
        } else {
            helper.getDecoratedStart(targetView) - helper.startAfterPadding
        }
        return distance
    }

    private fun getDistanceToEnd(targetView: View, helper: OrientationHelper): Int {
        val distance: Int
        distance = if (!snapToPadding) {
            val childEnd = helper.getDecoratedEnd(targetView)
            if (childEnd >= helper.end - (helper.end - helper.endAfterPadding) / 2) {
                helper.getDecoratedEnd(targetView) - helper.end
            } else {
                childEnd - helper.endAfterPadding
            }
        } else {
            helper.getDecoratedEnd(targetView) - helper.endAfterPadding
        }
        return distance
    }

    /**
     * Returns the first view that we should snap to.
     *
     * @param layoutManager the RecyclerView's LayoutManager
     * @param helper        orientation helper to calculate view sizes
     * @param gravity       gravity to find the closest view
     * @return the first view in the LayoutManager to snap to, or null if we shouldn't snap to any
     */
    private fun findView(
        layoutManager: RecyclerView.LayoutManager,
        helper: OrientationHelper,
        gravity: Int,
        checkEdgeOfList: Boolean
    ): View? {
        if (layoutManager.childCount == 0 || layoutManager !is LinearLayoutManager) {
            return null
        }
        val lm = layoutManager

        // If we're at an edge of the list, we shouldn't snap
        // to avoid having the last item not completely visible.
        if (checkEdgeOfList && isAtEdgeOfList(lm) && !snapLastItem) {
            return null
        }
        var edgeView: View? = null
        var distanceToTarget = Int.MAX_VALUE
        val center: Int
        center = if (layoutManager.getClipToPadding()) {
            helper.startAfterPadding + helper.totalSpace / 2
        } else {
            helper.end / 2
        }
        val snapToStart = gravity == Gravity.START && !isRtl || gravity == Gravity.END && isRtl
        val snapToEnd = gravity == Gravity.START && isRtl || gravity == Gravity.END && !isRtl
        for (i in 0 until lm.childCount) {
            val currentView = lm.getChildAt(i)
            var currentViewDistance: Int
            currentViewDistance = if (snapToStart) {
                if (!snapToPadding) {
                    Math.abs(helper.getDecoratedStart(currentView))
                } else {
                    Math.abs(
                        helper.startAfterPadding
                                - helper.getDecoratedStart(currentView)
                    )
                }
            } else if (snapToEnd) {
                if (!snapToPadding) {
                    Math.abs(
                        helper.getDecoratedEnd(currentView)
                                - helper.end
                    )
                } else {
                    Math.abs(
                        helper.endAfterPadding
                                - helper.getDecoratedEnd(currentView)
                    )
                }
            } else {
                Math.abs(
                    helper.getDecoratedStart(currentView) + helper.getDecoratedMeasurement(
                        currentView
                    ) / 2 - center
                )
            }
            if (currentViewDistance < distanceToTarget) {
                distanceToTarget = currentViewDistance
                edgeView = currentView
            }
        }
        return edgeView
    }

    private fun isAtEdgeOfList(lm: LinearLayoutManager): Boolean {
        return if (!lm.reverseLayout && gravity == Gravity.START || lm.reverseLayout && gravity == Gravity.END || !lm.reverseLayout && gravity == Gravity.TOP || lm.reverseLayout && gravity == Gravity.BOTTOM) {
            lm.findLastCompletelyVisibleItemPosition() == lm.itemCount - 1
        } else if (gravity == Gravity.CENTER) {
            (lm.findFirstCompletelyVisibleItemPosition() == 0
                    || lm.findLastCompletelyVisibleItemPosition() == lm.itemCount - 1)
        } else {
            lm.findFirstCompletelyVisibleItemPosition() == 0
        }
    }

    /**
     * Dispatches a [SnapListener.onSnap] event if the snapped position
     * is different than [RecyclerView.NO_POSITION].
     *
     *
     * When [GravitySnapHelper.findSnapView] returns null,
     * [GravitySnapHelper.dispatchSnapChangeWhenPositionIsUnknown] is called
     *
     * @param newState the new RecyclerView scroll state
     */
    private fun onScrollStateChanged(newState: Int) {
        if (newState == RecyclerView.SCROLL_STATE_IDLE && listener != null) {
            if (isScrolling) {
                if (nextSnapPosition != RecyclerView.NO_POSITION) {
                    listener!!.onSnap(nextSnapPosition)
                } else {
                    dispatchSnapChangeWhenPositionIsUnknown()
                }
            }
        }
        isScrolling = newState != RecyclerView.SCROLL_STATE_IDLE
    }

    /**
     * Calls [GravitySnapHelper.findSnapView]
     * without the check for the edge of the list.
     *
     *
     * This makes sure that a position is reported in [SnapListener.onSnap]
     */
    private fun dispatchSnapChangeWhenPositionIsUnknown() {
        val layoutManager = mRecyclerView!!.layoutManager ?: return
        val snapView = findSnapView(layoutManager, false) ?: return
        val snapPosition = mRecyclerView?.getChildAdapterPosition(snapView) ?: 0
        if (snapPosition != RecyclerView.NO_POSITION) {
            listener!!.onSnap(snapPosition)
        }
    }

    private fun getVerticalHelper(layoutManager: RecyclerView.LayoutManager): OrientationHelper {
        if (verticalHelper == null || verticalHelper!!.layoutManager !== layoutManager) {
            verticalHelper = OrientationHelper.createVerticalHelper(layoutManager)
        }
        return verticalHelper!!
    }

    private fun getHorizontalHelper(layoutManager: RecyclerView.LayoutManager): OrientationHelper {
        if (horizontalHelper == null || horizontalHelper!!.layoutManager !== layoutManager) {
            horizontalHelper = OrientationHelper.createHorizontalHelper(layoutManager)
        }
        return horizontalHelper!!
    }

    /**
     * A listener that's called when the [RecyclerView] used by [GravitySnapHelper]
     * changes its scroll state to [RecyclerView.SCROLL_STATE_IDLE]
     * and there's a valid snap position.
     */
    interface SnapListener {
        /**
         * @param position last position snapped to
         */
        fun onSnap(position: Int)
    }
}