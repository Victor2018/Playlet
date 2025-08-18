package com.victor.lib.common.view.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.ScaleAnimation
import android.widget.FrameLayout
import com.victor.lib.common.databinding.LoadingSeekbarBinding
import com.victor.lib.common.util.ViewUtils.hide
import com.victor.lib.common.util.ViewUtils.invisible
import com.victor.lib.common.util.ViewUtils.show


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
class LoadingSeekBar: FrameLayout {
    private lateinit var binding: LoadingSeekbarBinding

    constructor(context: Context) : this(context,null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs,0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView()
    }

    private fun initView() {
        binding = LoadingSeekbarBinding.inflate(LayoutInflater.from(context), this, true)
    }

    fun startLoadingAnimation() {
        binding.mViewLoading.show()
        /*val scale = ScaleAnimation(
            0.3f, 1f, 1f, 1f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )
        val alpha = AlphaAnimation(1f, 0.2f)
        scale.repeatCount = -1
        alpha.repeatCount = -1
        val set = AnimationSet(true)
        set.addAnimation(scale)
        set.addAnimation(alpha)
        set.duration = 500
        binding.mViewLoading.startAnimation(set)*/
    }

    fun stopLoadingAnimation() {
//        binding.mViewLoading.clearAnimation()
        binding.mViewLoading.invisible()
    }
}