package com.victor.module.me.view.dialog

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.View.OnClickListener
import android.view.Window
import android.view.WindowManager
import com.victor.lib.common.interfaces.OnDialogOkCancelClickListener
import com.victor.lib.common.util.ScreenUtils
import com.victor.lib.common.view.dialog.AbsDialog
import com.victor.module.me.R
import com.victor.module.me.databinding.DlgCancelAccountTipBinding

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: CancelAccountTipDialog
 * Author: Victor
 * Date: 2023/07/04 17:23
 * Description: 
 * -----------------------------------------------------------------
 */

class CancelAccountTipDialog(context: Context) : AbsDialog<DlgCancelAccountTipBinding>(context,DlgCancelAccountTipBinding::inflate),OnClickListener {

    var mOnDialogOkCancelClickListener: OnDialogOkCancelClickListener? = null


    override fun handleWindow(window: Window) {
        window.setGravity(Gravity.CENTER)
    }

    override fun handleLayoutParams(wlp: WindowManager.LayoutParams?) {
        wlp?.width = ScreenUtils.getWidth(context) * 85 / 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
    }

    fun initialize () {
        binding.mTvConfirm.setOnClickListener(this)
        binding.mTvCancel.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.mTvConfirm -> {
                mOnDialogOkCancelClickListener?.OnDialogOkClick()
                dismiss()
            }
            R.id.mTvCancel -> {
                mOnDialogOkCancelClickListener?.OnDialogCancelClick()
                dismiss()
            }
        }
    }
}