package com.victor.lib.common.util

import android.animation.Animator
import android.content.Context
import android.os.Build
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.Interpolator
import androidx.annotation.RequiresApi
import com.victor.lib.common.R
import com.victor.lib.common.app.App

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: AnimUtil
 * Author: Victor
 * Date: 2022/3/1 18:28
 * Description: 
 * -----------------------------------------------------------------
 */

object AnimUtil {
    private var fastOutSlowIn: Interpolator? = null
    private var fastOutLinearIn: Interpolator? = null

    fun topEnter(): Animation {
        val animation = AnimationUtils.loadAnimation(App.get(), R.anim.anim_top_enter)
        animation.fillAfter = true
        return animation
    }
    fun bottomExit(): Animation {
        val animation = AnimationUtils.loadAnimation(App.get(), R.anim.anim_bottom_exit)
        animation.fillAfter = true
        return animation
    }
    fun bottomEnter(): Animation {
        val animation = AnimationUtils.loadAnimation(App.get(), R.anim.anim_bottom_enter)
        animation.fillAfter = true
        return animation
    }
    fun topExit(): Animation {
        val animation = AnimationUtils.loadAnimation(App.get(), R.anim.anim_top_exit)
        animation.fillAfter = true
        return animation
    }
    fun likeScale(): Animation {
        val animation = AnimationUtils.loadAnimation(App.get(), R.anim.anim_like_scale)
        return animation
    }
    fun xShake(): Animation {
        val animation = AnimationUtils.loadAnimation(App.get(), R.anim.anim_x_shake)
        return animation
    }

    fun concatAnimators(animators: Array<Animator>, alphaAnimator: Animator): Array<Animator?> {
        val allAnimators = arrayOfNulls<Animator>(animators.size + 1)
        var i = 0

        for (animator in animators) {
            allAnimators[i] = animator
            ++i
        }
        allAnimators[allAnimators.size - 1] = alphaAnimator
        return allAnimators
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun getFastOutSlowInInterpolator(context: Context): Interpolator? {
        if (fastOutSlowIn == null) {
            fastOutSlowIn = AnimationUtils.loadInterpolator(context, android.R.interpolator.fast_out_slow_in)
        }
        return fastOutSlowIn
    }
}