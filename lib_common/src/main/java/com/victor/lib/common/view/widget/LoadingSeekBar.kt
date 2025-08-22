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
import android.view.animation.LinearInterpolator
import androidx.appcompat.widget.AppCompatSeekBar
import androidx.core.content.withStyledAttributes
import com.victor.lib.common.R


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: LoadingSeekBar
 * Author: Victor
 * Date: 2025/7/24 17:48
 * Description: 
 * -----------------------------------------------------------------
 */
class LoadingSeekBar: AppCompatSeekBar {

    private val lightPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var lightWidth = 200f
    private var translateX = 0f
    private var animator: ValueAnimator? = null
    private var direction = DIRECTION_LEFT_TO_RIGHT
    private var lightColor = Color.YELLOW
    private var peakAlpha = 180
    private var edgeAlpha = 0
    private var lightDuration = 3000
    private var showLight = true

    companion object {
        const val DIRECTION_LEFT_TO_RIGHT = 0
        const val DIRECTION_RIGHT_TO_LEFT = 1
    }

    constructor(context: Context) : this(context,null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs,0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(attrs)
    }

    fun initView(attrs: AttributeSet?) {
        initAttributes(attrs)
    }

    private fun initAttributes(attrs: AttributeSet?) {
        lightPaint.style = Paint.Style.FILL

        context.withStyledAttributes(
            attrs,
            R.styleable.LoadingSeekBar
        ) {
            direction = getInt(R.styleable.LoadingSeekBar_light_direction, DIRECTION_LEFT_TO_RIGHT)
            lightDuration = getInt(R.styleable.LoadingSeekBar_light_duration, lightDuration)
            lightColor = getColor(R.styleable.LoadingSeekBar_light_color, Color.YELLOW)
            peakAlpha = getInt(R.styleable.LoadingSeekBar_light_peak_alpha, 180)
            lightWidth = getDimension(R.styleable.LoadingSeekBar_light_width, 200f)
            showLight = getBoolean(R.styleable.LoadingSeekBar_show_light, true)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (showLight) {
            startLightAnimation()
        }
    }

    private fun startLightAnimation() {
        stopLightAnimation()

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

    private fun stopLightAnimation() {
        animator?.cancel()
        animator = null
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (showLight) {
            // 获取进度条的位置和尺寸
            val progressDrawable = progressDrawable
            progressDrawable?.let {
                val bounds = it.bounds
                var lightProgress = progress
                if (lightProgress == 0) {
                    lightProgress = max
                }
                val progressWidth = bounds.width() * lightProgress / max

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

                lightPaint.shader = gradient

                // 只在进度条范围内绘制流光效果
                canvas.save()
                canvas.clipRect(bounds.left.toFloat(), bounds.top.toFloat(),
                    bounds.left + progressWidth * 1f, bounds.bottom.toFloat())
                canvas.drawRect(bounds.left.toFloat(), bounds.top.toFloat(),
                    bounds.right.toFloat(), bounds.bottom.toFloat(), lightPaint)
                canvas.restore()
            }
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stopLightAnimation()
    }

    // 设置方向
    fun setLightDirection(direction: Int) {
        this.direction = direction
        startLightAnimation()
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
        startLightAnimation()
    }

    // 显示/隐藏流光效果
    fun showLight(show: Boolean) {
        this.showLight = show
        if (show) {
            startLightAnimation()
        } else {
            stopLightAnimation()
        }
        invalidate()
    }
}