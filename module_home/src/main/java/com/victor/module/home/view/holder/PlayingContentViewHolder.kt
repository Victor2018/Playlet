package com.victor.module.home.view.holder

import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import com.victor.lib.common.view.holder.ContentViewHolder
import com.victor.lib.coremodel.data.remote.entity.bean.HomeItemInfo
import com.victor.module.home.R

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: PlayingContentViewHolder.java
 * Author: Victor
 * Date: 2018/9/4 9:10
 * Description: 
 * -----------------------------------------------------------------
 */
class PlayingContentViewHolder(itemView: View, listener: AdapterView.OnItemClickListener?) :
    ContentViewHolder<HomeItemInfo>(itemView,listener) {

    override fun bindData(data: HomeItemInfo?) {
        val mTvTitle = itemView.findViewById<TextView>(R.id.mTvTitle)
        mTvTitle.text = data?.data?.title ?: ""
//        val mIvPoster = itemView.findViewById<ImageView>(R.id.mIvPoster)
//        val url = data?.data?.cover?.feed ?: ""
//        ImageUtils.instance.loadImage(itemView.context,mIvPoster, url,
//            com.victor.lib.common.R.mipmap.img_placeholder_horizontal)
    }
}