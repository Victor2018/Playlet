package com.victor.lib.common.view.widget.banner

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.AdapterView
import android.widget.TextView
import android.widget.ViewFlipper
import androidx.annotation.AnimRes
import androidx.annotation.FontRes
import androidx.core.content.res.ResourcesCompat
import com.victor.lib.common.R
import com.victor.lib.common.util.DensityUtil
import com.victor.lib.common.util.ImageUtils
import com.victor.lib.common.util.Loger
import com.victor.lib.common.util.ViewUtils
import com.victor.lib.common.view.widget.kenburnsview.KenBurnsView
import com.victor.lib.coremodel.data.remote.entity.bean.HomeItemInfo

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: BannerViewFlipper
 * Author: Victor
 * Date: 2020/8/5 下午 03:33
 * Description: 
 * -----------------------------------------------------------------
 */
class BannerViewFlipper: ViewFlipper {
    val TAG = "BannerViewFlipper"
    companion object {
        val ON_BANNER_ITEM_CLICK:Long = 6
        val ON_BANNER_ITEM_SELECT:Long = 7
    }

    var interval = 3000
    var hasSetAnimDuration = false
    var animDuration = 1000
    var textSize = 14
    var textColor = -0x1000000
    var singleLine = false

    var gravity = Gravity.LEFT or Gravity.CENTER_VERTICAL
    var GRAVITY_LEFT = 0
    var GRAVITY_CENTER = 1
    var GRAVITY_RIGHT = 2
    val DIRECTION_BOTTOM_TO_TOP = 0

    val DIRECTION_TOP_TO_BOTTOM = 1
    val DIRECTION_RIGHT_TO_LEFT = 2
    val DIRECTION_LEFT_TO_RIGHT = 3

    var direction = DIRECTION_BOTTOM_TO_TOP

    var typeface: Typeface? = null
    var isAnimStart = false

    @AnimRes
    var inAnimResId: Int = R.anim.anim_bottom_in

    @AnimRes
    var outAnimResId: Int = R.anim.anim_top_out

