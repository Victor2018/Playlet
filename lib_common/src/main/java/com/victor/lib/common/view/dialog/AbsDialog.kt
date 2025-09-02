package com.victor.lib.common.view.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Window
import android.view.WindowManager
import androidx.viewbinding.ViewBinding
import com.victor.lib.common.R

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: AbsDialog
 * Author: Victor
 * Date: 2022/3/1 18:28
 * Description: 
 * -----------------------------------------------------------------
 */

abstract class AbsDialog<VB : ViewBinding>(context: Context,private val bindingInflater: (LayoutInflater) -> VB): Dialog(context, R.style.BaseNoTitleDialog) {

    private var _binding: VB? = null
    public val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = bindingInflater(layoutInflater)
        setContentView(binding.root)

        //设置属性信息宽高或者动画
        val window = window
        handleWindow(window!!)
        val wlp = window.attributes
        handleLayoutParams(wlp)
        window.attributes = wlp
        //禁止app录屏和截屏
//        window.addFlags(WindowManager.LayoutParams.FLAG_SECURE)
    }

    /**
     * 用于处理窗口的属性
     *
     * @param window
     */
    abstract fun handleWindow(window: Window)

    abstract fun handleLayoutParams(wlp: WindowManager.LayoutParams?)

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        _binding = null
    }
}