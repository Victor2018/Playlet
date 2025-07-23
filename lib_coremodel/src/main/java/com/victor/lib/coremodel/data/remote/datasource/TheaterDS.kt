package com.victor.lib.coremodel.data.remote.datasource

import androidx.lifecycle.MutableLiveData
import com.victor.lib.coremodel.data.remote.entity.bean.FollowItem
import com.victor.lib.coremodel.data.remote.entity.bean.HomeItemInfo
import com.victor.lib.coremodel.data.remote.entity.response.BaseRes
import com.victor.lib.coremodel.data.remote.interfaces.ITheaterDS
import com.victor.lib.coremodel.data.remote.service.HomeApiService
import com.victor.lib.coremodel.data.remote.service.TheaterApiService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.victor.http.lib.ApiClient
import org.victor.http.lib.data.HttpResult
import org.victor.http.lib.datasource.AbsDS

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: TheaterDS
 * Author: Victor
 * Date: 2021/2/24 16:42
 * Description: 
 * -----------------------------------------------------------------
 */
class TheaterDS(private val ioDispatcher: CoroutineDispatcher): AbsDS(), ITheaterDS {

    override val rankingData = MutableLiveData<HttpResult<BaseRes<HomeItemInfo>>>()
    override suspend fun fetchRanking() {
        // Force Main thread
        withContext(Dispatchers.Main) {
            rankingData.value = fetchRankingReq()
        }
    }

    override val foundData = MutableLiveData<HttpResult<BaseRes<HomeItemInfo>>>()
    override suspend fun fetchFound() {
        // Force Main thread
        withContext(Dispatchers.Main) {
            foundData.value = fetchFoundReq()
        }
    }

    private suspend fun <T> fetchRankingReq(): T = withContext(ioDispatcher) {
        handleRespone(ApiClient.getApiService(TheaterApiService::class.java)
            .fetchRanking()) as T
    }

    private suspend fun <T> fetchFoundReq(): T = withContext(ioDispatcher) {
        handleRespone(ApiClient.getApiService(TheaterApiService::class.java)
            .fetchFound()) as T
    }

}