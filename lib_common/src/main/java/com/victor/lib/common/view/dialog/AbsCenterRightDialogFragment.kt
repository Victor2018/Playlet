package com.victor.lib.common.view.dialog

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.viewbinding.ViewBinding
import com.victor.lib.common.R


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: AbsCenterRightDialogFragment
 * Author: Victor
 * Date: 2022/4/1 10:07
 * Description: 
 * -----------------------------------------------------------------
 */

abstract class AbsCenterRightDialogFragment<VB : ViewBinding>
    (private val bindingInflater: (LayoutInflater) -> VB) : AbsDialogFragment<VB>(bindingInflater) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initView(rootView: View?) {
        super.initView(rootView)
    }

    /**
     * 用于处理窗口的属性
     * @param window
     */
    override fun handleWindow(window: Window?) {
        super.handleWindow(window)
        //底部弹出
        window?.setGravity(Gravity.RIGHT)
    }

    /**
     * 处理默认的宽和高
     * 和动画效果
     * @param wl
     */
    override fun handleLayoutParams(wl: WindowManager.LayoutParams?) {
        super.handleLayoutParams(wl)
        wl?.windowAnimations = R.style.RightDialogAnimShow
    }
}