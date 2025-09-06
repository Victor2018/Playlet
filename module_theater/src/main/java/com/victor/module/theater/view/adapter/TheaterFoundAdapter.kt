package com.victor.module.theater.view.adapter

import android.content.Context
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.victor.lib.common.view.adapter.BaseRecycleAdapter
import com.victor.lib.coremodel.data.remote.entity.bean.DramaItemInfo
import com.victor.module.theater.R
import com.victor.module.theater.view.holder.TheaterFoundContentHolder
import com.victor.module.theater.view.holder.TheaterFoundHeaderHolder

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: TheaterFoundAdapter
 * Author: Victor
 * Date: 2025/7/23 14:25
 * Description: 
 * -----------------------------------------------------------------
 */
class TheaterFoundAdapter(context: Context?, listener: AdapterView.OnItemClickListener?) :
    BaseRecycleAdapter<DramaItemInfo, RecyclerView.ViewHolder>(context, listener) {

    var mHomeItemInfo: DramaItemInfo? = null

    override fun onCreateHeadVHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TheaterFoundHeaderHolder(inflate(R.layout.rv_theater_found_header_cell, parent), listener)
    }

    override fun onBindHeadVHolder(viewHolder: RecyclerView.ViewHolder, data: DramaItemInfo?, position: Int) {
        val headerViewHolder = viewHolder as TheaterFoundHeaderHolder
        headerViewHolder.bindData(mHomeItemInfo)
    }

    override fun onCreateContentVHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TheaterFoundContentHolder(inflate(R.layout.rv_theater_found_cell, parent), listener)
    }

    override fun onBindContentVHolder(viewHolder: RecyclerView.ViewHolder, data: DramaItemInfo?, position: Int) {
        val contentViewHolder = viewHolder as TheaterFoundContentHolder
        contentViewHolder.mOnItemClickListener = listener
        contentViewHolder.bindData(data)
    }
}