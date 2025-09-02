package com.victor.module.me.view.dialog

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.core.widget.NestedScrollView
import com.victor.lib.common.util.ScreenUtils
import com.victor.lib.common.view.dialog.AbsBottomDialog
import com.victor.module.me.databinding.DlgModifyBindPhoneTipBinding
import com.victor.module.me.R
import com.victor.module.me.interfaces.OnModifyBindPhoneListener

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: ModifyBindPhoneTipDialog
 * Author: Victor
 * Date: 2022/4/12 12:10
 * Description: 
 * -----------------------------------------------------------------
 */

class ModifyBindPhoneTipDialog(context: Context) :
    AbsBottomDialog<DlgModifyBindPhoneTipBinding>(context,DlgModifyBindPhoneTipBinding::inflate), View.OnClickListener {

    var mOnEditBindPhoneListener: OnModifyBindPhoneListener? = null
    var phone: String? = null

    override fun handleLayoutParams(wlp: WindowManager.LayoutParams?) {
        wlp?.width = ScreenUtils.getWidth(context)
        wlp?.height = ScreenUtils.getHeight(context) * 8 / 10
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
        initData()
    }

    fun initialize() {
        binding.mTvConfirm.setOnClickListener(this)
        binding.mTvCancel.setOnClickListener(this)

        binding.mNsvContent.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            var count = v.childCount
            if (count > 0) {
                if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                    // 滚动到底部时的操作
                    binding.mTvConfirm.text = "我已阅读完毕且同意协议"
                    binding.mTvConfirm.isEnabled = true
                    binding.mTvCancel.isEnabled = true
                    binding.mTvConfirm.alpha = 1.0f
                    binding.mTvCancel.alpha = 1.0f
                }
            }
        })
    }

    fun initData () {
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.mTvConfirm -> {
                mOnEditBindPhoneListener?.OnModifyBindPhone()
                dismiss()
            }
            R.id.mTvCancel -> {
                dismiss()
            }
        }
    }

}