package com.victor.lib.coremodel.data.remote.service

import com.victor.lib.coremodel.data.remote.api.HomeApi
import com.victor.lib.coremodel.data.remote.entity.bean.FollowItem
import com.victor.lib.coremodel.data.remote.entity.bean.HomeItemInfo
import com.victor.lib.coremodel.data.remote.entity.response.BaseRes
import org.victor.http.lib.adapter.NetworkResponse
import org.victor.http.lib.data.HttpError
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: HomeApiService
 * Author: Victor
 * Date: 2021/2/24 16:45
 * Description: 
 * -----------------------------------------------------------------
 */
interface HomeApiService {

    @GET(HomeApi.DRAMA_LIST)
    suspend fun fetchDramaList(): NetworkResponse<BaseRes<FollowItem>, HttpError>

    @GET(HomeApi.HOT_RECOMMEND)
    suspend fun fetchHotRecommend(): NetworkResponse<BaseRes<HomeItemInfo>, HttpError>

    @GET(HomeApi.HOT_PLAY)
    suspend fun fetchHotPlay(): NetworkResponse<BaseRes<HomeItemInfo>, HttpError>

    @GET(HomeApi.HOT_NEW)
    suspend fun fetchHotNew(): NetworkResponse<BaseRes<HomeItemInfo>, HttpError>

    @GET(HomeApi.HOT_SEARCH)
    suspend fun fetchHotSearch(): NetworkResponse<BaseRes<HomeItemInfo>, HttpError>

    @GET(HomeApi.HOME_PLAYING)
    suspend fun fetchHomePlaying(
        @Query("id") id: Int,
        @Query("deviceModel") deviceModel: String?,
        @Query("udid") udid: String?
    ): NetworkResponse<BaseRes<HomeItemInfo>, HttpError>
}