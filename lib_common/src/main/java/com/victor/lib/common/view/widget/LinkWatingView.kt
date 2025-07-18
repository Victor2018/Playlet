package com.victor.lib.common.view.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.victor.lib.common.R


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: LinkWatingView
 * Author: Victor
 * Date: 2022/6/7 18:00
 * Description: 
 * -----------------------------------------------------------------
 */

class LinkWatingView : View {
    private val p: P = P()
    private var mPaint = Paint()
    private var runnable: Runnable? = null

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initAttrs(context,attrs,defStyleAttr)
    }

    private fun initAttrs(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        val array =
            context.obtainStyledAttributes(attrs, R.styleable.LinkWatingView, defStyleAttr, 0)
        p.count = array.getInt(R.styleable.LinkWatingView_count_number, p.count)
        p.space = array.getDimensionPixelSize(R.styleable.LinkWatingView_item_spacing, p.space)
        p.radius = array.getDimensionPixelSize(R.styleable.LinkWatingView_item_radius, p.radius)
        p.floatRadius = array.getDimensionPixelSize(
            R.styleable.LinkWatingView_item_float_radius,
            p.floatRadius
        )
        p.defaultColor =
            array.getColor(R.styleable.LinkWatingView_item_default_color, p.defaultColor)
        p.changeColor =
            array.getColor(R.styleable.LinkWatingView_item_change_color, p.changeColor)
        p.autoPlay = array.getBoolean(R.styleable.LinkWatingView_item_auto_play, p.autoPlay)
        p.delay = array.getInt(R.styleable.LinkWatingView_item_play_delay, p.delay)
        array.recycle()
        mPaint.flags = Paint.ANTI_ALIAS_FLAG
        runnable = Runnable {
            p.current++
            if (p.current === p.count) {
                p.current = 0
            }
            invalidate()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.translate(paddingStart.toFloat(), (p.radius + p.floatRadius).toFloat())
        var x: Int
        for (i in 0 until p.count) {
            x = if (i == 0) p.radius else p.space + p.radius * 2
            if (i == p.current) {
                mPaint.color = p.changeColor
                x += p.floatRadius
                canvas.translate(x.toFloat(), 0f)
                canvas.drawCircle(0f, 0f, (p.radius + p.floatRadius).toFloat(), mPaint)
                canvas.translate(p.floatRadius.toFloat(), 0f)
            } else {
                mPaint.color = p.defaultColor
                canvas.translate(x.toFloat(), 0f)
                canvas.drawCircle(0f, 0f, p.radius.toFloat(), mPaint)
            }
        }
        if (p.autoPlay) {
            removeCallbacks(runnable) //避免onDraw极短时间内被多次执行的时候，导致执行异常
            postDelayed(runnable, p.delay.toLong())
        }
    }

    fun setCount(count: Int) {
        p.count = count
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec))
        invalidate()
    }

    fun getCurrentIndex(): Int {
        return p.current
    }

    fun setCurrentIndex(index: Int) {
        if (index < 0 || index > p.count) {
            throw IndexOutOfBoundsException("index must be >=0 and < count")
        }
        p.current = index
        invalidate()
    }

    fun setAuto(auto: Boolean) {
        p.autoPlay = auto
        invalidate()
    }

    var widthMeasureSpec = 0
    var heightMeasureSpec = 0

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        this.widthMeasureSpec = widthMeasureSpec
        this.heightMeasureSpec = heightMeasureSpec
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec))
    }

    private fun measureWidth(measureSpec: Int): Int {
        val specMode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)
        return if (specMode == MeasureSpec.EXACTLY) specSize else paddingStart + paddingEnd + p.radius * 2 * p.count + p.space * (p.count - 1) + p.floatRadius * 2
    }

    private fun measureHeight(measureSpec: Int): Int {
        val specMode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)
        return if (specMode == MeasureSpec.EXACTLY) specSize else paddingTop + paddingBottom + (p.radius + p.floatRadius) * 2
    }

    private class P {
        var count = 3
        var space = 4
        var radius = 4
        var defaultColor: Int = Color.BLUE
        var changeColor: Int = Color.RED
        var current = 0
        var autoPlay = true
        var delay = 500
        var floatRadius = 2
    }
}