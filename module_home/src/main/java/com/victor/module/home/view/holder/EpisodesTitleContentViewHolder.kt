package com.victor.module.home.view.holder

import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import com.victor.lib.common.util.ResUtils
import com.victor.lib.common.view.holder.ContentViewHolder
import com.victor.module.home.R

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: EpisodesTitleContentViewHolder.java
 * Author: Victor
 * Date: 2018/9/4 9:10
 * Description: 
 * -----------------------------------------------------------------
 */
class EpisodesTitleContentViewHolder(itemView: View, listener: AdapterView.OnItemClickListener?) :
    ContentViewHolder<String>(itemView,listener) {

    var selectPositon : Int = 0

    override fun bindData(data: String?) {
        val mTvTitle = itemView.findViewById<TextView>(R.id.mTvTitle)
        mTvTitle.text = data

        if (bindingAdapterPosition == selectPositon) {
            mTvTitle.setTextColor(ResUtils.getColorRes(com.victor.lib.common.R.color.colorPrimary))
            mTvTitle.background = ResUtils.getDrawableRes(com.victor.lib.common.R.drawable.shape_fff1f2_radius_8)
        } else {
            mTvTitle.setTextColor(ResUtils.getColorRes(com.victor.lib.common.R.color.color_333333))
            mTvTitle.background = ResUtils.getDrawableRes(com.victor.lib.common.R.drawable.shape_f6f6f6_radius_8)
        }

        mTvTitle.setOnClickListener(this)
    }
}