package com.victor.lib.common.view.dialog

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.Window
import android.view.WindowManager
import androidx.viewbinding.ViewBinding
import com.victor.lib.common.R
import com.victor.lib.common.util.ScreenUtils
import com.victor.lib.common.view.dialog.AbsDialog

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

abstract class AbsRightDialog<VB : ViewBinding>
    (context: Context, private val bindingInflater: (LayoutInflater) -> VB): AbsDialog<VB>(context,bindingInflater) {
    override fun handleWindow(window: Window) {
        //右侧弹出
        window.setGravity(Gravity.RIGHT)
    }

    override fun handleLayoutParams(wlp: WindowManager.LayoutParams?) {
        wlp?.width = ScreenUtils.getWidth(context)
        wlp?.windowAnimations = R.style.RightDialogAnimShow
    }
}