package com.victor.module.home.view.holder

import android.view.View
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import com.victor.lib.common.util.DramaShowUtil
import com.victor.lib.common.util.ImageUtils
import com.victor.lib.common.util.TextViewBoundsUtil
import com.victor.lib.common.view.holder.ContentViewHolder
import com.victor.lib.common.view.widget.ExpandableTextView
import com.victor.lib.coremodel.data.local.entity.DramaEntity
import com.victor.lib.coremodel.data.local.vm.DramaVM
import com.victor.lib.coremodel.data.remote.entity.bean.DramaType
import com.victor.lib.coremodel.data.remote.entity.bean.HomeItemInfo
import com.victor.module.home.R
import kotlin.random.Random

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

    var mDramaVM: DramaVM? = null

    override fun bindData(data: HomeItemInfo?) {
        val mIvPosterBg = itemView.findViewById<ImageView>(R.id.mIvPosterBg)
        val mIvPoster = itemView.findViewById<ImageView>(R.id.mIvPoster)
        val mTvTitle = itemView.findViewById<TextView>(R.id.mTvTitle)
        val mTvType = itemView.findViewById<TextView>(R.id.mTvType)
        val mTvDescribe = itemView.findViewById<ExpandableTextView>(R.id.mTvDescribe)
        val mTvFavCount = itemView.findViewById<TextView>(R.id.mTvFavCount)
        val mTvCollectCount = itemView.findViewById<TextView>(R.id.mTvCollectCount)
        val mTvShareCount = itemView.findViewById<TextView>(R.id.mTvShareCount)
        val mTvDramaCount = itemView.findViewById<TextView>(R.id.mTvDramaCount)

        val url = data?.data?.cover?.feed ?: ""
        ImageUtils.instance.imageGauss(itemView.context,mIvPosterBg, url,18)
        ImageUtils.instance.loadImage(itemView.context,mIvPoster, url,
            com.victor.lib.common.R.mipmap.img_placeholder_vertical)

        mTvTitle.text = data?.data?.title ?: ""
        mTvType.text = data?.data?.category ?: ""
        mTvDescribe.text = data?.data?.description ?: ""
        mTvFavCount.text = DramaShowUtil.formatPopularityNumber(data?.data?.consumption?.shareCount)
        mTvCollectCount.text = DramaShowUtil.formatPopularityNumber(data?.data?.consumption?.replyCount)
        mTvShareCount.text = DramaShowUtil.formatPopularityNumber(data?.data?.consumption?.shareCount)

        val dramaCount = Random.nextInt(20, 101)
        mTvDramaCount.text = "免费看全集·全${dramaCount}集"

        isDramaFollowing(data?.id ?: 0){
            if (it == null) {
                TextViewBoundsUtil.setTvDrawableTop(itemView.context,mTvFavCount,com.victor.lib.common.R.mipmap.ic_fav_normal)
            } else {
                TextViewBoundsUtil.setTvDrawableTop(itemView.context,mTvFavCount,com.victor.lib.common.R.mipmap.ic_fav_focus)
            }
        }

        isDramaLiked(data?.id ?: 0){
            if (it == null) {
                TextViewBoundsUtil.setTvDrawableTop(itemView.context,mTvCollectCount,com.victor.lib.common.R.mipmap.ic_collect_normal)
            } else {
                TextViewBoundsUtil.setTvDrawableTop(itemView.context,mTvCollectCount,com.victor.lib.common.R.mipmap.ic_collect_focus)
            }
        }

        mTvDescribe.setOnClickListener(this)
        mTvFavCount.setOnClickListener(this)
        mTvCollectCount.setOnClickListener(this)
        mTvShareCount.setOnClickListener(this)
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

    private fun isDramaFollowing(id: Int?,callback: (DramaEntity?) -> Unit) {
        mDramaVM?.getById(id ?: 0, DramaType.FOLLOWING.value,callback)
    }

    private fun isDramaLiked(id: Int?,callback: (DramaEntity?) -> Unit) {
        mDramaVM?.getById(id ?: 0, DramaType.LIKE.value,callback)
    }
}