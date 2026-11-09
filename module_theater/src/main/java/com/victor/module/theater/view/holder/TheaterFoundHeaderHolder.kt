package com.victor.module.theater.view.holder

import android.widget.AdapterView
import com.victor.lib.common.view.holder.HeaderViewHolder
import com.victor.lib.coremodel.data.remote.entity.bean.DramaItemInfo
import com.victor.module.theater.databinding.RvTheaterFoundHeaderCellBinding

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: TheaterHotContentHolder
 * Author: Victor
 * Date: 2025/7/23 14:23
 * Description: 
 * -----------------------------------------------------------------
 */
class TheaterFoundHeaderHolder(private val binding: RvTheaterFoundHeaderCellBinding, listener: AdapterView.OnItemClickListener?) :
    HeaderViewHolder<DramaItemInfo>(binding,listener) {

    override fun bindData(data: DramaItemInfo?) {
        binding.mBsvBanner.startWithList(data?.data?.itemList)
    }

}