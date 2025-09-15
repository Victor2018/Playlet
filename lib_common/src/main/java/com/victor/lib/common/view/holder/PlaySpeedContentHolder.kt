package com.victor.lib.common.view.holder

import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import com.victor.lib.common.R
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

class PlaySpeedContentHolder(itemView: View, listener: AdapterView.OnItemClickListener?) :
    ContentViewHolder<String>(itemView,listener) {

    var playSpeed: String? = null

    override fun bindData(data: String?) {
        val mTvSpeed = itemView.findViewById<TextView>(R.id.mTvSpeed)
        val mIvChecked = itemView.findViewById<ImageView>(R.id.mIvChecked)
        mTvSpeed.text = data
        if (TextUtils.equals(playSpeed,data)) {
            itemView.setBackgroundColor(ResUtils.getColorRes(R.color.color_FAFAFA))
            mTvSpeed.setTextColor(ResUtils.getColorRes(R.color.color_FF1F00))
            mTvSpeed.paint.isFakeBoldText = true
            mIvChecked.show()
        } else {
            itemView.setBackgroundColor(ResUtils.getColorRes(R.color.transparent))
            mTvSpeed.setTextColor(ResUtils.getColorRes(R.color.color_333333))
            mTvSpeed.paint.isFakeBoldText = false
            mIvChecked.hide()
        }
    }

    override fun onLongClick(v: View): Boolean {
        return false
    }
}