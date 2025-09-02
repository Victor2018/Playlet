package com.victor.lib.common.view.dialog

import android.content.Context
import android.os.Bundle
import android.view.*
import com.victor.lib.common.interfaces.OnDialogOkCancelClickListener
import com.victor.lib.common.R
import com.victor.lib.common.databinding.DlgUpdatePermissionGuideBinding
import com.victor.lib.common.util.ScreenUtils
import com.victor.lib.common.view.dialog.AbsDialog

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: UpdatePermissionGuideDialog
 * Author: Victor
 * Date: 2022/4/19 18:01
 * Description: 
 * -----------------------------------------------------------------
 */

class UpdatePermissionGuideDialog(context: Context) : AbsDialog<DlgUpdatePermissionGuideBinding>
    (context,DlgUpdatePermissionGuideBinding::inflate), View.OnClickListener {

    val TAG = "UpdatePermissionGuideDialog"

    var mOnDialogOkCancelClickListener: OnDialogOkCancelClickListener? = null

    override fun handleWindow(window: Window) {
        window.setGravity(Gravity.CENTER)
    }

    override fun handleLayoutParams(wlp: WindowManager.LayoutParams?) {
        wlp?.width = (ScreenUtils.getWidth(context) * 0.75).toInt()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
    }

    fun initialize() {
        setCanceledOnTouchOutside(false)
        binding.mIvClose.setOnClickListener(this)
        binding.mTvOpenNow.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.mIvClose -> {
                mOnDialogOkCancelClickListener?.OnDialogCancelClick()
                dismiss()
            }
            R.id.mTvOpenNow -> {
                mOnDialogOkCancelClickListener?.OnDialogOkClick()
                dismiss()
            }
        }
    }

}