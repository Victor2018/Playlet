package com.victor.module.home.view.holder

import android.widget.AdapterView
import com.victor.lib.common.view.holder.ContentViewHolder
import com.victor.lib.coremodel.data.remote.entity.bean.FollowItem
import com.victor.module.home.databinding.RvDramaCellBinding
import com.victor.module.home.view.adapter.DramaItemAdapter

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: DramaContentViewHolder.java
 * Author: Victor
 * Date: 2018/9/4 9:10
 * Description: 
 * -----------------------------------------------------------------
 */
class DramaContentViewHolder(private val binding: RvDramaCellBinding, listener: AdapterView.OnItemClickListener?) :
    ContentViewHolder<FollowItem>(binding,listener) {

    override fun bindData(data: FollowItem?) {
        binding.mTvDescribe.text = data?.data?.header?.description ?: ""

//        recyclerView.layoutManager = VegaLayoutManager(VegaLayoutManager.HORIZONTAL)

        val cellAdapter = binding.recyclerView.adapter as? DramaItemAdapter
            ?: DramaItemAdapter(itemView.context, listener)
        cellAdapter.add(data?.data?.itemList)

        binding.recyclerView.adapter = cellAdapter
    }
}