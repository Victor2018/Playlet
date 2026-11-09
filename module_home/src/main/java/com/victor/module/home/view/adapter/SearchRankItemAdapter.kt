package com.victor.module.home.view.adapter

import android.content.Context
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.victor.lib.common.view.adapter.BaseRecycleAdapter
import com.victor.lib.coremodel.data.remote.entity.bean.DramaItemInfo
import com.victor.module.home.databinding.RvSearchRankItemCellBinding
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
class SearchRankItemAdapter(context: Context?, listener: AdapterView.OnItemClickListener?) :
        BaseRecycleAdapter<DramaItemInfo, RecyclerView.ViewHolder>(context, listener) {

    var parentPosition = -1

    override fun onCreateHeadVHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
        return null
    }

    override fun onBindHeadVHolder(viewHolder: RecyclerView.ViewHolder, data: com.victor.lib.coremodel.data.remote.entity.bean.DramaItemInfo?, position: Int) {
    }

    override fun onCreateContentVHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SearchRankItemContentViewHolder(inflateVB(RvSearchRankItemCellBinding::inflate ,parent),listener)
    }

    override fun onBindContentVHolder(viewHolder: RecyclerView.ViewHolder, data: com.victor.lib.coremodel.data.remote.entity.bean.DramaItemInfo?, position: Int) {
        if (viewHolder is SearchRankItemContentViewHolder) {
            viewHolder.parentPosition = parentPosition
            viewHolder.bindData(data)
        }
    }
}