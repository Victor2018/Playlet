package com.victor.module.home.view.holder

import android.view.View
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import com.victor.lib.common.util.DramaShowUtil
import com.victor.lib.common.util.ImageUtils
import com.victor.lib.common.view.holder.ContentViewHolder
import com.victor.lib.common.view.widget.ExpandableTextView
import com.victor.lib.coremodel.data.remote.entity.bean.DramaItemInfo
import com.victor.module.home.R
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
class DetailPlayingContentViewHolder(itemView: View, listener: AdapterView.OnItemClickListener?) :
    ContentViewHolder<DramaItemInfo>(itemView,listener) {

    override fun bindData(data: DramaItemInfo?) {
        val mIvPosterBg = itemView.findViewById<ImageView>(R.id.mIvPosterBg)
        val mTvTitle = itemView.findViewById<TextView>(R.id.mTvTitle)
        val mTvDescribe = itemView.findViewById<ExpandableTextView>(R.id.mTvDescribe)
        val mTvFavCount = itemView.findViewById<TextView>(R.id.mTvFavCount)
        val mTvCollectCount = itemView.findViewById<TextView>(R.id.mTvCollectCount)
        val mTvShareCount = itemView.findViewById<TextView>(R.id.mTvShareCount)
        val mTvDramaEpisodes = itemView.findViewById<TextView>(R.id.mTvDramaEpisodes)

        val url = data?.data?.cover?.feed ?: data?.data?.content?.data?.cover?.feed ?: ""
        ImageUtils.instance.imageGauss(itemView.context,mIvPosterBg, url,18)

        mTvTitle.text = data?.data?.title ?: data?.data?.content?.data?.title ?: ""
        val dramaCount = Random.nextInt(20, 101)
        mTvDescribe.text = "第${bindingAdapterPosition + 1}集·共${dramaCount}集 | ${data?.data?.description ?: ""}"
        mTvFavCount.text = DramaShowUtil.formatPopularityNumber(data?.data?.consumption?.shareCount)
        mTvCollectCount.text = DramaShowUtil.formatPopularityNumber(data?.data?.consumption?.replyCount)
        mTvShareCount.text = DramaShowUtil.formatPopularityNumber(data?.data?.consumption?.shareCount)

        mTvDescribe.setOnClickListener(this)
        mTvFavCount.setOnClickListener(this)
        mTvCollectCount.setOnClickListener(this)
        mTvShareCount.setOnClickListener(this)
        mTvDramaEpisodes.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.mTvDescribe -> {
                val mTvDescribe = itemView.findViewById<ExpandableTextView>(R.id.mTvDescribe)
                mTvDescribe.toggle()
            }
            else -> {
                super.onClick(view)
            }
        }
    }

}