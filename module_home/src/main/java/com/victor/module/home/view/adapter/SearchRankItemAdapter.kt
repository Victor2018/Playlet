package com.victor.module.home.view.adapter

import android.content.Context
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.victor.lib.common.view.adapter.BaseRecycleAdapter
import com.victor.lib.coremodel.data.remote.entity.bean.HomeItemInfo
import com.victor.module.home.R
import com.victor.module.home.view.holder.SearchRankItemContentViewHolder

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: SearchRankItemAdapter.java
 * Author: Victor
 * Date: 2018/8/30 17:57
 * Description: 
 * -----------------------------------------------------------------
 */
class SearchRankItemAdapter(context: Context?, listener: AdapterView.OnItemClickListener?, var parentPosition: Int) :
        BaseRecycleAdapter<HomeItemInfo, RecyclerView.ViewHolder>(context, listener) {


    override fun onCreateHeadVHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
        return null
    }

    override fun onBindHeadVHolder(viewHolder: RecyclerView.ViewHolder, data: com.victor.lib.coremodel.data.remote.entity.bean.HomeItemInfo?, position: Int) {
    }

    override fun onCreateContentVHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SearchRankItemContentViewHolder(inflate(R.layout.rv_search_rank_item_cell ,parent),listener,parentPosition)
    }

    override fun onBindContentVHolder(viewHolder: RecyclerView.ViewHolder, data: com.victor.lib.coremodel.data.remote.entity.bean.HomeItemInfo?, position: Int) {
        val contentViewHolder = viewHolder as SearchRankItemContentViewHolder
        contentViewHolder.mOnItemClickListener = listener
        contentViewHolder.bindData(data)
    }
}