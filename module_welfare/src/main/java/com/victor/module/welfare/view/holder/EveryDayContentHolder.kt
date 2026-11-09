package com.victor.module.welfare.view.holder

import android.util.TypedValue
import android.view.View
import android.widget.AdapterView
import com.victor.lib.common.util.ResUtils
import com.victor.lib.common.view.holder.ContentViewHolder
import com.victor.module.welfare.databinding.RvEveryDayCellBinding

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: EveryDayContentHolder
 * Author: Victor
 * Date: 2025/7/23 14:23
 * Description: 
 * -----------------------------------------------------------------
 */
class EveryDayContentHolder(private val binding: RvEveryDayCellBinding, listener: AdapterView.OnItemClickListener?) :
    ContentViewHolder<String>(binding,listener) {

    override fun bindData(data: String?) {
        if (bindingAdapterPosition == 0) {
            binding.mTvCoinValue.setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                ResUtils.getDimenFloatPixRes(com.victor.screen.match.library.R.dimen.dp_36))
            binding.mTvCoinValue.text = "0/$data"
        } else {
            binding.mTvCoinValue.setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                ResUtils.getDimenFloatPixRes(com.victor.screen.match.library.R.dimen.dp_42))
            binding.mTvCoinValue.text = data
        }
    }

    override fun onLongClick(v: View): Boolean {
        return false
    }

}