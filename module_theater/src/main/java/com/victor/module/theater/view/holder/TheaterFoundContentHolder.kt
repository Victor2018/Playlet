package com.victor.module.theater.view.holder

import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import com.victor.lib.common.util.ImageUtils
import com.victor.lib.common.util.ResUtils
import com.victor.lib.common.view.holder.ContentViewHolder
import com.victor.lib.coremodel.data.remote.entity.bean.DramaItemInfo
import com.victor.module.theater.databinding.RvTheaterFoundCellBinding

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: TheaterFoundContentHolder
 * Author: Victor
 * Date: 2025/7/23 14:23
 * Description: 
 * -----------------------------------------------------------------
 */
class TheaterFoundContentHolder(private val binding: RvTheaterFoundCellBinding, listener: AdapterView.OnItemClickListener?) :
    ContentViewHolder<DramaItemInfo>(binding,listener) {

    override fun bindData(data: DramaItemInfo?) {
        setHotPositionIconBg(bindingAdapterPosition,binding.mTvPosition)

        var url = data?.data?.content?.data?.cover?.feed ?: ""
        var title = data?.data?.content?.data?.title ?: ""
        var category = data?.data?.content?.data?.category ?: ""
        var description = data?.data?.content?.data?.description ?: ""

        if (TextUtils.equals("videoSmallCard",data?.type)) {
            url = data?.data?.cover?.feed ?: ""
            title = data?.data?.title ?: ""
            category = data?.data?.category ?: ""
            description = data?.data?.description ?: ""
        }
        ImageUtils.instance.loadImage(itemView.context,binding.mIvPoster, url,
            com.victor.lib.common.R.mipmap.img_placeholder_horizontal)

        binding.mTvContentName.text = title
        binding.mTvType.text = category
        binding.mTvDescribe.text = description
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