package com.victor.module.home.view.holder

import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.victor.lib.common.view.holder.ContentViewHolder
import com.victor.lib.coremodel.data.remote.entity.bean.FollowItem
import com.victor.module.home.R
import com.victor.module.home.view.adapter.DramaItemAdapter

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: DramaContentViewHolder.java
 * Author: Victor
 * Date: 2018/9/4 9:10
 * Description: 
 * -----------------------------------------------------------------
 */
class DramaContentViewHolder(itemView: View, listener: AdapterView.OnItemClickListener?) :
    ContentViewHolder<FollowItem>(itemView,listener) {

    override fun bindData(data: FollowItem?) {
        val mTvDescribe = itemView.findViewById<TextView>(R.id.mTvDescribe)
        val recyclerView = itemView.findViewById<RecyclerView>(R.id.recyclerView)

        mTvDescribe.text = data?.data?.header?.description ?: ""

//        recyclerView.layoutManager = VegaLayoutManager(VegaLayoutManager.HORIZONTAL)

        var cellAdapter = DramaItemAdapter(itemView.context,mOnItemClickListener,position)
        cellAdapter.add(data?.data?.itemList)

        recyclerView.adapter = cellAdapter
    }
}