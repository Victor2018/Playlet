package com.victor.lib.coremodel.data.remote.datasource

import androidx.lifecycle.MutableLiveData
import com.victor.lib.coremodel.data.remote.entity.bean.FollowItem
import com.victor.lib.coremodel.data.remote.entity.bean.HomeItemInfo
import com.victor.lib.coremodel.data.remote.entity.response.BaseRes
import com.victor.lib.coremodel.data.remote.interfaces.IHomeDS
import com.victor.lib.coremodel.data.remote.service.HomeApiService
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
 * File: HomeDS
 * Author: Victor
 * Date: 2021/2/24 16:42
 * Description: 
 * -----------------------------------------------------------------
 */
class HomeDS(private val ioDispatcher: CoroutineDispatcher): AbsDS(), IHomeDS {

    override val dramaListData = MutableLiveData<HttpResult<BaseRes<FollowItem>>>()
    override suspend fun fetchDramaList() {
        // Force Main thread
        withContext(Dispatchers.Main) {
            dramaListData.value = fetchDramaListReq()
        }
    }

    override val homePlayingData = MutableLiveData<HttpResult<BaseRes<HomeItemInfo>>>()
    override suspend fun fetchHomePlaying(id: Int) {
        // Force Main thread
        withContext(Dispatchers.Main) {
            homePlayingData.value = fetchHomePlayingReq(id)
        }
    }

    private suspend fun <T> fetchDramaListReq(): T = withContext(ioDispatcher) {
        handleRespone(ApiClient.getApiService(HomeApiService::class.java)
            .fetchDramaList()) as T
    }

    private suspend fun <T> fetchHomePlayingReq(id: Int): T = withContext(ioDispatcher) {
        handleRespone(ApiClient.getApiService(HomeApiService::class.java)
            .fetchHomePlaying(id,"Android","d2807c895f0348a180148c9dfa6f2feeac0781b5")) as T
    }
}