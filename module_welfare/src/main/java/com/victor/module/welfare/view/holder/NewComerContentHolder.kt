package com.victor.module.welfare.view.holder

import android.util.TypedValue
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import com.victor.lib.common.util.ResUtils
import com.victor.lib.common.view.holder.ContentViewHolder
import com.victor.module.welfare.R

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
class NewComerContentHolder(itemView: View, listener: AdapterView.OnItemClickListener?) :
    ContentViewHolder<String>(itemView,listener) {

    override fun bindData(data: String?) {
        if (bindingAdapterPosition == 0) {
            itemView.findViewById<TextView>(R.id.mTvCoinValue).text = "0/$data"
            itemView.findViewById<TextView>(R.id.mTvDay).text = "今天"
        } else if (bindingAdapterPosition == 1) {
            itemView.findViewById<TextView>(R.id.mTvCoinValue).text = data
            itemView.findViewById<TextView>(R.id.mTvDay).text = "明天"
        } else {
            itemView.findViewById<TextView>(R.id.mTvCoinValue).text = data
            itemView.findViewById<TextView>(R.id.mTvDay).text = "第${bindingAdapterPosition + 1}天"
        }
    }

    override fun onLongClick(v: View): Boolean {
        return false
    }

}