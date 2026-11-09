package com.victor.lib.common.view.adapter

import android.content.Context
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.victor.lib.common.databinding.RvPlaySpeedCellBinding
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
        return PlaySpeedContentHolder(inflateVB(RvPlaySpeedCellBinding::inflate, parent),listener)
    }

    override fun onBindContentVHolder(viewHolder: RecyclerView.ViewHolder, data: String?, position: Int) {
        if (viewHolder is PlaySpeedContentHolder) {
            viewHolder.playSpeed = playSpeed
            viewHolder.bindData(data)
        }
    }

}