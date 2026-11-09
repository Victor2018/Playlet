package com.victor.module.theater.view.adapter

import android.content.Context
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.victor.lib.common.view.adapter.BaseRecycleAdapter
import com.victor.lib.coremodel.data.remote.entity.bean.DramaItemInfo
import com.victor.module.theater.data.HotType
import com.victor.module.theater.databinding.RvHotNewCellBinding
import com.victor.module.theater.databinding.RvHotPlayCellBinding
import com.victor.module.theater.databinding.RvHotRecommendCellBinding
import com.victor.module.theater.databinding.RvHotSearchCellBinding
import com.victor.module.theater.view.holder.HotNewContentHolder
import com.victor.module.theater.view.holder.HotPlayContentHolder
import com.victor.module.theater.view.holder.HotRecommendContentHolder
import com.victor.module.theater.view.holder.HotSearchContentHolder

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: TheaterHotAdapter
 * Author: Victor
 * Date: 2025/7/23 14:25
 * Description: 
 * -----------------------------------------------------------------
 */
class TheaterHotAdapter(context: Context?, listener: AdapterView.OnItemClickListener?) :
    BaseRecycleAdapter<DramaItemInfo, RecyclerView.ViewHolder>(context, listener) {

    var hotType = HotType.RECOMMEND

    override fun onCreateHeadVHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
        return null
    }

    override fun onBindHeadVHolder(viewHolder: RecyclerView.ViewHolder, data: DramaItemInfo?, position: Int) {
    }

    override fun onCreateContentVHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            HotType.RECOMMEND -> {
                return HotRecommendContentHolder(inflateVB(RvHotRecommendCellBinding::inflate, parent),listener)
            }
            HotType.PLAY -> {
                return HotPlayContentHolder(inflateVB(RvHotPlayCellBinding::inflate, parent),listener)
            }
            HotType.NEW -> {
                return HotNewContentHolder(inflateVB(RvHotNewCellBinding::inflate, parent),listener)
            }
            HotType.SEARCH -> {
                return HotSearchContentHolder(inflateVB(RvHotSearchCellBinding::inflate, parent),listener)
            }
        }
        return HotRecommendContentHolder(inflateVB(RvHotRecommendCellBinding::inflate, parent),listener)
    }

    override fun onBindContentVHolder(viewHolder: RecyclerView.ViewHolder, data: DramaItemInfo?, position: Int) {
        when (viewHolder) {
            is HotRecommendContentHolder -> {
                viewHolder.bindData(data)
            }
            is HotPlayContentHolder -> {
                viewHolder.bindData(data)
            }
            is HotNewContentHolder -> {
                viewHolder.bindData(data)
            }
            is HotSearchContentHolder -> {
                viewHolder.bindData(data)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        var viewType = super.getItemViewType(position)
        if (viewType == ITEM_TYPE_CONTENT) {
            viewType = hotType
        }
        return viewType
    }
}