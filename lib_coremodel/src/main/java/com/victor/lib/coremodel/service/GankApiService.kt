package com.victor.lib.coremodel.service

import com.victor.lib.coremodel.data.bean.GankDetailEntity
import com.victor.lib.coremodel.http.api.GankApi
import org.victor.http.lib.adapter.NetworkResponse
import org.victor.http.lib.data.HttpError
import retrofit2.http.GET


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: GankApiService
 * Author: Victor
 * Date: 2021/2/24 16:45
 * Description: 
 * -----------------------------------------------------------------
 */
interface GankApiService {

    @GET(GankApi.GANK_GIRL)
    suspend fun fetchGankGirl(): NetworkResponse<GankDetailEntity, HttpError>
}