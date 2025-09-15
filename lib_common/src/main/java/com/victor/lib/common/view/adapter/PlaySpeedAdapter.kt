package com.victor.lib.common.view.adapter

import android.content.Context
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.victor.lib.common.R
import com.victor.lib.common.view.holder.PlaySpeedContentHolder

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: PlaySpeedAdapter
 * Author: Victor
 * Date: 2022/6/6 11:45
 * Description: 
 * -----------------------------------------------------------------
 */

class PlaySpeedAdapter(context: Context, listener: AdapterView.OnItemClickListener) :
    BaseRecycleAdapter<String, RecyclerView.ViewHolder>(context, listener) {

    var playSpeed: String? = "1.0X"

    override fun onCreateHeadVHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
        return null
    }

    override fun onBindHeadVHolder(viewHolder: RecyclerView.ViewHolder, data: String?, position: Int) {
    }

    override fun onCreateContentVHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PlaySpeedContentHolder(inflate(R.layout.rv_play_speed_cell, parent),listener)
    }

    override fun onBindContentVHolder(viewHolder: RecyclerView.ViewHolder, data: String?, position: Int) {
        val contentViewHolder = viewHolder as PlaySpeedContentHolder
        viewHolder.playSpeed = playSpeed
        contentViewHolder.bindData(data)
        contentViewHolder.mOnItemClickListener = listener
    }

}