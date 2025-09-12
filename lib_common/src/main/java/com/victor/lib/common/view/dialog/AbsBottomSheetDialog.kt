package com.victor.lib.common.view.dialog

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.Window
import android.view.WindowManager
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.victor.lib.common.R


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: AbsBottomDialog
 * Author: Victor
 * Date: 2022/3/1 18:28
 * Description: 
 * -----------------------------------------------------------------
 */

abstract class AbsBottomSheetDialog<VB : ViewBinding>(context: Context,private val bindingInflater: (LayoutInflater) -> VB)
    : BottomSheetDialog(context, R.style.BottomSheetDialog) {

    private var _binding: VB? = null
    public val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = bindingInflater(layoutInflater)
        setContentView(binding.root)

        //设置属性信息宽高或者动画
        handleWindow(window!!)
        val wlp = window!!.attributes
        handleLayoutParams(wlp)
        window!!.attributes = wlp
    }

    /**
     * 用于处理窗口的属性
     *
     * @param window
     */
    fun handleWindow(window: Window) {
        window.setGravity(Gravity.BOTTOM)
    }

    abstract fun handleLayoutParams(wlp: WindowManager.LayoutParams?)

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        _binding = null
    }

}