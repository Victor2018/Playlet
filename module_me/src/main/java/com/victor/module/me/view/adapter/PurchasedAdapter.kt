package com.victor.module.me.view.adapter

import android.content.Context
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.victor.lib.common.view.adapter.BaseRecycleAdapter
import com.victor.lib.coremodel.data.local.entity.PurchasedDramaEntity
import com.victor.module.me.R
import com.victor.module.me.view.holder.PurchasedContentHolder

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: PurchasedAdapter
 * Author: Victor
 * Date: 2025/7/23 14:25
 * Description: 
 * -----------------------------------------------------------------
 */
class PurchasedAdapter(context: Context?, listener: AdapterView.OnItemClickListener?) :
    BaseRecycleAdapter<PurchasedDramaEntity, RecyclerView.ViewHolder>(context, listener) {

    override fun onCreateHeadVHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
        return null
    }

    override fun onBindHeadVHolder(viewHolder: RecyclerView.ViewHolder, data: PurchasedDramaEntity?, position: Int) {
    }

    override fun onCreateContentVHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PurchasedContentHolder(inflate(R.layout.rv_purchased_cell, parent), listener)
    }

    override fun onBindContentVHolder(viewHolder: RecyclerView.ViewHolder, data: PurchasedDramaEntity?, position: Int) {
        val contentViewHolder = viewHolder as PurchasedContentHolder
        contentViewHolder.mOnItemClickListener = listener
        contentViewHolder.bindData(data)
    }
}