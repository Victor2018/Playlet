package com.victor.lib.common.view.adapter.anim

import android.animation.Animator
import android.animation.ObjectAnimator
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: SlideInBottomAnimatorAdapter.java
 * Author: Victor
 * Date: 2022/3/1 18:28
 * Description:
 * -----------------------------------------------------------------
 */

class SlideInBottomAnimatorAdapter <T: RecyclerView.ViewHolder>
(var adapter: RecyclerView.Adapter<T>?, var recyclerView: RecyclerView): AnimatorAdapter<T>(adapter,recyclerView) {
    private val TRANSLATION_Y = "translationY"

    override fun getAnimators(view: View): Array<Animator> {
        var anim = ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, (mRecyclerView.measuredHeight shr 1).toFloat(), 0f)
        return arrayOf(anim)
    }

}