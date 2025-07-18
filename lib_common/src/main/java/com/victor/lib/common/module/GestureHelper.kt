package com.victor.lib.common.module

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import com.victor.lib.common.interfaces.OnViewGestureListener
import com.victor.lib.common.util.ScreenUtils

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: GestureHelper
 * Author: Victor
 * Date: 2022/7/25 18:44
 * Description: 播放控手势控制。通过对view的GestureDetector事件做监听，判断水平滑动还是垂直滑动。
 * -----------------------------------------------------------------
 */

class GestureHelper(var context: Context?,var gestureView: View?): View.OnTouchListener,
    GestureDetector.OnDoubleTapListener {

    var isGestureEnable = true

    //是否水平
    private var isInHorizenalGesture = false

    //是否右边垂直
    private var isInRightGesture = false

    //是否左边垂直
    private var isInLeftGesture = false

    //手势决定器
    private var mGestureDetector: GestureDetector? = null

    //手势回调
    var mOnViewGestureListener: OnViewGestureListener? = null

    //当前是否处于分屏模式
    private val mIsMultiWindow = false
    private val mView: View? = null

    fun init () {
        mGestureDetector = GestureDetector(context, mOnGestureListener)
        gestureView?.setOnTouchListener(this)

        //GestureDetector增加双击事件的监听。。里面包含了单击事件
        mGestureDetector?.setOnDoubleTapListener(this)
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                //对结束事件的监听
                mOnViewGestureListener?.onGestureEnd()
                isInLeftGesture = false
                isInRightGesture = false
                isInHorizenalGesture = false
            }
            else -> {
            }
        }
        //其他的事件交给GestureDetector。
        return event?.let { mGestureDetector?.onTouchEvent(it) } ?: false
    }

    override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
        //处理点击事件
        mOnViewGestureListener?.onSingleTap()
        return false
    }

    override fun onDoubleTap(e: MotionEvent): Boolean {
        mOnViewGestureListener?.onDoubleTap()
        return false
    }

    override fun onDoubleTapEvent(e: MotionEvent): Boolean {
        return false
    }


    /**
     * 绑定到GestureDetector的。
     */
    private val mOnGestureListener: GestureDetector.OnGestureListener =
        object : GestureDetector.OnGestureListener {
            private var mXDown = 0f
            override fun onSingleTapUp(e: MotionEvent): Boolean {
                return false
            }

            override fun onShowPress(e: MotionEvent) {}
            override fun onScroll(e1: MotionEvent?,
                                  e2: MotionEvent,
                                  distanceX: Float,
                                  distanceY: Float): Boolean {
                //如果关闭了手势。则不处理。
                if (!isGestureEnable || e1 == null || e2 == null) {
                    return false
                }
                if (Math.abs(distanceX) > Math.abs(distanceY)) {
                    //水平滑动
                    if (!isInLeftGesture && !isInRightGesture) {
                        isInHorizenalGesture = true
                    }
                }
                if (isInHorizenalGesture) {
                    mOnViewGestureListener?.onHorizontalDistance(e1.x, e2.x)
                } else {
                    /*
                    如果是分屏模式,则根据当前展示的View的宽度来计算手势是处于右侧还是左侧
                 */
                    if (mIsMultiWindow) {
                        if (ScreenUtils.isInLeft(mView, mXDown.toInt())) {
                            isInLeftGesture = true
                            mOnViewGestureListener?.onLeftVerticalDistance(e1.y, e2.y)
                        } else if (ScreenUtils.isInRight(mView, mXDown.toInt())) {
                            isInRightGesture = true
                            mOnViewGestureListener?.onRightVerticalDistance(e1.y, e2.y)
                        }
                    } else {
                        if (ScreenUtils.isInLeft(context, mXDown.toInt())) {
                            isInLeftGesture = true
                            mOnViewGestureListener?.onLeftVerticalDistance(e1.y, e2.y)
                        } else if (ScreenUtils.isInRight(context, mXDown.toInt())) {
                            isInRightGesture = true
                            mOnViewGestureListener?.onRightVerticalDistance(e1.y, e2.y)
                        }
                    }
                }
                return true
            }

            override fun onLongPress(e: MotionEvent) {}
            override fun onFling(
                e1: MotionEvent?,
                e2: MotionEvent,
                velocityX: Float,
                velocityY: Float
            ): Boolean {
                return false
            }

            override fun onDown(e: MotionEvent): Boolean {
                mXDown = e.x
                return true
            }
        }
}