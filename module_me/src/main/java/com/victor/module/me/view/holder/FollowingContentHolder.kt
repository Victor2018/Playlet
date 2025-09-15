package com.victor.module.me.view.holder

import android.view.View
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import com.victor.lib.common.util.ImageUtils
import com.victor.lib.common.view.holder.ContentViewHolder
import com.victor.lib.coremodel.data.local.entity.DramaEntity
import com.victor.lib.coremodel.data.remote.entity.bean.DramaItemInfo
import com.victor.module.me.R
import org.victor.http.lib.util.JsonUtils
import kotlin.random.Random

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: FollowingContentHolder
 * Author: Victor
 * Date: 2025/7/23 14:23
 * Description: 
 * -----------------------------------------------------------------
 */
class FollowingContentHolder(itemView: View, listener: AdapterView.OnItemClickListener?) :
    ContentViewHolder<DramaEntity>(itemView,listener) {

    override fun bindData(data: DramaEntity?) {
        val mIvPoster = itemView.findViewById<ImageView>(R.id.mIvPoster)
        val url = data?.dramaItem?.data?.cover?.feed ?: ""
        ImageUtils.instance.loadImage(itemView.context,mIvPoster, url,
            com.victor.lib.common.R.mipmap.img_placeholder_vertical)
        itemView.findViewById<TextView>(R.id.mTvContentName).text = data?.dramaItem?.data?.title ?: ""

        val start = Random.nextInt(1, 20)
        val dramaCount = Random.nextInt(20, 101)
        itemView.findViewById<TextView>(R.id.mTvDramaCount).text = "${start}/${dramaCount}集"
    }

    override fun onLongClick(v: View): Boolean {
        return false
    }

}