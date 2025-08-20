package com.victor.lib.coremodel.data.remote.interfaces

import androidx.lifecycle.LiveData
import com.victor.lib.coremodel.data.remote.entity.bean.HomeItemInfo
import com.victor.lib.coremodel.data.remote.entity.response.BaseRes
import org.victor.http.lib.data.HttpResult


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: ITheaterDS
 * Author: Victor
 * Date: 2021/2/24 16:37
 * Description: 
 * -----------------------------------------------------------------
 */
interface ITheaterDS {

    val rankingData: LiveData<HttpResult<BaseRes<HomeItemInfo>>>
    suspend fun fetchRanking()

    val foundData: LiveData<HttpResult<BaseRes<HomeItemInfo>>>
    suspend fun fetchFound()

    val hotRecommendData: LiveData<HttpResult<BaseRes<HomeItemInfo>>>
    suspend fun fetchHotRecommend()

    val hotPlayData: LiveData<HttpResult<BaseRes<HomeItemInfo>>>
    suspend fun fetchHotPlay()

    val hotNewData: LiveData<HttpResult<BaseRes<HomeItemInfo>>>
    suspend fun fetchHotNew()

    val hotSearchData: LiveData<HttpResult<BaseRes<HomeItemInfo>>>
    suspend fun fetchHotSearch()
}