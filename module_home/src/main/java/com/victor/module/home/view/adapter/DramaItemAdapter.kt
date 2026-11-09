package com.victor.module.home.view.adapter

import android.content.Context
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.victor.module.home.view.holder.DramaItemContentViewHolder
import com.victor.lib.common.view.adapter.BaseRecycleAdapter
import com.victor.lib.coremodel.data.remote.entity.bean.DramaItemInfo
import com.victor.module.home.databinding.RvDramaItemCellBinding

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: DramaItemAdapter.java
 * Author: Victor
 * Date: 2018/8/30 17:57
 * Description: 
 * -----------------------------------------------------------------
 */
class DramaItemAdapter(context: Context?, listener: AdapterView.OnItemClickListener?) :
        BaseRecycleAdapter<DramaItemInfo, RecyclerView.ViewHolder>(context, listener) {

    var parentPosition = -1

    override fun onCreateHeadVHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
        return null
    }

    override fun onBindHeadVHolder(viewHolder: RecyclerView.ViewHolder, data: DramaItemInfo?, position: Int) {
    }

    override fun onCreateContentVHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return DramaItemContentViewHolder(inflateVB(RvDramaItemCellBinding::inflate ,parent),listener)
    }

    override fun onBindContentVHolder(viewHolder: RecyclerView.ViewHolder, data: DramaItemInfo?, position: Int) {
        if (viewHolder is DramaItemContentViewHolder) {
            viewHolder.parentPosition = parentPosition
            viewHolder.bindData(data)
        }
    }
}