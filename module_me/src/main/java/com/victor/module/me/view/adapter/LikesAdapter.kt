package com.victor.module.me.view.adapter

import android.content.Context
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.victor.lib.common.view.adapter.BaseRecycleAdapter
import com.victor.lib.coremodel.data.local.entity.DramaEntity
import com.victor.module.me.databinding.RvLikesCellBinding
import com.victor.module.me.view.holder.LikesContentHolder

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: LikesAdapter
 * Author: Victor
 * Date: 2025/7/23 14:25
 * Description: 
 * -----------------------------------------------------------------
 */
class LikesAdapter(context: Context?, listener: AdapterView.OnItemClickListener?) :
    BaseRecycleAdapter<DramaEntity, RecyclerView.ViewHolder>(context, listener) {

    override fun onCreateHeadVHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
        return null
    }

    override fun onBindHeadVHolder(viewHolder: RecyclerView.ViewHolder, data: DramaEntity?, position: Int) {
    }

    override fun onCreateContentVHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return LikesContentHolder(inflateVB(RvLikesCellBinding::inflate, parent), listener)
    }

    override fun onBindContentVHolder(viewHolder: RecyclerView.ViewHolder, data: DramaEntity?, position: Int) {
        if (viewHolder is LikesContentHolder) {
            viewHolder.bindData(data)
        }
    }
}