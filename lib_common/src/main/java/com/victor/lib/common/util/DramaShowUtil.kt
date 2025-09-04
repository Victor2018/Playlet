package com.victor.lib.common.util

import com.victor.lib.common.app.App
import com.victor.lib.coremodel.data.local.entity.FollowingDramaEntity
import com.victor.lib.coremodel.data.local.entity.HistoryDramaEntity
import com.victor.lib.coremodel.data.local.entity.LikedDramaEntity
import com.victor.lib.coremodel.data.local.entity.PurchasedDramaEntity
import com.victor.lib.coremodel.data.remote.entity.bean.HomeData
import kotlin.random.Random

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: DramaShowUtil
 * Author: Victor
 * Date: 2025/7/23 17:40
 * Description: 
 * -----------------------------------------------------------------
 */
object DramaShowUtil {

    /**
     * 获取显示人气
     */
    fun formatPopularityNumber (goodsPopularity: Int?): String {
        var popularity = goodsPopularity ?: 0
        if (popularity == 0) {
            val randomNum = Random.nextInt(800, 1001)
            return "$randomNum"
        }
        if (popularity <= 10000) {//小于1万
            return "${popularity}"
        }
        if (popularity <= 1000000) {//大于1万，小于100万
            return "${AmountUtil.getRoundUp(popularity.toDouble() / 10000.0,2)}w"
        }
        if (popularity <= 1000000000) {//大于100万，小于10亿
            return "${AmountUtil.getRoundUp(popularity.toDouble() / 10000,0)}w"
        }

        return "10亿"
    }

    fun trans2HistoryDramaEntity(data: HomeData?): HistoryDramaEntity {
        val userId = App.get().getUserInfo()?.uid ?: ""
        val item = HistoryDramaEntity(data?.id ?: 0,data?.dataType,data?.title ?: "",data?.category ?: "",
            data?.description ?: "",data?.playUrl ?: "",data?.cover?.feed ?: "",
            data?.duration ?: 0,userId)
        return item
    }

    fun trans2FollowingDramaEntity(data: HomeData?): FollowingDramaEntity {
        val userId = App.get().getUserInfo()?.uid ?: ""
        val item = FollowingDramaEntity(data?.id ?: 0,data?.dataType,data?.title ?: "",data?.category ?: "",
            data?.description ?: "",data?.playUrl ?: "",data?.cover?.feed ?: "",
            data?.duration ?: 0,userId)
        return item
    }

    fun trans2LikedDramaEntity(data: HomeData?): LikedDramaEntity {
        val userId = App.get().getUserInfo()?.uid ?: ""
        val item = LikedDramaEntity(data?.id ?: 0,data?.dataType,data?.title ?: "",data?.category ?: "",
            data?.description ?: "",data?.playUrl ?: "",data?.cover?.feed ?: "",
            data?.duration ?: 0,userId)
        return item
    }

    fun trans2PurchasedDramaEntity(data: HomeData?): PurchasedDramaEntity {
        val userId = App.get().getUserInfo()?.uid ?: ""
        val item = PurchasedDramaEntity(data?.id ?: 0,data?.dataType,data?.title ?: "",data?.category ?: "",
            data?.description ?: "",data?.playUrl ?: "",data?.cover?.feed ?: "",
            data?.duration ?: 0,userId)
        return item
    }

}