package com.victor.module.home.view.holder

import android.view.View
import android.widget.AdapterView
import com.victor.lib.common.util.ImageUtils
import com.victor.lib.common.view.holder.ContentViewHolder
import com.victor.lib.coremodel.data.remote.entity.bean.DramaItemInfo
import com.victor.module.home.databinding.RvDramaItemCellBinding

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: DramaItemContentViewHolder.java
 * Author: Victor
 * Date: 2018/9/4 9:10
 * Description: 
 * -----------------------------------------------------------------
 */
class DramaItemContentViewHolder(private val binding: RvDramaItemCellBinding, listener: AdapterView.OnItemClickListener?) :
    ContentViewHolder<DramaItemInfo>(binding,listener) {

    var parentPosition = -1

    override fun onClick(view: View) {
        listener?.onItemClick(null, view, bindingAdapterPosition, parentPosition.toLong())
    }

    override fun bindData(data: DramaItemInfo?) {
        binding.mTvTitle.text = data?.data?.title ?: ""
        val url = data?.data?.cover?.feed ?: ""
        ImageUtils.instance.loadImage(itemView.context,binding.mIvPoster, url,
            com.victor.lib.common.R.mipmap.img_placeholder_horizontal)
    }
}