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
import com.victor.module.home.view.holder.SearchRankContentViewHolder

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: SearchRankAdapter.java
 * Author: Victor
 * Date: 2018/8/30 17:57
 * Description: 
 * -----------------------------------------------------------------
 */
class SearchRankAdapter(context: Context, listener: AdapterView.OnItemClickListener?) :
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
        return SearchRankContentViewHolder(inflate(R.layout.rv_search_rank_cell, parent), listener)
    }

    override fun onBindContentVHolder(viewHolder: RecyclerView.ViewHolder, data: com.victor.lib.coremodel.data.remote.entity.bean.FollowItem?, position: Int) {
        val contentViewHolder = viewHolder as SearchRankContentViewHolder
        contentViewHolder.mOnItemClickListener = listener
        contentViewHolder.bindData(data)
    }
}