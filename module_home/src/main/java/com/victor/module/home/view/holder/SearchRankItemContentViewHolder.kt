package com.victor.module.home.view.holder

import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import com.victor.lib.common.util.DramaShowUtil
import com.victor.lib.common.util.ImageUtils
import com.victor.lib.common.util.ResUtils
import com.victor.lib.common.view.holder.ContentViewHolder
import com.victor.lib.coremodel.data.remote.entity.bean.HomeItemInfo
import com.victor.module.home.R

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: HotSearchItemContentViewHolder.java
 * Author: Victor
 * Date: 2018/9/4 9:10
 * Description: 
 * -----------------------------------------------------------------
 */
class SearchRankItemContentViewHolder(itemView: View,
                                      listener: AdapterView.OnItemClickListener?,
                                      var parentPosition: Int) : ContentViewHolder<HomeItemInfo>(itemView,listener) {

    override fun onClick(view: View) {
        mOnItemClickListener!!.onItemClick(null, view, bindingAdapterPosition, parentPosition.toLong())
    }

    override fun bindData(data: HomeItemInfo?) {
        setHotPositionIconBg(bindingAdapterPosition,itemView.findViewById(R.id.mTvPosition))

        val mIvPoster = itemView.findViewById<ImageView>(R.id.mIvPoster)

        var url = data?.data?.cover?.feed ?: ""
        var title = data?.data?.title ?: ""
        var category = data?.data?.category ?: ""

        if (TextUtils.equals("videoSmallCard",data?.type)) {
            url = data?.data?.cover?.feed ?: ""
            title = data?.data?.title ?: ""
            category = data?.data?.category ?: ""
        }
        ImageUtils.instance.loadImage(itemView.context,mIvPoster, url,
            com.victor.lib.common.R.mipmap.img_placeholder_horizontal)

        itemView.findViewById<TextView>(R.id.mTvContentName).text = title
        itemView.findViewById<TextView>(R.id.mTvType).text = category

        val count = DramaShowUtil.formatPopularityNumber(data?.data?.consumption?.shareCount ?: 0)
        itemView.findViewById<TextView>(R.id.mTvFire).text = count
    }

    private fun setHotPositionIconBg(position: Int,mTvPosition: TextView) {
        mTvPosition.text = "${position + 1}"
        when (position) {
            0 -> {
                mTvPosition.background = ResUtils.getDrawableRes(com.victor.lib.common.R.drawable.shape_ff1f1c_lt_br_radius_12)
            }
            1 -> {
                mTvPosition.background = ResUtils.getDrawableRes(com.victor.lib.common.R.drawable.shape_fa7d00_lt_br_radius_12)
            }
            2 -> {
                mTvPosition.background = ResUtils.getDrawableRes(com.victor.lib.common.R.drawable.shape_ffd424_lt_br_radius_12)
            }
            else -> {
                mTvPosition.background = ResUtils.getDrawableRes(com.victor.lib.common.R.drawable.shape_99000000_lt_br_radius_12)
            }
        }
    }
}