package com.victor.lib.common.view.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.viewpager.widget.ViewPager

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: VerticalViewPager
 * Author: Victor
 * Date: 2022/6/14 9:54
 * Description: 
 * -----------------------------------------------------------------
 */

class VerticalViewPager : ViewPager {

    private val MIN_SCALE = 0.90f

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        setPageTransformer(false, DefaultTransformer())
//        setPageTransformer(false, ZoomOutTransformer())
//        setPageTransformer(true, StackTransformer())
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        val intercept = super.onInterceptTouchEvent(swapTouchEvent(ev))
        //If not intercept, touch event should not be swapped.
        swapTouchEvent(ev)
        return intercept
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        return super.onTouchEvent(swapTouchEvent(ev))
    }

    private fun swapTouchEvent(event: MotionEvent?): MotionEvent? {
        val width = width.toFloat()
        val height = height.toFloat()

        var eventY = event?.y ?: 0f
        var eventX = event?.x ?: 0f
        val swappedX = eventY / height * width
        val swappedY = eventX / width * height
        event?.setLocation(swappedX, swappedY)
        return event
    }

    inner class DefaultTransformer : PageTransformer {
        override fun transformPage(view: View, position: Float) {
            var alpha = 0f
            if (0 <= position && position <= 1) {
                alpha = 1 - position
            } else if (-1 < position && position < 0) {
                alpha = position + 1
            }
            view.alpha = alpha
            view.translationX = view.width * -position
            val yPosition = position * view.height
            view.translationY = yPosition
        }
    }

    inner class StackTransformer: PageTransformer{
        override fun transformPage(page: View, position: Float) {
            page.translationX = page.width * -position
            page.translationY = if (position < 0) position * page.height else 0f
        }
    }

    inner class ZoomOutTransformer: PageTransformer{
        override fun transformPage(view: View, position: Float) {
            val pageWidth: Int = view.width
            val pageHeight: Int = view.height
            var alpha = 0f
            if (0 <= position && position <= 1) {
                alpha = 1 - position
            } else if (-1 < position && position < 0) {
                val scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position))
                val verticalMargin = pageHeight * (1 - scaleFactor) / 2
                val horizontalMargin = pageWidth * (1 - scaleFactor) / 2
                if (position < 0) {
                    view.translationX = horizontalMargin - verticalMargin / 2
                } else {
                    view.translationX = -horizontalMargin + verticalMargin / 2
                }
                view.scaleX = scaleFactor
                view.scaleY = scaleFactor
                alpha = position + 1
            }

            view.alpha = alpha
            view.translationX = view.width * -position
            val yPosition: Float = position * view.height
            view.translationY = yPosition
        }
    }

}