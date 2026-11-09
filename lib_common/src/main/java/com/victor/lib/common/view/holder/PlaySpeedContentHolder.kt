package com.victor.lib.common.view.holder

import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import com.victor.lib.common.R
import com.victor.lib.common.databinding.RvPlaySpeedCellBinding
import com.victor.lib.common.util.ResUtils
import com.victor.lib.common.util.ViewUtils.hide
import com.victor.lib.common.util.ViewUtils.show

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: PlaySpeedContentHolder
 * Author: Victor
 * Date: 2022/6/6 11:46
 * Description: 
 * -----------------------------------------------------------------
 */

class PlaySpeedContentHolder(private val binding: RvPlaySpeedCellBinding, listener: AdapterView.OnItemClickListener?) :
    ContentViewHolder<String>(binding,listener) {

    var playSpeed: String? = null

    override fun bindData(data: String?) {
        binding.mTvSpeed.text = data
        if (TextUtils.equals(playSpeed,data)) {
            itemView.setBackgroundColor(ResUtils.getColorRes(R.color.color_FAFAFA))
            binding.mTvSpeed.setTextColor(ResUtils.getColorRes(R.color.color_FF1F00))
            binding.mTvSpeed.paint.isFakeBoldText = true
            binding.mIvChecked.show()
        } else {
            itemView.setBackgroundColor(ResUtils.getColorRes(R.color.transparent))
            binding.mTvSpeed.setTextColor(ResUtils.getColorRes(R.color.color_333333))
            binding.mTvSpeed.paint.isFakeBoldText = false
            binding.mIvChecked.hide()
        }
    }

    override fun onLongClick(v: View): Boolean {
        return false
    }
}