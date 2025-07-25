package com.victor.module.theater.view.holder

import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ImageView
import android.widget.TextSwitcher
import android.widget.TextView
import com.victor.lib.common.util.ImageUtils
import com.victor.lib.common.util.ResUtils
import com.victor.lib.common.view.holder.ContentViewHolder
import com.victor.lib.common.view.holder.HeaderViewHolder
import com.victor.lib.common.view.widget.banner.BannerViewFlipper
import com.victor.lib.common.view.widget.banner.DescriptionViewSwitcherFactory
import com.victor.lib.coremodel.data.remote.entity.bean.FollowItem
import com.victor.lib.coremodel.data.remote.entity.bean.HomeItemInfo
import com.victor.module.theater.R
import org.victor.http.lib.util.JsonUtils

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

    fun bindData(data: HomeItemInfo?) {
        val mBsvBanner = itemView.findViewById<BannerViewFlipper>(R.id.mBsvBanner)
        mBsvBanner.onItemClickListener = mOnItemClickListener
        mBsvBanner.startWithList(data?.data?.itemList)
    }

}