package com.victor.lib.coremodel.data.remote.interfaces

import androidx.lifecycle.LiveData
import com.victor.lib.coremodel.data.remote.entity.bean.FollowItem
import com.victor.lib.coremodel.data.remote.entity.bean.HomeItemInfo
import com.victor.lib.coremodel.data.remote.entity.response.BaseRes
import org.victor.http.lib.data.HttpResult


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: IHomeDS
 * Author: Victor
 * Date: 2021/2/24 16:37
 * Description: 
 * -----------------------------------------------------------------
 */
interface IHomeDS {
    val dramaListData: LiveData<HttpResult<BaseRes<FollowItem>>>
    suspend fun fetchDramaList()

    val hotRecommendData: LiveData<HttpResult<BaseRes<HomeItemInfo>>>
    suspend fun fetchHotRecommend()

    val hotPlayData: LiveData<HttpResult<BaseRes<HomeItemInfo>>>
    suspend fun fetchHotPlay()

    val hotNewData: LiveData<HttpResult<BaseRes<HomeItemInfo>>>
    suspend fun fetchHotNew()

    val hotSearchData: LiveData<HttpResult<BaseRes<HomeItemInfo>>>
    suspend fun fetchHotSearch()

    val homePlayingData: LiveData<HttpResult<BaseRes<HomeItemInfo>>>
    suspend fun fetchHomePlaying()
}