package com.victor.module.welfare.view.adapter

import android.content.Context
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.victor.lib.common.view.adapter.BaseRecycleAdapter
import com.victor.module.welfare.databinding.RvEveryDayCellBinding
import com.victor.module.welfare.view.holder.EveryDayContentHolder

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: EveryDayAdapter
 * Author: Victor
 * Date: 2025/7/23 14:25
 * Description: 
 * -----------------------------------------------------------------
 */
class EveryDayAdapter(context: Context?, listener: AdapterView.OnItemClickListener?) :
    BaseRecycleAdapter<String, RecyclerView.ViewHolder>(context, listener) {

    override fun onCreateHeadVHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
        return null
    }

    override fun onBindHeadVHolder(viewHolder: RecyclerView.ViewHolder, data: String?, position: Int) {
    }

    override fun onCreateContentVHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return EveryDayContentHolder(inflateVB(RvEveryDayCellBinding::inflate, parent), listener)
    }

    override fun onBindContentVHolder(viewHolder: RecyclerView.ViewHolder, data: String?, position: Int) {
        if (viewHolder is EveryDayContentHolder) {
            viewHolder.bindData(data)
        }
    }
}