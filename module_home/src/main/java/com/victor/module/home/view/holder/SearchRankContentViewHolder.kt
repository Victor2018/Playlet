package com.victor.module.home.view.holder

import android.widget.AdapterView
import com.victor.lib.common.view.holder.ContentViewHolder
import com.victor.lib.coremodel.data.remote.entity.bean.FollowItem
import com.victor.module.home.databinding.RvSearchRankCellBinding
import com.victor.module.home.view.adapter.SearchRankItemAdapter


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: HotSearchContentViewHolder.java
 * Author: Victor
 * Date: 2018/9/4 9:10
 * Description: 
 * -----------------------------------------------------------------
 */
class SearchRankContentViewHolder(private val binding: RvSearchRankCellBinding, listener: AdapterView.OnItemClickListener?) :
    ContentViewHolder<FollowItem>(binding,listener) {

    override fun bindData(data: FollowItem?) {
        binding.mTvDescribe.text = data?.data?.header?.description ?: ""

//        recyclerView.layoutManager = VegaLayoutManager(VegaLayoutManager.HORIZONTAL)

        val cellAdapter = binding.recyclerView.adapter as? SearchRankItemAdapter
            ?: SearchRankItemAdapter(itemView.context, listener)

        cellAdapter.parentPosition = bindingAdapterPosition

        cellAdapter.add(data?.data?.itemList)

        binding.recyclerView.adapter = cellAdapter

        setItemBackground(bindingAdapterPosition)
    }

    private fun setItemBackground(position: Int) {
        val bgColorArray = intArrayOf(
            com.victor.lib.common.R.drawable.gradient_33d32f2f_white_radius_24,
            com.victor.lib.common.R.drawable.gradient_33ffa8f36_white_radius_24,
            com.victor.lib.common.R.drawable.gradient_3300ff5f_white_radius_24,
            com.victor.lib.common.R.drawable.gradient_332592ff_white_radius_24
        )
        itemView.setBackgroundResource(bgColorArray[position%4])
    }
}