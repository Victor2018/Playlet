package com.victor.lib.common.view.dialog

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.victor.lib.common.R
import com.victor.lib.common.databinding.DlgPlaySettingsBinding
import com.victor.lib.common.util.ScreenUtils

class PlaySettingsDialog (context: Context) : AbsBottomSheetDialog<DlgPlaySettingsBinding>
    (context,DlgPlaySettingsBinding::inflate),View.OnClickListener {


    override fun handleLayoutParams(wlp: WindowManager.LayoutParams?) {
        wlp?.width = ScreenUtils.getWidth(context)
//        wlp?.height = ScreenUtils.getHeight(context) * 8 / 10
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initData()
    }

    private fun initView() {
        binding.mTvShare.setOnClickListener(this)
        binding.mTvUnLike.setOnClickListener(this)
        binding.mTvReport.setOnClickListener(this)
    }

    private fun initData() {
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.mTvShare -> {
                dismiss()
            }
            R.id.mTvUnLike -> {
                dismiss()
            }
            R.id.mTvReport -> {
                dismiss()
            }
        }
    }

}