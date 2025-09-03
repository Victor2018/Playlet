package com.victor.module.home.view.holder

import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.victor.lib.common.view.holder.ContentViewHolder
import com.victor.lib.coremodel.data.remote.entity.bean.FollowItem
import com.victor.module.home.R
import com.victor.module.home.view.adapter.SearchRankItemAdapter


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: HotSearchContentViewHolder.java
 * Author: Victor
 * Date: 2018/9/4 9:10
 * Description: 
 * -----------------------------------------------------------------
 */
class SearchRankContentViewHolder(itemView: View, listener: AdapterView.OnItemClickListener?) :
    ContentViewHolder<FollowItem>(itemView,listener) {

    override fun bindData(data: FollowItem?) {
        val mTvDescribe = itemView.findViewById<TextView>(R.id.mTvDescribe)
        val recyclerView = itemView.findViewById<RecyclerView>(R.id.recyclerView)

        mTvDescribe.text = data?.data?.header?.description ?: ""

//        recyclerView.layoutManager = VegaLayoutManager(VegaLayoutManager.HORIZONTAL)

        var cellAdapter = SearchRankItemAdapter(itemView.context,mOnItemClickListener,position)
        cellAdapter.add(data?.data?.itemList)

        recyclerView.adapter = cellAdapter

        setItemBackground(bindingAdapterPosition)
    }

    private fun setItemBackground(position: Int) {
        val bgColorArray = intArrayOf(
            com.victor.lib.common.R.drawable.gradient_33d32f2f_white_radius_24,
            com.victor.lib.common.R.drawable.gradient_33ffa8f36_white_radius_24,
            com.victor.lib.common.R.drawable.gradient_3300ff5f_white_radius_24,
            com.victor.lib.common.R.drawable.gradient_332592ff_white_radius_24
        )
        itemView.setBackgroundResource(bgColorArray[position%4])
    }
}