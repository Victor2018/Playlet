package com.victor.lib.common.view.widget

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.TimeInterpolator
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import com.victor.lib.common.R
import com.victor.lib.common.util.Loger
import androidx.core.content.withStyledAttributes

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: ExpandableTextView
 * Author: Victor
 * Date: 2022/4/27 12:13
 * Description:
 * https://github.com/Blogcat/Android-ExpandableTextView
 * -----------------------------------------------------------------
 */

class ExpandableTextView : AppCompatTextView {
    private val TAG = "ExpandableTextView"

    private var onExpandListeners: ArrayList<OnExpandListener>? = null
    private var expandInterpolator: TimeInterpolator? = null
    private var collapseInterpolator: TimeInterpolator? = null

    var canExpend = false
    private var mMaxLines = 0
    private var animationDuration: Long = 0
    private var animating = false
    private var expanded = false
    private var collapsedHeight = 0
    private val defaultAnimDuration = 500

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initAttrs(context, attrs, defStyleAttr)
    }

    private fun initAttrs(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        // read attributes
        context.withStyledAttributes(
            attrs, R.styleable.ExpandableTextView, defStyleAttr, 0
        ) {

            animationDuration = getInt(
                R.styleable.ExpandableTextView_animation_duration, defaultAnimDuration
            ).toLong()
        }

        // keep the original value of maxLines
        mMaxLines = maxLines

        // create bucket of OnExpandListener instances
        onExpandListeners = ArrayList()

        // create default interpolators
        expandInterpolator = AccelerateDecelerateInterpolator()
        collapseInterpolator = AccelerateDecelerateInterpolator()
    }

    override fun setText(text: CharSequence?, type: BufferType?) {
        super.setText(text, type)
        post {
            canExpend = calLines(text.toString())
        }
    }

    private fun calLines(text: String): Boolean {
        val txtW = textSize
        val maxWords = width / txtW
        val lines = text.length / maxWords
        return if (lines > mMaxLines) true else lines.toInt() == mMaxLines && text.length % maxWords > 0
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var hMeasureSpec = heightMeasureSpec
        // if this TextView is collapsed and mMaxLines = 0,
        // than make its height equals to zero
        if (mMaxLines == 0 && !this.expanded && !this.animating) {
            hMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.EXACTLY)
        }
        super.onMeasure(widthMeasureSpec, hMeasureSpec)
    }

    //region public helper methods

    //region public helper methods
    /**
     * Toggle the expanded state of this [ExpandableTextView].
     * @return true if toggled, false otherwise.
     */
    fun toggle(): Boolean {
        return if (expanded) collapse() else expand()
    }

    /**
     * Expand this [ExpandableTextView].
     * @return true if expanded, false otherwise.
     */
    fun expand(): Boolean {
        Loger.e(TAG, "expand()......")
        if (!expanded && !animating && mMaxLines >= 0) {
            // notify listener
            notifyOnExpand()

            // measure collapsed height
            measure(
                MeasureSpec.makeMeasureSpec(this.measuredWidth, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
            )
            collapsedHeight = this.measuredHeight

            // indicate that we are now animating
            animating = true

            // set mMaxLines to MAX Integer, so we can calculate the expanded height
            maxLines = Int.MAX_VALUE

            // measure expanded height
            measure(
                MeasureSpec.makeMeasureSpec(this.measuredWidth, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
            )
            val expandedHeight = this.measuredHeight

            // animate from collapsed height to expanded height
            val valueAnimator = ValueAnimator.ofInt(collapsedHeight, expandedHeight)
            valueAnimator.addUpdateListener { animation ->
                this@ExpandableTextView.height = animation.animatedValue as Int
            }

            // wait for the animation to end
            valueAnimator.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    // reset min & max height (previously set with setHeight() method)
                    this@ExpandableTextView.maxHeight = Int.MAX_VALUE
                    this@ExpandableTextView.minHeight = 0

                    // if fully expanded, set height to WRAP_CONTENT, because when rotating the device
                    // the height calculated with this ValueAnimator isn't correct anymore
                    var layoutParams = this@ExpandableTextView.layoutParams
                    layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
                    layoutParams = layoutParams

                    // keep track of current status
                    expanded = true
                    animating = false
                }
            })

            // set interpolator
            valueAnimator.interpolator = expandInterpolator

            // start the animation
            valueAnimator
                .setDuration(animationDuration)
                .start()
            return true
        }
        return false
    }

    /**
     * Collapse this [TextView].
     * @return true if collapsed, false otherwise.
     */
    fun collapse(): Boolean {
        Loger.e(TAG, "collapse()......")
        if (expanded && !animating && mMaxLines >= 0) {
            // notify listener
            notifyOnCollapse()

            // measure expanded height
            val expandedHeight = this.measuredHeight

            // indicate that we are now animating
            animating = true

            // animate from expanded height to collapsed height
            val valueAnimator = ValueAnimator.ofInt(expandedHeight, collapsedHeight)
            valueAnimator.addUpdateListener { animation ->
                this@ExpandableTextView.height = animation.animatedValue as Int
            }

            // wait for the animation to end
            valueAnimator.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    // keep track of current status
                    expanded = false
                    animating = false

                    // set mMaxLines back to original value
                    maxLines = mMaxLines

                    // if fully collapsed, set height back to WRAP_CONTENT, because when rotating the device
                    // the height previously calculated with this ValueAnimator isn't correct anymore
                    var layoutParams = this@ExpandableTextView.layoutParams
                    layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
                    layoutParams = layoutParams
                }
            })

            // set interpolator
            valueAnimator.interpolator = collapseInterpolator

            // start the animation
            valueAnimator
                .setDuration(animationDuration)
                .start()
            return true
        }
        return false
    }

    //endregion

    //region public getters and setters

    //endregion
    //region public getters and setters
    /**
     * Sets the duration of the expand / collapse animation.
     * @param animationDuration duration in milliseconds.
     */
    fun setAnimationDuration(animationDuration: Long) {
        this.animationDuration = animationDuration
    }

    /**
     * Adds a listener which receives updates about this [ExpandableTextView].
     * @param onExpandListener the listener.
     */
    fun addOnExpandListener(onExpandListener: OnExpandListener) {
        onExpandListeners?.add(onExpandListener)
    }

    /**
     * Removes a listener which receives updates about this [ExpandableTextView].
     * @param onExpandListener the listener.
     */
    fun removeOnExpandListener(onExpandListener: OnExpandListener) {
        onExpandListeners?.remove(onExpandListener)
    }

    /**
     * Sets a [TimeInterpolator] for expanding and collapsing.
     * @param interpolator the interpolator
     */
    fun setInterpolator(interpolator: TimeInterpolator?) {
        expandInterpolator = interpolator
        collapseInterpolator = interpolator
    }

    /**
     * Sets a [TimeInterpolator] for expanding.
     * @param expandInterpolator the interpolator
     */
    fun setExpandInterpolator(expandInterpolator: TimeInterpolator?) {
        this.expandInterpolator = expandInterpolator
    }

    /**
     * Returns the current [TimeInterpolator] for expanding.
     * @return the current interpolator, null by default.
     */
    fun getExpandInterpolator(): TimeInterpolator? {
        return expandInterpolator
    }

    /**
     * Sets a [TimeInterpolator] for collpasing.
     * @param collapseInterpolator the interpolator
     */
    fun setCollapseInterpolator(collapseInterpolator: TimeInterpolator?) {
        this.collapseInterpolator = collapseInterpolator
    }

    /**
     * Returns the current [TimeInterpolator] for collapsing.
     * @return the current interpolator, null by default.
     */
    fun getCollapseInterpolator(): TimeInterpolator? {
        return collapseInterpolator
    }

    /**
     * Is this [ExpandableTextView] expanded or not?
     * @return true if expanded, false if collapsed.
     */
    fun isExpanded(): Boolean {
        return expanded
    }

    //endregion

    //endregion
    /**
     * This method will notify the listener about this view being expanded.
     */
    private fun notifyOnCollapse() {
        for (onExpandListener in onExpandListeners!!) {
            onExpandListener.onCollapse(this)
        }
    }

    /**
     * This method will notify the listener about this view being collapsed.
     */
    private fun notifyOnExpand() {
        for (onExpandListener in onExpandListeners!!) {
            onExpandListener.onExpand(this)
        }
    }

    //region public interfaces

    //region public interfaces
    /**
     * Interface definition for a callback to be invoked when
     * a [ExpandableTextView] is expanded or collapsed.
     */
    interface OnExpandListener {
        /**
         * The [ExpandableTextView] is being expanded.
         * @param view the textview
         */
        fun onExpand(view: ExpandableTextView)

        /**
         * The [ExpandableTextView] is being collapsed.
         * @param view the textview
         */
        fun onCollapse(view: ExpandableTextView)
    }

    /**
     * Simple implementation of the [OnExpandListener] interface with stub
     * implementations of each method. Extend this if you do not intend to override
     * every method of [OnExpandListener].
     */
    class SimpleOnExpandListener : OnExpandListener {
        override fun onExpand(view: ExpandableTextView) {
            // empty implementation
        }

        override fun onCollapse(view: ExpandableTextView) {
            // empty implementation
        }
    }

    //endregion

}