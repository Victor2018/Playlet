package com.victor.module.home.view.adapter

import android.content.Context
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.victor.module.home.view.holder.DramaItemContentViewHolder
import com.victor.lib.common.view.adapter.BaseRecycleAdapter
import com.victor.lib.coremodel.data.remote.entity.bean.DramaItemInfo
import com.victor.module.home.R

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
class DramaItemAdapter(context: Context?, listener: AdapterView.OnItemClickListener?, var parentPosition: Int) :
        BaseRecycleAdapter<DramaItemInfo, RecyclerView.ViewHolder>(context, listener) {


    override fun onCreateHeadVHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
        return null
    }

    override fun onBindHeadVHolder(viewHolder: RecyclerView.ViewHolder, data: com.victor.lib.coremodel.data.remote.entity.bean.DramaItemInfo?, position: Int) {
    }

    override fun onCreateContentVHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return DramaItemContentViewHolder(inflate(R.layout.rv_drama_item_cell ,parent),listener,parentPosition)
    }

    override fun onBindContentVHolder(viewHolder: RecyclerView.ViewHolder, data: com.victor.lib.coremodel.data.remote.entity.bean.DramaItemInfo?, position: Int) {
        val contentViewHolder = viewHolder as DramaItemContentViewHolder
        contentViewHolder.mOnItemClickListener = listener
        contentViewHolder.bindData(data)
    }
}