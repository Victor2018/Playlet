package com.victor.module.home.view.adapter

import android.content.Context
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.victor.lib.common.view.adapter.BaseRecycleAdapter
import com.victor.module.home.R
import com.victor.module.home.view.holder.EpisodesContentViewHolder

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: EpisodesAdapter.java
 * Author: Victor
 * Date: 2018/8/30 17:57
 * Description: 
 * -----------------------------------------------------------------
 */
class EpisodesAdapter(context: Context?, listener: AdapterView.OnItemClickListener?) :
        BaseRecycleAdapter<Int, RecyclerView.ViewHolder>(context, listener) {

    var selectPositon = 0

    override fun onCreateHeadVHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
        return null
    }

    override fun onBindHeadVHolder(viewHolder: RecyclerView.ViewHolder, data: Int?, position: Int) {
    }

    override fun onCreateContentVHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return EpisodesContentViewHolder(inflate(R.layout.rv_episodes_cell ,parent),listener)
    }

    override fun onBindContentVHolder(viewHolder: RecyclerView.ViewHolder, data: Int?, position: Int) {
        if (viewHolder is EpisodesContentViewHolder) {
            viewHolder.mOnItemClickListener = listener
            viewHolder.selectPositon = selectPositon
            viewHolder.bindData(data)
        }
    }
}