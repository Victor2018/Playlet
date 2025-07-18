package com.victor.lib.common.view.widget.pullleftrefreshlayout

import android.animation.Animator
import android.animation.ValueAnimator
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.content.Context
import android.graphics.Color
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.view.*
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import com.victor.lib.common.R

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: PullLeftToRefreshLayout
 * Author: Victor
 * Date: 2023/2/2 15:27
 * Description: 
 * -----------------------------------------------------------------
 */

class PullLeftToRefreshLayout : FrameLayout {

    companion object {
        const val BACK_ANIM_DUR: Long = 500
        const val BEZIER_BACK_DUR: Long = 350
        const val ROTATION_ANIM_DUR = 200

    }

    /**
     * MoreView移动的最大距离
     */
    var MORE_VIEW_MOVE_DIMEN = 0f
    var ROTATION_ANGLE = 180
    val ANIMATION_INTERPOLATOR = LinearInterpolator()

    var SCAN_MORE: String? = null
    var RELEASE_SCAN_MORE: String? = null

    private var mTouchStartX = 0f
    private var mTouchCurX = 0f

    private var mPullWidth = 0f
    private var mFooterWidth = 0f

    /**
     * 目的是为了将moreView隐藏以便滑动
     */
    private var moreViewMarginRight = 0
    private var footerViewBgColor = 0

    private var isRefresh = false
    private var scrollState = false

    private var mChildView: View? = null
    private var footerView: AnimView? = null
    private var moreView: View? = null
    private var moreText: TextView? = null
    private var arrowIv: ImageView? = null

    private var mBackAnimator: ValueAnimator? = null
    private var mArrowRotateAnim: RotateAnimation? = null
    private var mArrowRotateBackAnim: RotateAnimation? = null

    var onScrollListener: OnScrollListener? = null
    var mOnRefreshListener: OnRefreshListener? = null

