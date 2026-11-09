package com.victor.module.me.view.holder

import android.view.View
import android.widget.AdapterView
import com.victor.lib.common.util.ImageUtils
import com.victor.lib.common.view.holder.ContentViewHolder
import com.victor.lib.coremodel.data.local.entity.DramaEntity
import com.victor.module.me.databinding.RvLikesCellBinding
import kotlin.random.Random

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: LikesContentHolder
 * Author: Victor
 * Date: 2025/7/23 14:23
 * Description: 
 * -----------------------------------------------------------------
 */
class LikesContentHolder(private val binding: RvLikesCellBinding, listener: AdapterView.OnItemClickListener?) :
    ContentViewHolder<DramaEntity>(binding,listener) {

    override fun bindData(data: DramaEntity?) {
        val url = data?.dramaItem?.data?.cover?.feed ?: ""
        ImageUtils.instance.loadImage(itemView.context,binding.mIvPoster, url,
            com.victor.lib.common.R.mipmap.img_placeholder_vertical)
        binding.mTvContentName.text = data?.dramaItem?.data?.title ?: ""

        val start = Random.nextInt(1, 20)
        val dramaCount = Random.nextInt(20, 101)
        binding.mTvDramaCount.text = "${start}/${dramaCount}集"

    }

    override fun onLongClick(v: View): Boolean {
        return false
    }

}