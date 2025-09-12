package com.victor.module.home.view.holder

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
 * File: EpisodesContentViewHolder.java
 * Author: Victor
 * Date: 2018/9/4 9:10
 * Description: 
 * -----------------------------------------------------------------
 */
class EpisodesContentViewHolder(itemView: View, listener: AdapterView.OnItemClickListener?) :
    ContentViewHolder<Int>(itemView,listener) {

    var selectPositon : Int = 0

    override fun bindData(data: Int?) {
        val mTvEpisodes = itemView.findViewById<TextView>(R.id.mTvEpisodes)
        mTvEpisodes.text = "$data"

        if (bindingAdapterPosition == selectPositon) {
            mTvEpisodes.setTextColor(ResUtils.getColorRes(com.victor.lib.common.R.color.colorPrimary))
            mTvEpisodes.background = ResUtils.getDrawableRes(com.victor.lib.common.R.drawable.border_fff5722_33fff5722_radius_8)
        } else {
            mTvEpisodes.setTextColor(ResUtils.getColorRes(com.victor.lib.common.R.color.color_333333))
            mTvEpisodes.background = ResUtils.getDrawableRes(com.victor.lib.common.R.drawable.border_f6f6f6_33f6f6f6_radius_8)
        }

        mTvEpisodes.setOnClickListener(this)
    }
}