    val interpolator = DecelerateInterpolator(10f)

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        mPullWidth = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            80f,
            context.resources.displayMetrics
        )
        MORE_VIEW_MOVE_DIMEN =
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 20f, context.resources.displayMetrics
            )
        mFooterWidth = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            20f,
            context.resources.displayMetrics
        )
        moreViewMarginRight = -resources.getDimensionPixelSize(com.victor.screen.match.library.R.dimen.dp_26)
        SCAN_MORE = resources.getString(R.string.scan_more)
        RELEASE_SCAN_MORE = resources.getString(R.string.release_scan_more)
        val ta = context.obtainStyledAttributes(attrs, R.styleable.PullLeftToRefreshLayout)
        footerViewBgColor =
            ta.getColor(R.styleable.PullLeftToRefreshLayout_footerBgColor, Color.rgb(243, 242, 242))
        ta.recycle()
        post {
            mChildView = getChildAt(0)
            addFooterView()
            addMoreView()
            initBackAnim()
            initRotateAnim()
        }
    }

    private fun addFooterView() {
        val params = LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT)
        params.gravity = Gravity.RIGHT
        footerView = AnimView(context)
        footerView?.layoutParams = params
        footerView?.setBgColor(footerViewBgColor)
        footerView?.setBezierBackDur(BEZIER_BACK_DUR)
        addViewInternal(footerView)
    }

    private fun addMoreView() {
        val params =
            LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        params.gravity = Gravity.RIGHT or Gravity.CENTER_VERTICAL
        params.setMargins(0, 0, moreViewMarginRight, 0)
        moreView = LayoutInflater.from(context).inflate(R.layout.item_load_more, this, false)
        moreView?.layoutParams = params
        moreText = moreView!!.findViewById<View>(R.id.tvMoreText) as TextView
        arrowIv = moreView!!.findViewById<View>(R.id.ivRefreshArrow) as ImageView
        addViewInternal(moreView!!)
    }

    private fun initRotateAnim() {
        val pivotType = Animation.RELATIVE_TO_SELF
        val pivotValue = 0.5f
        mArrowRotateAnim = RotateAnimation(
            0f,
            ROTATION_ANGLE.toFloat(),
            pivotType,
            pivotValue,
            pivotType,
            pivotValue
        )
        mArrowRotateAnim?.setInterpolator(ANIMATION_INTERPOLATOR)
        mArrowRotateAnim?.setDuration(ROTATION_ANIM_DUR.toLong())
        mArrowRotateAnim?.setFillAfter(true)
        mArrowRotateBackAnim = RotateAnimation(
            ROTATION_ANGLE.toFloat(),
            0f,
            pivotType,
            pivotValue,
            pivotType,
            pivotValue
        )
        mArrowRotateBackAnim?.setInterpolator(ANIMATION_INTERPOLATOR)
        mArrowRotateBackAnim?.setDuration(ROTATION_ANIM_DUR.toLong())
        mArrowRotateBackAnim?.setFillAfter(true)
    }

    private fun initBackAnim() {
        if (mChildView == null) {
            return
        }
        mBackAnimator = ValueAnimator.ofFloat(mPullWidth, 0f)
        mBackAnimator?.addListener(AnimListener())
        mBackAnimator?.addUpdateListener(AnimatorUpdateListener { animation ->
            var `val` = animation.animatedValue as Float
            if (`val` <= mFooterWidth) {
                `val` = interpolator.getInterpolation(`val` / mFooterWidth) * `val`
                footerView!!.layoutParams.width = `val`.toInt()
                footerView!!.requestLayout()
            }
            mChildView?.setTranslationX(-`val`)
            moveMoreView(`val`, true)
        })
        mBackAnimator?.setDuration(BACK_ANIM_DUR)
    }

    private fun addViewInternal(child: View?) {
        super.addView(child)
    }

    override fun addView(child: View) {
        if (childCount >= 1) {
            throw RuntimeException("you can only attach one child")
        }
        mChildView = child
        super.addView(child)
    }


    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        if (isRefresh) {
            return true
        }
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                mTouchStartX = ev.x
                mTouchCurX = mTouchStartX
                setScrollState(false)
            }
            MotionEvent.ACTION_MOVE -> {
                val curX = ev.x
                val dx = curX - mTouchStartX
                if (dx < -10 && !canScrollRight()) { //点击事件要传回子类
                    setScrollState(true)
                    return true
                }
            }
        }
        return super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (isRefresh) {
            return super.onTouchEvent(event)
        }
        when (event.action) {
            MotionEvent.ACTION_MOVE -> {
                mTouchCurX = event.x
                var dx = mTouchStartX - mTouchCurX
                dx = Math.min(mPullWidth * 2, dx)
                dx = Math.max(0f, dx)
                if (mChildView == null || dx <= 0) {
                    return true
                }
                val unit = dx / 2
                val offsetx = interpolator.getInterpolation(unit / mPullWidth) * unit
                mChildView?.setTranslationX(-offsetx)
                footerView!!.layoutParams.width = offsetx.toInt()
                footerView!!.requestLayout()
                moveMoreView(offsetx, false)
                return true
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                if (mChildView == null) {
                    return true
                }
                val childDx = Math.abs(mChildView?.getTranslationX() ?: 0f )
                if (childDx >= mFooterWidth) {
                    mBackAnimator!!.setFloatValues(childDx, 0f)
                    mBackAnimator!!.start()
                    footerView!!.releaseDrag()
                    if (reachReleasePoint()) {
                        isRefresh = true
                    }
                } else {
                    mBackAnimator!!.setFloatValues(childDx, 0f)
                    mBackAnimator!!.start()
                }
                setScrollState(false)
                return true
            }
        }
        return super.onTouchEvent(event)
    }

    /**
     * direction Negative to check scrolling left, positive to check scrolling right
     *
     * @return
     */
    private fun canScrollRight(): Boolean {
        return if (mChildView == null) {
            false
        } else ViewCompat.canScrollHorizontally(mChildView, 1)
    }

    private fun moveMoreView(offsetx: Float, release: Boolean) {
        val dx = offsetx / 2
        if (dx <= MORE_VIEW_MOVE_DIMEN) {
            moreView!!.translationX = -dx
            if (!release && switchMoreText(SCAN_MORE)) {
                arrowIv!!.clearAnimation()
                arrowIv!!.startAnimation(mArrowRotateBackAnim)
            }
        } else {
            if (switchMoreText(RELEASE_SCAN_MORE)) {
                arrowIv!!.clearAnimation()
                arrowIv!!.startAnimation(mArrowRotateAnim)
            }
        }
    }

    private fun switchMoreText(text: String?): Boolean {
        if (TextUtils.equals(text, moreText?.text.toString())) {
            return false
        }
        moreText!!.text = text
        return true
    }

    private fun reachReleasePoint(): Boolean {
        return RELEASE_SCAN_MORE == moreText?.text.toString()
    }

    inner class AnimListener : Animator.AnimatorListener {
        override fun onAnimationStart(animator: Animator) {}
        override fun onAnimationEnd(animator: Animator) {
            if (isRefresh) {
                mOnRefreshListener?.onRefresh()
            }
            moreText?.text = SCAN_MORE
            arrowIv?.clearAnimation()
            isRefresh = false
        }

        override fun onAnimationCancel(animator: Animator) {}
        override fun onAnimationRepeat(animator: Animator) {}
    }

    private fun setScrollState(scrollState: Boolean) {
        if (this.scrollState == scrollState) {
            return
        }
        this.scrollState = scrollState
        if (onScrollListener != null) {
            onScrollListener!!.onScrollChange(scrollState)
        }
    }


    interface OnRefreshListener {
        fun onRefresh()
    }


}