package com.victor.module.home.view.adapter

import android.content.Context
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.victor.module.home.view.holder.HotRecommendContentHolder
import com.victor.lib.common.view.adapter.BaseRecycleAdapter
import com.victor.lib.coremodel.data.remote.entity.bean.HomeItemInfo
import com.victor.module.home.R
import com.victor.module.home.view.data.HotType
import com.victor.module.home.view.holder.HotNewContentHolder
import com.victor.module.home.view.holder.HotPlayContentHolder
import com.victor.module.home.view.holder.HotSearchContentHolder

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: HotAdapter
 * Author: Victor
 * Date: 2022/3/22 14:33
 * Description: 
 * -----------------------------------------------------------------
 */

class HotAdapter(context: Context, listener: AdapterView.OnItemClickListener) :
    BaseRecycleAdapter<HomeItemInfo, RecyclerView.ViewHolder>(context, listener) {

    var hotType = HotType.RECOMMEND

    override fun onCreateHeadVHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
        return null
    }

    override fun onBindHeadVHolder(viewHolder: RecyclerView.ViewHolder, data: HomeItemInfo?, position: Int) {
    }

    override fun onCreateContentVHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            HotType.RECOMMEND -> {
                return HotRecommendContentHolder(inflate(R.layout.rv_hot_recommend, parent),listener)
            }
            HotType.PLAY -> {
                return HotPlayContentHolder(inflate(R.layout.rv_hot_play, parent),listener)
            }
            HotType.NEW -> {
                return HotNewContentHolder(inflate(R.layout.rv_hot_new, parent),listener)
            }
            HotType.SEARCH -> {
                return HotSearchContentHolder(inflate(R.layout.rv_hot_search, parent),listener)
            }
        }
        return HotRecommendContentHolder(inflate(R.layout.rv_hot_recommend, parent),listener)
    }

    override fun onBindContentVHolder(viewHolder: RecyclerView.ViewHolder, data: HomeItemInfo?, position: Int) {
        if (viewHolder is HotRecommendContentHolder) {
            viewHolder.bindData(data)
        } else if (viewHolder is HotPlayContentHolder) {
            viewHolder.bindData(data)
        } else if (viewHolder is HotNewContentHolder) {
            viewHolder.bindData(data)
        } else if (viewHolder is HotSearchContentHolder) {
            viewHolder.bindData(data)
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