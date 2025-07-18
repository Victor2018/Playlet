package com.victor.lib.common.view.widget.pullleftrefreshlayout

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: AnimView
 * Author: Victor
 * Date: 2023/2/2 15:23
 * Description: 
 * -----------------------------------------------------------------
 */

class AnimView : View {

    var isBezierBackDone = false
    private var mWidth = 0
    private var mHeight = 0
    private var PULL_WIDTH = 0
    private var PULL_DELTA = 0

    private var mStart: Long = 0
    private var mStop: Long = 0
    private var mBezierDeta = 0

    private var bezierBackDur: Long = 0

    private var mBackPaint: Paint? = null
    private var mPath: Path? = null

    private var mAniStatus = AnimatorStatus.PULL_LEFT

    internal enum class AnimatorStatus {
        PULL_LEFT, DRAG_LEFT, RELEASE
    }

    constructor(context: Context) : this(context,null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs,0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    private fun init(context: Context) {
        PULL_WIDTH = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            26f,
            context.resources.displayMetrics
        ).toInt()
        PULL_DELTA = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            80f,
            context.resources.displayMetrics
        ).toInt()
        mPath = Path()
        mBackPaint = Paint()
        mBackPaint?.isAntiAlias = true
        mBackPaint?.style = Paint.Style.FILL
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var widthMeasureSpec = widthMeasureSpec
        val width = MeasureSpec.getSize(widthMeasureSpec)
        if (width > PULL_DELTA + PULL_WIDTH) {
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(
                PULL_DELTA + PULL_WIDTH,
                MeasureSpec.getMode(widthMeasureSpec)
            )
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (changed) {
            mWidth = width
            mHeight = height
            if (mWidth < PULL_WIDTH) {
                mAniStatus = AnimatorStatus.PULL_LEFT
            }
            when (mAniStatus) {
                AnimatorStatus.PULL_LEFT -> if (mWidth >= PULL_WIDTH) {
                    mAniStatus = AnimatorStatus.DRAG_LEFT
                }

                AnimatorStatus.DRAG_LEFT -> TODO()
                AnimatorStatus.RELEASE -> TODO()
            }
        }
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        when (mAniStatus) {
            AnimatorStatus.PULL_LEFT -> mBackPaint?.let {
                canvas.drawRect(
                    0f, 0f, mWidth.toFloat(), mHeight.toFloat(),
                    it
                )
            }
            AnimatorStatus.DRAG_LEFT -> drawDrag(canvas)
            AnimatorStatus.RELEASE -> drawBack(canvas, getBezierDelta())
        }
    }

    private fun drawDrag(canvas: Canvas) {
        canvas.drawRect(
            (mWidth - PULL_WIDTH).toFloat(), 0f, mWidth.toFloat(), mHeight.toFloat(),
            mBackPaint!!
        )
        mPath?.reset()
        mPath?.moveTo((mWidth - PULL_WIDTH).toFloat(), 0f)
        mPath?.quadTo(
            0f,
            (mHeight / 2).toFloat(),
            (mWidth - PULL_WIDTH).toFloat(),
            mHeight.toFloat()
        )
        canvas.drawPath(mPath!!, mBackPaint!!)
    }

    private fun drawBack(canvas: Canvas, delta: Int) {
        mPath?.reset()
        mPath?.moveTo(mWidth.toFloat(), 0f)
        mPath?.lineTo((mWidth - PULL_WIDTH).toFloat(), 0f)
        mPath?.quadTo(
            delta.toFloat(),
            (mHeight / 2).toFloat(),
            (mWidth - PULL_WIDTH).toFloat(),
            mHeight.toFloat()
        )
        mPath?.lineTo(mWidth.toFloat(), mHeight.toFloat())
        canvas.drawPath(mPath!!, mBackPaint!!)
        invalidate()
        if (bezierBackRatio == 1f) {
            isBezierBackDone = true
        }
        if (isBezierBackDone && mWidth <= PULL_WIDTH) {
            drawFooterBack(canvas)
        }
    }

    private fun drawFooterBack(canvas: Canvas) {
        canvas.drawRect(0f, 0f, mWidth.toFloat(), mHeight.toFloat(), mBackPaint!!)
    }

    fun releaseDrag() {
        mAniStatus = AnimatorStatus.RELEASE
        mStart = System.currentTimeMillis()
        mStop = mStart + bezierBackDur
        mBezierDeta = mWidth - PULL_WIDTH
        isBezierBackDone = false
        requestLayout()
    }

    fun setBezierBackDur(bezierBackDur: Long) {
        this.bezierBackDur = bezierBackDur
    }

    private var bezierBackRatio = 0f

    private fun getBezierDelta(): Int {
        bezierBackRatio = getBezierBackRatio()
        return (mBezierDeta * bezierBackRatio).toInt()
    }

    private fun getBezierBackRatio(): Float {
        if (System.currentTimeMillis() >= mStop) {
            return 1f
        }
        val ratio = (System.currentTimeMillis() - mStart) / bezierBackDur.toFloat()
        return Math.min(1f, ratio)
    }

    fun setBgColor(color: Int) {
        mBackPaint?.color = color
    }
}