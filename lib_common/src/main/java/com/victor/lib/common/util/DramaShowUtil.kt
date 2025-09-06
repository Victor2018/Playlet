package com.victor.lib.common.util

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

}