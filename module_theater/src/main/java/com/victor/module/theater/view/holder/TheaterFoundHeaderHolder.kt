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
        val mTsDescription = itemView.findViewById<TextSwitcher>(R.id.mTsDescription)

        if (mTsDescription.childCount < 2) {
            mTsDescription.setFactory(DescriptionViewSwitcherFactory(itemView.context))
        }

        mTsDescription.setInAnimation(itemView.context, android.R.anim.fade_in)
        mTsDescription.setOutAnimation(itemView.context, android.R.anim.fade_out)
        mBsvBanner.onItemClickListener = object : OnItemClickListener {
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (id == BannerViewFlipper.ON_BANNER_ITEM_CLICK) {
                    mOnItemClickListener?.onItemClick(null, view, bindingAdapterPosition, 0)
                }
                if (id == BannerViewFlipper.ON_BANNER_ITEM_SELECT) {
                    val title = mBsvBanner?.messages?.get(position)?.data?.content?.data?.title
                    mTsDescription.setText(title)
                }
            }
        }

        mBsvBanner.startWithList(data?.data?.itemList)

    }

}