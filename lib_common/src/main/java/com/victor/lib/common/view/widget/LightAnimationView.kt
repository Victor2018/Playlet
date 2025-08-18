package com.victor.lib.common.view.widget

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import com.victor.lib.common.R


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: LightAnimationView
 * Author: Victor
 * Date: 2025/8/18 16:20
 * Description: 
 * -----------------------------------------------------------------
 */
class LightAnimationView : View {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var lightWidth = 200f
    private var translateX = 0f
    private var animator: ValueAnimator? = null
    private var direction = DIRECTION_LEFT_TO_RIGHT
    private var lightColor = Color.YELLOW
    private var peakAlpha = 180 // 中心最高透明度
    private var edgeAlpha = 0   // 边缘透明度
    private var lightDuration = 3000 // 光带动画时长

    companion object {
        const val DIRECTION_LEFT_TO_RIGHT = 0
        const val DIRECTION_RIGHT_TO_LEFT = 1
    }

    constructor(context: Context) : this(context,null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs,0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(attrs)
    }

    private fun initView(attrs: AttributeSet?) {
        paint.style = Paint.Style.FILL

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.LightAnimationView)
        direction = typedArray.getInt(R.styleable.LightAnimationView_light_direction, DIRECTION_LEFT_TO_RIGHT)
        lightDuration = typedArray.getInt(R.styleable.LightAnimationView_light_duration, lightDuration)
        lightColor = typedArray.getColor(R.styleable.LightAnimationView_light_color, Color.YELLOW)
        peakAlpha = typedArray.getInt(R.styleable.LightAnimationView_light_peak_alpha, 180)
        lightWidth = typedArray.getDimension(R.styleable.LightAnimationView_light_width, 200f)
        typedArray.recycle()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        startAnimation()
    }

    fun startAnimation() {
        stopAnimation()

        val startValue = if (direction == DIRECTION_LEFT_TO_RIGHT) -lightWidth else width + lightWidth
        val endValue = if (direction == DIRECTION_LEFT_TO_RIGHT) width + lightWidth else -lightWidth

        animator = ValueAnimator.ofFloat(startValue, endValue).apply {
            duration = lightDuration.toLong()
            repeatCount = ValueAnimator.INFINITE
            interpolator = LinearInterpolator()
            addUpdateListener { animation ->
                translateX = animation.animatedValue as Float
                invalidate()
            }
            start()
        }
    }

    fun stopAnimation() {
        animator?.cancel()
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // 创建光晕渐变效果
        val gradient = LinearGradient(
            translateX - lightWidth/2, 0f,
            translateX + lightWidth/2, 0f,
            intArrayOf(
                Color.argb(edgeAlpha, Color.red(lightColor), Color.green(lightColor), Color.blue(lightColor)),
                Color.argb(peakAlpha, Color.red(lightColor), Color.green(lightColor), Color.blue(lightColor)),
                Color.argb(edgeAlpha, Color.red(lightColor), Color.green(lightColor), Color.blue(lightColor))
            ),
            floatArrayOf(0f, 0.5f, 1f),
            Shader.TileMode.CLAMP
        )

        paint.shader = gradient
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stopAnimation()
    }

    // 设置方向
    fun setDirection(direction: Int) {
        this.direction = direction
        startAnimation()
    }

    // 设置灯光颜色
    fun setLightColor(color: Int) {
        this.lightColor = color
        invalidate()
    }

    // 设置中心透明度
    fun setPeakAlpha(alpha: Int) {
        this.peakAlpha = alpha
        invalidate()
    }

    // 设置灯光宽度
    fun setLightWidth(width: Float) {
        this.lightWidth = width
        startAnimation()
    }
}