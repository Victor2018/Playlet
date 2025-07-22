package com.victor.module.home.view.holder

import android.view.View
import android.widget.TextView
import com.victor.lib.common.util.ResUtils
import com.victor.lib.common.view.holder.ContentViewHolder
import com.victor.module.home.R

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: HotSearchContentHolder
 * Author: Victor
 * Date: 2022/3/22 14:30
 * Description: 
 * -----------------------------------------------------------------
 */

class HotSearchContentHolder(itemView: View) : ContentViewHolder<String?>(itemView) {

    override fun bindData (data: String?) {
//        itemView.findViewById<TextView>(R.id.mTvTitle).text = data
        setHotPositionIconBg(bindingAdapterPosition,itemView.findViewById(R.id.mTvPosition))
    }

    override fun onClick(view: View) {
        super.onClick(view)
    }

    override fun onLongClick(v: View): Boolean {
        return false
    }

    private fun setHotPositionIconBg(position: Int,mTvPosition: TextView) {
        mTvPosition.text = "${position + 1}"
        when (position) {
            0 -> {
                mTvPosition.background = ResUtils.getDrawableRes(com.victor.lib.common.R.drawable.shape_ff1f1c_lt_br_radius_12)
            }
            1 -> {
                mTvPosition.background = ResUtils.getDrawableRes(com.victor.lib.common.R.drawable.shape_fa7d00_lt_br_radius_12)
            }
            2 -> {
                mTvPosition.background = ResUtils.getDrawableRes(com.victor.lib.common.R.drawable.shape_ffd424_lt_br_radius_12)
            }
            else -> {
                mTvPosition.background = ResUtils.getDrawableRes(com.victor.lib.common.R.drawable.shape_99000000_lt_br_radius_12)
            }
        }
    }
}