    var position = 0
    var messages: List<HomeItemInfo> = ArrayList()
    var onItemClickListener: AdapterView.OnItemClickListener? = null

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs, 0);
    }

    private fun init(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) {
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.MarqueeViewStyle, defStyleAttr, 0)
        interval = typedArray.getInteger(R.styleable.MarqueeViewStyle_mvInterval, interval)
        hasSetAnimDuration = typedArray.hasValue(R.styleable.MarqueeViewStyle_mvAnimDuration)
        animDuration =
            typedArray.getInteger(R.styleable.MarqueeViewStyle_mvAnimDuration, animDuration)
        singleLine = typedArray.getBoolean(R.styleable.MarqueeViewStyle_mvSingleLine, false)
        if (typedArray.hasValue(R.styleable.MarqueeViewStyle_mvTextSize)) {
            textSize = typedArray.getDimension(
                R.styleable.MarqueeViewStyle_mvTextSize,
                textSize.toFloat()
            ).toInt()
            textSize = DensityUtil.px2sp(context, textSize.toFloat())
        }
        textColor = typedArray.getColor(R.styleable.MarqueeViewStyle_mvTextColor, textColor)
        @FontRes val fontRes = typedArray.getResourceId(R.styleable.MarqueeViewStyle_mvFont, 0)
        if (fontRes != 0) {
            typeface = ResourcesCompat.getFont(context, fontRes)
        }
        val gravityType =
            typedArray.getInt(R.styleable.MarqueeViewStyle_mvGravity, GRAVITY_LEFT)
        when (gravityType) {
            GRAVITY_LEFT -> gravity = Gravity.LEFT or Gravity.CENTER_VERTICAL
            GRAVITY_CENTER -> gravity = Gravity.CENTER
            GRAVITY_RIGHT -> gravity = Gravity.RIGHT or Gravity.CENTER_VERTICAL
        }
        if (typedArray.hasValue(R.styleable.MarqueeViewStyle_mvDirection)) {
            direction = typedArray.getInt(R.styleable.MarqueeViewStyle_mvDirection, direction)
            when (direction) {
                DIRECTION_BOTTOM_TO_TOP -> {
                    inAnimResId = R.anim.anim_bottom_in
                    outAnimResId = R.anim.anim_top_out
                }
                DIRECTION_TOP_TO_BOTTOM -> {
                    inAnimResId = R.anim.anim_top_in
                    outAnimResId = R.anim.anim_bottom_out
                }
                DIRECTION_RIGHT_TO_LEFT -> {
                    inAnimResId = R.anim.anim_right_in
                    outAnimResId = R.anim.anim_left_out
                }
                DIRECTION_LEFT_TO_RIGHT -> {
                    inAnimResId = R.anim.anim_left_in
                    outAnimResId = R.anim.anim_right_out
                }
            }
            inAnimResId = android.R.anim.fade_in
            outAnimResId = android.R.anim.fade_out
        } else {
            inAnimResId = R.anim.anim_bottom_in
            outAnimResId = R.anim.anim_top_out
        }
        typedArray.recycle()
        flipInterval = interval
    }

    /**
     * 根据字符串列表，启动翻页公告
     *
     * @param messages 字符串列表
     */
    fun startWithList(messages: List<HomeItemInfo>?) {
        startWithList(messages, inAnimResId, outAnimResId)
    }

    /**
     * 根据字符串列表，启动翻页公告
     *
     * @param messages     字符串列表
     * @param inAnimResId  进入动画的resID
     * @param outAnimResID 离开动画的resID
     */
    fun startWithList(
        messages: List<HomeItemInfo>?,
        @AnimRes inAnimResId: Int,
        @AnimRes outAnimResID: Int
    ) {
        if (messages == null || messages.size == 0) return
        this.messages = messages
        postStart(inAnimResId, outAnimResID)
    }

    private fun postStart(@AnimRes inAnimResId: Int, @AnimRes outAnimResID: Int) {
        post { start(inAnimResId, outAnimResID) }
    }

    private fun start(@AnimRes inAnimResId: Int, @AnimRes outAnimResID: Int) {
        removeAllViews()
        clearAnimation()
        // 检测数据源
        if (messages == null || messages.isEmpty()) {
            throw RuntimeException("The messages cannot be empty!")
        }
        position = 0
        addView(getBannerView(messages[position]))
        if (messages.size > 1) {
            setInAndOutAnimation(inAnimResId, outAnimResID)
            startFlipping()
        }
        if (inAnimation != null) {
            inAnimation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {
                    if (isAnimStart) {
                        animation.cancel()
                    }
                    isAnimStart = true
                }

                override fun onAnimationEnd(animation: Animation) {
                    position++
                    if (position >= messages.size) {
                        position = 0
                    }
                    val view: View = getBannerView(messages[position])
                    if (view.parent == null) {
                        addView(view)
                    }
                    isAnimStart = false

                    if (onItemClickListener != null) {
                        onItemClickListener!!.onItemClick(null,getChildAt(position),getCurrentPosition(), ON_BANNER_ITEM_SELECT)
                    }
                }

                override fun onAnimationRepeat(animation: Animation) {}
            })
        }
    }

    private fun getBannerView(marqueeItem: HomeItemInfo): View {
        val rootView = ViewUtils.getViewByLayout(context,R.layout.banner_item)
        val mIvPoster = rootView.findViewById<KenBurnsView>(R.id.mIvPoster)
        val mTvTitle = rootView.findViewById<TextView>(R.id.mTvTitle)
        rootView.setOnClickListener {v ->
            onItemClickListener?.onItemClick(null,v,getCurrentPosition(), ON_BANNER_ITEM_CLICK)
        }

        ImageUtils.instance.loadImage(context!!,mIvPoster,marqueeItem.data?.content?.data?.cover?.feed)
        mTvTitle.text = marqueeItem.data?.content?.data?.title
        return rootView
    }

    /**
     * 设置进入动画和离开动画
     *
     * @param inAnimResId  进入动画的resID
     * @param outAnimResID 离开动画的resID
     */
    private fun setInAndOutAnimation(
        @AnimRes inAnimResId: Int,
        @AnimRes outAnimResID: Int
    ) {
        val inAnim: Animation = AnimationUtils.loadAnimation(context, inAnimResId)
        if (hasSetAnimDuration) inAnim.duration = animDuration.toLong()
        inAnimation = inAnim
        val outAnim: Animation = AnimationUtils.loadAnimation(context, outAnimResID)
        if (hasSetAnimDuration) outAnim.duration = animDuration.toLong()
        outAnimation = outAnim
    }

    fun getCurrentPosition(): Int {
        Loger.e(TAG,"position = " + position)
        var index = position -1;
        if (index < 0) {
            index = messages.size - 1
        }
        return index
    }

}