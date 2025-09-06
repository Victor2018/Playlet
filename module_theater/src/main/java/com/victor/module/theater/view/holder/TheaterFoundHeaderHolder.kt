package com.victor.module.theater.view.holder

import android.view.View
import android.widget.AdapterView.OnItemClickListener
import com.victor.lib.common.view.holder.HeaderViewHolder
import com.victor.lib.common.view.widget.banner.BannerViewFlipper
import com.victor.lib.coremodel.data.remote.entity.bean.DramaItemInfo
import com.victor.module.theater.R

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
class TheaterFoundHeaderHolder(itemView: View, listener: OnItemClickListener?) :
    HeaderViewHolder(itemView,listener) {

    fun bindData(data: DramaItemInfo?) {
        val mBsvBanner = itemView.findViewById<BannerViewFlipper>(R.id.mBsvBanner)
        mBsvBanner.onItemClickListener = mOnItemClickListener
        mBsvBanner.startWithList(data?.data?.itemList)
    }

}