package com.victor.module.home.view.holder

import android.view.View
import android.widget.AdapterView
import com.victor.lib.common.util.DramaShowUtil
import com.victor.lib.common.util.ImageUtils
import com.victor.lib.common.view.holder.ContentViewHolder
import com.victor.lib.coremodel.data.remote.entity.bean.DramaItemInfo
import com.victor.module.home.R
import com.victor.module.home.databinding.RvDetailPlayingCellBinding
import kotlin.random.Random

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: DetailPlayingContentViewHolder.java
 * Author: Victor
 * Date: 2018/9/4 9:10
 * Description: 
 * -----------------------------------------------------------------
 */
class DetailPlayingContentViewHolder(private val binding: RvDetailPlayingCellBinding, listener: AdapterView.OnItemClickListener?) :
    ContentViewHolder<DramaItemInfo>(binding,listener) {

    override fun bindData(data: DramaItemInfo?) {
        val url = data?.data?.cover?.feed ?: data?.data?.content?.data?.cover?.feed ?: ""
        ImageUtils.instance.imageGauss(itemView.context,binding.mIvPosterBg, url,18)

        binding.mTvTitle.text = data?.data?.title ?: data?.data?.content?.data?.title ?: ""
        val dramaCount = Random.nextInt(20, 101)
        binding.mTvDescribe.text = "第${bindingAdapterPosition + 1}集·共${dramaCount}集 | ${data?.data?.description ?: ""}"
        binding.mTvFavCount.text = DramaShowUtil.formatPopularityNumber(data?.data?.consumption?.shareCount)
        binding.mTvCollectCount.text = DramaShowUtil.formatPopularityNumber(data?.data?.consumption?.replyCount)
        binding.mTvShareCount.text = DramaShowUtil.formatPopularityNumber(data?.data?.consumption?.shareCount)

        binding.mTvDescribe.setOnClickListener(this)
        binding.mTvFavCount.setOnClickListener(this)
        binding.mTvCollectCount.setOnClickListener(this)
        binding.mTvShareCount.setOnClickListener(this)
        binding.mTvDramaEpisodes.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.mTvDescribe -> {
                binding.mTvDescribe.toggle()
            }
            else -> {
                super.onClick(view)
            }
        }
    }

}