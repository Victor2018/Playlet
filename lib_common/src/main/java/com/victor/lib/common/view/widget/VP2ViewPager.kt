package com.victor.lib.common.view.widget

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager
import com.victor.lib.common.util.Loger

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: VP2ViewPager.kt
 * Author: Victor
 * Date: 2022/3/1 18:28
 * Description: 处理ViewPager2中嵌套ViewPager导致的滑动冲突
 * -----------------------------------------------------------------
 */

class VP2ViewPager(context: Context, attrs: AttributeSet) : ViewPager(context, attrs) {
    val TAG = "VP2ViewPager"
    var mStartLastX = 0
    var mStartLastY = 0
    var disallowParentInterceptTouchEvent = false
    var count = 0

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        var x = ev?.x?.toInt()
        var y = ev?.y?.toInt()

        when (ev?.action) {
            MotionEvent.ACTION_DOWN -> {
                if (x != null) {
                    mStartLastX = x
                }
                if (y != null) {
                    mStartLastY = y
                }
                // 禁止parent拦截down事件
                parent.requestDisallowInterceptTouchEvent(true)
            }
            MotionEvent.ACTION_MOVE -> {
                var deltaX = x?.minus(mStartLastX) ?: 0
                var deltaY = y?.minus(mStartLastY) ?: 0
                Loger.e(TAG,"ACTION_MOVE-deltaX = $deltaX")
                Loger.e(TAG,"ACTION_MOVE-currentItem = $currentItem")
                Loger.e(TAG,"ACTION_MOVE-count = $count")

                if (deltaX < 0) {//从右向左滑
                    if (currentItem == 0) {
                        disallowParentInterceptTouchEvent = false
                    } else {
                        disallowParentInterceptTouchEvent = currentItem >= count -1
                    }
                } else {
                    disallowParentInterceptTouchEvent = false
                }

                if (disallowParentInterceptTouchEvent) {
                    parent.requestDisallowInterceptTouchEvent(false)
                }
            }
            MotionEvent.ACTION_UP -> {
            }
        }
        return super.dispatchTouchEvent(ev)
    }

}