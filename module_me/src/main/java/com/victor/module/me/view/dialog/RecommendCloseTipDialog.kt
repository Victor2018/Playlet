package com.victor.module.me.view.dialog

import android.content.Context
import android.os.Bundle
import android.view.View
import com.victor.lib.common.interfaces.OnDialogOkCancelClickListener
import com.victor.lib.common.view.dialog.AbsBottomDialog
import com.victor.module.me.R
import com.victor.module.me.databinding.DlgRecommendCloseTipBinding

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: RecommendCloseTipDialog
 * Author: Victor
 * Date: 2023/08/18 14:23
 * Description: 
 * -----------------------------------------------------------------
 */

class RecommendCloseTipDialog(context: Context) :
    AbsBottomDialog<DlgRecommendCloseTipBinding>(context,DlgRecommendCloseTipBinding::inflate), View.OnClickListener {

    var mOnDialogOkCancelClickListener: OnDialogOkCancelClickListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
    }

    fun initialize () {
        setCanceledOnTouchOutside(false)
        binding.mIvClose.setOnClickListener(this)
        binding.mTvConfirm.setOnClickListener(this)
        binding.mTvCancel.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.mIvClose -> {
                mOnDialogOkCancelClickListener?.OnDialogCancelClick()
                dismiss()
            }
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