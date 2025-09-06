package com.victor.lib.coremodel.data.remote.interfaces

import androidx.lifecycle.LiveData
import com.victor.lib.coremodel.data.remote.entity.bean.DramaItemInfo
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

    val rankingData: LiveData<HttpResult<BaseRes<DramaItemInfo>>>
    suspend fun fetchRanking()

    val rankingNextData: LiveData<HttpResult<BaseRes<DramaItemInfo>>>
    suspend fun fetchRankingNext(nextPageUrl: String?)

    val foundData: LiveData<HttpResult<BaseRes<DramaItemInfo>>>
    suspend fun fetchFound()

    val foundNextData: LiveData<HttpResult<BaseRes<DramaItemInfo>>>
    suspend fun fetchFoundNext(nextPageUrl: String?)

    val hotRecommendData: LiveData<HttpResult<BaseRes<DramaItemInfo>>>
    suspend fun fetchHotRecommend()

    val hotRecommendNextData: LiveData<HttpResult<BaseRes<DramaItemInfo>>>
    suspend fun fetchHotRecommendNext(nextPageUrl: String?)

    val hotPlayData: LiveData<HttpResult<BaseRes<DramaItemInfo>>>
    suspend fun fetchHotPlay()

    val hotPlayNextData: LiveData<HttpResult<BaseRes<DramaItemInfo>>>
    suspend fun fetchHotPlayNext(nextPageUrl: String?)

    val hotNewData: LiveData<HttpResult<BaseRes<DramaItemInfo>>>
    suspend fun fetchHotNew()

    val hotNewNextData: LiveData<HttpResult<BaseRes<DramaItemInfo>>>
    suspend fun fetchHotNewNext(nextPageUrl: String?)

    val hotSearchData: LiveData<HttpResult<BaseRes<DramaItemInfo>>>
    suspend fun fetchHotSearch()

    val hotSearchNextData: LiveData<HttpResult<BaseRes<DramaItemInfo>>>
    suspend fun fetchHotSearchNext(nextPageUrl: String?)
}