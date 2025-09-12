package com.victor.module.home.view.adapter

import android.content.Context
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.victor.lib.common.view.adapter.BaseRecycleAdapter
import com.victor.module.home.R
import com.victor.module.home.view.holder.EpisodesTitleContentViewHolder

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: PlayingAdapter.java
 * Author: Victor
 * Date: 2018/8/30 17:57
 * Description: 
 * -----------------------------------------------------------------
 */
class EpisodesTitleAdapter(context: Context?, listener: AdapterView.OnItemClickListener?) :
        BaseRecycleAdapter<String, RecyclerView.ViewHolder>(context, listener) {

    var selectPositon = 0

    override fun onCreateHeadVHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
        return null
    }

    override fun onBindHeadVHolder(viewHolder: RecyclerView.ViewHolder, data: String?, position: Int) {
    }

    override fun onCreateContentVHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return EpisodesTitleContentViewHolder(inflate(R.layout.rv_episodes_title_cell ,parent),listener)
    }

    override fun onBindContentVHolder(viewHolder: RecyclerView.ViewHolder, data: String?, position: Int) {
        if (viewHolder is EpisodesTitleContentViewHolder) {
            viewHolder.mOnItemClickListener = listener
            viewHolder.selectPositon = selectPositon
            viewHolder.bindData(data)
        }
    }
}