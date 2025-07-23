package com.victor.module.home.view.adapter

import android.content.Context
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.victor.lib.common.util.Loger
import com.victor.lib.common.view.adapter.BaseRecycleAdapter
import com.victor.lib.common.view.widget.layoutmanager.GravitySnapHelper
import com.victor.lib.coremodel.data.remote.entity.bean.FollowItem
import com.victor.module.home.R
import com.victor.module.home.view.holder.FollowContentViewHolder

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: DramaAdapter.java
 * Author: Victor
 * Date: 2018/8/30 17:57
 * Description: 
 * -----------------------------------------------------------------
 */
class DramaAdapter(context: Context, listener: AdapterView.OnItemClickListener?) :
        BaseRecycleAdapter<FollowItem, RecyclerView.ViewHolder>(context, listener),
        GravitySnapHelper.SnapListener{

    override fun onSnap(position: Int) {
        Loger.d("onSnap()-position = ", position.toString())
    }

    override fun onCreateHeadVHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
        return null
    }

    override fun onBindHeadVHolder(viewHolder: RecyclerView.ViewHolder, data: com.victor.lib.coremodel.data.remote.entity.bean.FollowItem?, position: Int) {
    }

    override fun onCreateContentVHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return FollowContentViewHolder(inflate(R.layout.rv_drama_cell, parent), listener)
    }

    override fun onBindContentVHolder(viewHolder: RecyclerView.ViewHolder, data: com.victor.lib.coremodel.data.remote.entity.bean.FollowItem?, position: Int) {
        val contentViewHolder = viewHolder as FollowContentViewHolder
        contentViewHolder.mOnItemClickListener = listener
        contentViewHolder.bindData(data)
    }
}