package com.victor.lib.coremodel.data.remote.service

import com.victor.lib.coremodel.data.remote.api.HomeApi
import com.victor.lib.coremodel.data.remote.api.TheaterApi
import com.victor.lib.coremodel.data.remote.entity.bean.HomeItemInfo
import com.victor.lib.coremodel.data.remote.entity.response.BaseRes
import org.victor.http.lib.adapter.NetworkResponse
import org.victor.http.lib.data.HttpError
import retrofit2.http.GET


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: TheaterApiService
 * Author: Victor
 * Date: 2021/2/24 16:45
 * Description: 
 * -----------------------------------------------------------------
 */
interface TheaterApiService {

    @GET(TheaterApi.RANKING)
    suspend fun fetchRanking(): NetworkResponse<BaseRes<HomeItemInfo>, HttpError>

    @GET(TheaterApi.FOUND)
    suspend fun fetchFound(): NetworkResponse<BaseRes<HomeItemInfo>, HttpError>

    @GET(TheaterApi.HOT_RECOMMEND)
    suspend fun fetchHotRecommend(): NetworkResponse<BaseRes<HomeItemInfo>, HttpError>

    @GET(TheaterApi.HOT_PLAY)
    suspend fun fetchHotPlay(): NetworkResponse<BaseRes<HomeItemInfo>, HttpError>

    @GET(TheaterApi.HOT_NEW)
    suspend fun fetchHotNew(): NetworkResponse<BaseRes<HomeItemInfo>, HttpError>

    @GET(TheaterApi.HOT_SEARCH)
    suspend fun fetchHotSearch(): NetworkResponse<BaseRes<HomeItemInfo>, HttpError>
}