package com.victor.lib.common.view.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.View.OnClickListener
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.widget.AppCompatTextView
import com.victor.lib.common.R
import com.victor.lib.common.util.MainHandler
import com.victor.lib.common.util.ResUtils
import com.victor.lib.common.util.SpannableUtil
import com.victor.lib.common.util.ViewUtils.hide
import com.victor.lib.common.util.ViewUtils.show

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: PlayTipView
 * Author: Victor
 * Date: 2022/6/17 14:07
 * Description: 播放提示view
 * -----------------------------------------------------------------
 */

class PlayTipView : AppCompatTextView, Animation.AnimationListener,OnClickListener {

    var hideDelayTime: Long = 6000
    var tips = ArrayList<String?>()

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        tips.clear()
        setOnClickListener(this)
    }

    fun showPlayTip (tip: String?) {
        if (tip?.contains(tip) == false) {
            tips.add(tip)
        }
        if (visibility == View.GONE) {
            showTip(tip)
        }
    }

    fun showTip (tip: String?) {
        show()
        MainHandler.get().removeCallbacks(hideRunnable)
        setText(tip)
        var anim = AnimationUtils.loadAnimation(context, R.anim.anim_msg_alpha_in)
        anim.fillAfter = true
        anim.setAnimationListener(this)
        startAnimation(anim)
    }

    fun setText (tip: String?) {
        var spanText = "购买"
        SpannableUtil.setSpannableColor(this,
            ResUtils.getColorRes(R.color.color_EB4F3A),tip,spanText)
    }

    override fun onAnimationStart(p0: Animation?) {
        show()
    }

    override fun onAnimationEnd(p0: Animation?) {
        MainHandler.get().postDelayed(hideRunnable,hideDelayTime)
    }

    override fun onAnimationRepeat(p0: Animation?) {
    }

    fun hideRootView () {
        var anim = AnimationUtils.loadAnimation(context,R.anim.anim_msg_alpha_out)
        anim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {
            }

            override fun onAnimationEnd(p0: Animation?) {
                hide()
            }

            override fun onAnimationRepeat(p0: Animation?) {
            }

        })
        startAnimation(anim)
    }

    val hideRunnable = Runnable {
        if (tips.size > 0) {
            tips.removeAt(0)
        }
        if (tips.size > 0) {
            showTip(tips.get(0))
        } else {
            hideRootView()
        }
    }

    fun hideTip () {
        tips.clear()
        MainHandler.get().removeCallbacks(hideRunnable)
        clearAnimation()
        hide()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        MainHandler.get().removeCallbacks(hideRunnable)
    }

    override fun onClick(v: View?) {
        hideTip()
    }
}