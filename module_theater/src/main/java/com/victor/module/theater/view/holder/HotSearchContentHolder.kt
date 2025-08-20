package com.victor.module.theater.view.holder

import android.view.View
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import com.victor.lib.common.util.DramaShowUtil
import com.victor.lib.common.util.ImageUtils
import com.victor.lib.common.util.ResUtils
import com.victor.lib.common.view.holder.ContentViewHolder
import com.victor.lib.coremodel.data.remote.entity.bean.HomeItemInfo
import com.victor.module.theater.R

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: HotSearchContentHolder
 * Author: Victor
 * Date: 2022/3/22 14:30
 * Description: 
 * -----------------------------------------------------------------
 */

class HotSearchContentHolder(itemView: View, listener: AdapterView.OnItemClickListener?) :
    ContentViewHolder<HomeItemInfo?>(itemView,listener) {

    override fun bindData (data: HomeItemInfo?) {
        setHotPositionIconBg(bindingAdapterPosition,itemView.findViewById(R.id.mTvPosition))

        val mIvPoster = itemView.findViewById<ImageView>(R.id.mIvPoster)
        val url = data?.data?.cover?.feed ?: ""
        ImageUtils.instance.loadImage(itemView.context,mIvPoster, url,
            com.victor.lib.common.R.mipmap.img_placeholder_horizontal)

        itemView.findViewById<TextView>(R.id.mTvContentName).text = data?.data?.title ?: ""
        itemView.findViewById<TextView>(R.id.mTvType).text = data?.data?.category ?: ""
        itemView.findViewById<TextView>(R.id.mTvDescribe).text = data?.data?.description ?: ""
        val count = DramaShowUtil.formatPopularityNumber(data?.data?.consumption?.shareCount ?: 0)
        itemView.findViewById<TextView>(R.id.mTvFire).text = count
    }

    override fun onClick(view: View) {
        super.onClick(view)
    }

    override fun onLongClick(v: View): Boolean {
        return false
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