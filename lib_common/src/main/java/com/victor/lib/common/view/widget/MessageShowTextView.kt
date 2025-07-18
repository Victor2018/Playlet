package com.victor.lib.common.view.widget

import android.content.Context
import android.text.style.ImageSpan
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.widget.AppCompatTextView
import com.victor.lib.common.R
import com.victor.lib.common.util.MainHandler
import com.victor.lib.common.util.ViewUtils.hide
import com.victor.lib.common.util.ViewUtils.show
import com.victor.lib.common.util.emoji.MoonUtil

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: MessageShowTextView
 * Author: Victor
 * Date: 2022/5/31 20:00
 * Description: 
 * -----------------------------------------------------------------
 */

class MessageShowTextView : AppCompatTextView,Animation.AnimationListener {

    var mRootView: View? = null

    constructor(context: Context) : this(context,null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs,0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
    }

    fun showMessage (msg: String?,rootView: View?) {
        MainHandler.get().removeCallbacks(hideRunnable)
        mRootView = rootView
        MoonUtil.identifyFaceExpression(context,this,msg, ImageSpan.ALIGN_BOTTOM)
        var anim = AnimationUtils.loadAnimation(context, R.anim.anim_message_show)
        anim.fillAfter = true
        anim.setAnimationListener(this)
        startAnimation(anim)
    }

    override fun isFocused(): Boolean {
        return true
    }

    override fun onAnimationStart(p0: Animation?) {
        mRootView?.show()
    }

    override fun onAnimationEnd(p0: Animation?) {
        MainHandler.get().postDelayed(hideRunnable,5000)
    }

    override fun onAnimationRepeat(p0: Animation?) {
    }

    fun hideRootView () {
        var anim = AnimationUtils.loadAnimation(context,R.anim.anim_msg_alpha_out)
        anim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {
            }

            override fun onAnimationEnd(p0: Animation?) {
                mRootView?.hide()
            }

            override fun onAnimationRepeat(p0: Animation?) {
            }

        })
        mRootView?.startAnimation(anim)
    }

    val hideRunnable = Runnable {
        hideRootView()
    }

}