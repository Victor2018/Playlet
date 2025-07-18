package com.victor.lib.common.view.widget

import android.animation.ValueAnimator
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.victor.lib.common.R
import java.util.*
import kotlin.collections.ArrayList

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: MusicIndicator
 * Author: Victor
 * Date: 2022/6/15 18:45
 * Description: 
 * -----------------------------------------------------------------
 */

class MusicIndicator : View {
    private var paint: Paint? = null
    private var stepNum = 10

    private var duration = 3000
    private var barNum = 3
    private var barColor = -0x1000000
    private var barRadius = 10f

    private var viewHeight = 0
    private var viewWidth = 0

    private var animList: ArrayList<ValueAnimator>? = null
    private var isPause = false

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initAttrs(attrs)
    }

    fun initAttrs (attrs: AttributeSet?) {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.MusicIndicator, 0, 0)
        try {
            barNum = ta.getInt(R.styleable.MusicIndicator_bar_num, 3)
            stepNum = ta.getInt(R.styleable.MusicIndicator_step_num, 10)
            duration = ta.getInt(R.styleable.MusicIndicator_duration, 3000)
            barColor = ta.getColor(R.styleable.MusicIndicator_indicator_bar_color, -0x1000000)
        } finally {
            ta.recycle()
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        val floatList = getGraduateFloatList(stepNum, viewHeight)
        generateAnim(floatList, barNum)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint = Paint()
        paint?.setColor(barColor)
        drawIndicator(canvas, barNum)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        viewWidth = MeasureSpec.getSize(widthMeasureSpec)
        viewHeight = MeasureSpec.getSize(heightMeasureSpec)
        setMeasuredDimension(viewWidth, viewHeight)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    // 1割ずつの数列を作る
    private fun getGraduateFloatList(arraySize: Int, max: Int): MutableList<Float> {
        val floatList: MutableList<Float> = ArrayList()
        val dividedMax = (max / arraySize).toDouble()
        for (i in 1..arraySize) {
            val a = i * dividedMax
            floatList.add(a.toFloat())
        }
        floatList[floatList.size - 1] = floatList[0]
        return floatList
    }

    private fun generateAnim(floatList: MutableList<Float>, barNum: Int) {
        animList = ArrayList()
        for (i in 0 until barNum) {
            Collections.shuffle(floatList)
            floatList[floatList.size - 1] = floatList[0]
            val floatArray = FloatArray(floatList.size)
            var j = 0
            for (f in floatList) {
                floatArray[j++] = f ?: Float.NaN
            }
            var anim = ValueAnimator()
            anim = ValueAnimator.ofFloat(*floatArray)
            anim.duration = duration.toLong()
            anim.repeatCount = ValueAnimator.INFINITE
            anim.addUpdateListener(AnimatorUpdateListener {
                if (isPause) return@AnimatorUpdateListener
                invalidate()
            })
            anim.start()
            animList?.add(anim)
        }
    }


    private fun drawIndicator(canvas: Canvas, barNum: Int) {
        // 一本あたりのすきま
        val spaceWidth = canvas.width * 0.5 / (barNum - 1)
        // 一本のふとさ
        val barWidth = canvas.width * 0.5 / barNum
        val sumWidth = spaceWidth + barWidth
        for (i in 0 until barNum - 1) {
            val height = animList!![i].animatedValue as Float
            /*canvas.drawRect(
                (i * sumWidth).toFloat(),  // left
                canvas.height - height,  //height, // top
                (i * sumWidth + barWidth).toFloat(),  // right
                canvas.height.toFloat(),  // bottom
                paint!! // Paint
            )*/

            var rect = RectF( (i * sumWidth).toFloat(),  // left
                canvas.height - height,  //height, // top
                (i * sumWidth + barWidth).toFloat(),  // right
                canvas.height.toFloat())

            canvas.drawRoundRect(rect,barRadius,barRadius,paint!!)
            if (i == barNum - 2) {
                val heightLast = animList!![i + 1].animatedValue as Float
               /* canvas.drawRect(
                    ((i + 1) * sumWidth).toFloat(),  // left
                    canvas.height - heightLast,  //height, // top
                    ((i + 1) * sumWidth + barWidth).toFloat(),  // right
                    canvas.height.toFloat(),  // bottom
                    paint!! // Paint
                )*/

                var rect = RectF( ((i + 1) * sumWidth).toFloat(),  // left
                    canvas.height - heightLast,  //height, // top
                    ((i + 1) * sumWidth + barWidth).toFloat(),  // right
                    canvas.height.toFloat())

                canvas.drawRoundRect(rect,barRadius,barRadius,paint!!)
            }
        }
    }

    fun setStepNum(stepNum: Int) {
        this.stepNum = stepNum
    }

    fun setDuration(duration: Int) {
        this.duration = duration
    }

    fun setBarNum(barNum: Int) {
        this.barNum = barNum
    }

    fun setBarColor(barColor: Int) {
        this.barColor = barColor
    }

    fun setPause(pause: Boolean) {
        isPause = pause
    }

    fun start() {
        requestLayout()
    }
}