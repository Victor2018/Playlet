package com.victor.module.home.view.adapter

import android.content.Context
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.victor.module.home.view.holder.HotRecommendContentHolder
import com.victor.lib.common.view.adapter.BaseRecycleAdapter
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
    BaseRecycleAdapter<String, RecyclerView.ViewHolder>(context, listener) {

    var hotType = HotType.RECOMMEND

    override fun onCreateHeadVHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
        return null
    }

    override fun onBindHeadVHolder(viewHolder: RecyclerView.ViewHolder, data: String?, position: Int) {
    }

    override fun onCreateContentVHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            HotType.RECOMMEND -> {
                return HotRecommendContentHolder(inflate(R.layout.rv_hot_recommend, parent))
            }
            HotType.PLAY -> {
                return HotPlayContentHolder(inflate(R.layout.rv_hot_play, parent))
            }
            HotType.NEW -> {
                return HotNewContentHolder(inflate(R.layout.rv_hot_new, parent))
            }
            HotType.SEARCH -> {
                return HotSearchContentHolder(inflate(R.layout.rv_hot_search, parent))
            }
        }
        return HotRecommendContentHolder(inflate(R.layout.rv_hot_recommend, parent))
    }

    override fun onBindContentVHolder(viewHolder: RecyclerView.ViewHolder, data: String?, position: Int) {
        if (viewHolder is HotRecommendContentHolder) {
            viewHolder.mOnItemClickListener = listener
            viewHolder.bindData(data)
        } else if (viewHolder is HotPlayContentHolder) {
            viewHolder.mOnItemClickListener = listener
            viewHolder.bindData(data)
        } else if (viewHolder is HotNewContentHolder) {
            viewHolder.mOnItemClickListener = listener
            viewHolder.bindData(data)
        } else if (viewHolder is HotSearchContentHolder) {
            viewHolder.mOnItemClickListener = listener
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