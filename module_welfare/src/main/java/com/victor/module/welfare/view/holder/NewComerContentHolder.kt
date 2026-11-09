package com.victor.module.welfare.view.holder

import android.view.View
import android.widget.AdapterView
import com.victor.lib.common.view.holder.ContentViewHolder
import com.victor.module.welfare.databinding.RvNewComerCellBinding

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: NewComerContentHolder
 * Author: Victor
 * Date: 2025/7/23 14:23
 * Description: 
 * -----------------------------------------------------------------
 */
class NewComerContentHolder(private val binding: RvNewComerCellBinding, listener: AdapterView.OnItemClickListener?) :
    ContentViewHolder<String>(binding,listener) {

    override fun bindData(data: String?) {
        if (bindingAdapterPosition == 0) {
            binding.mTvCoinValue.text = "0/$data"
            binding.mTvDay.text = "今天"
        } else if (bindingAdapterPosition == 1) {
            binding.mTvCoinValue.text = data
            binding.mTvDay.text = "明天"
        } else {
            binding.mTvCoinValue.text = data
            binding.mTvDay.text = "第${bindingAdapterPosition + 1}天"
        }
    }

    override fun onLongClick(v: View): Boolean {
        return false
    }

}