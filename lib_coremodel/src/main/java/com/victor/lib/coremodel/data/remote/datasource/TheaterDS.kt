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

    override val rankingNextData = MutableLiveData<HttpResult<BaseRes<HomeItemInfo>>>()
    override suspend fun fetchRankingNext(nextPageUrl: String?) {
        // Force Main thread
        withContext(Dispatchers.Main) {
            rankingNextData.value = fetchRankingNextReq(nextPageUrl)
        }
    }

    override val foundData = MutableLiveData<HttpResult<BaseRes<HomeItemInfo>>>()
    override suspend fun fetchFound() {
        // Force Main thread
        withContext(Dispatchers.Main) {
            foundData.value = fetchFoundReq()
        }
    }

    override val foundNextData = MutableLiveData<HttpResult<BaseRes<HomeItemInfo>>>()
    override suspend fun fetchFoundNext(nextPageUrl: String?) {
        // Force Main thread
        withContext(Dispatchers.Main) {
            foundNextData.value = fetchFoundNextReq(nextPageUrl)
        }
    }

    override val hotRecommendData = MutableLiveData<HttpResult<BaseRes<HomeItemInfo>>>()
    override suspend fun fetchHotRecommend() {
        // Force Main thread
        withContext(Dispatchers.Main) {
            hotRecommendData.value = fetchHotRecommendReq()
        }
    }

    override val hotRecommendNextData = MutableLiveData<HttpResult<BaseRes<HomeItemInfo>>>()
    override suspend fun fetchHotRecommendNext(nextPageUrl: String?) {
        // Force Main thread
        withContext(Dispatchers.Main) {
            hotRecommendNextData.value = fetchHotRecommendNextReq(nextPageUrl)
        }
    }

    override val hotPlayData = MutableLiveData<HttpResult<BaseRes<HomeItemInfo>>>()
    override suspend fun fetchHotPlay() {
        // Force Main thread
        withContext(Dispatchers.Main) {
            hotPlayData.value = fetchHotPlayReq()
        }
    }

    override val hotPlayNextData = MutableLiveData<HttpResult<BaseRes<HomeItemInfo>>>()
    override suspend fun fetchHotPlayNext(nextPageUrl: String?) {
        // Force Main thread
        withContext(Dispatchers.Main) {
            hotPlayNextData.value = fetchHotPlayNextReq(nextPageUrl)
        }
    }

    override val hotNewData = MutableLiveData<HttpResult<BaseRes<HomeItemInfo>>>()
    override suspend fun fetchHotNew() {
        // Force Main thread
        withContext(Dispatchers.Main) {
            hotNewData.value = fetchHotNewReq()
        }
    }

    override val hotNewNextData = MutableLiveData<HttpResult<BaseRes<HomeItemInfo>>>()
    override suspend fun fetchHotNewNext(nextPageUrl: String?) {
        // Force Main thread
        withContext(Dispatchers.Main) {
            hotNewNextData.value = fetchHotNewNextReq(nextPageUrl)
        }
    }

    override val hotSearchData = MutableLiveData<HttpResult<BaseRes<HomeItemInfo>>>()
    override suspend fun fetchHotSearch() {
        // Force Main thread
        withContext(Dispatchers.Main) {
            hotSearchData.value = fetchHotSearchReq()
        }
    }

    override val hotSearchNextData = MutableLiveData<HttpResult<BaseRes<HomeItemInfo>>>()
    override suspend fun fetchHotSearchNext(nextPageUrl: String?) {
        // Force Main thread
        withContext(Dispatchers.Main) {
            hotSearchNextData.value = fetchHotSearchNextReq(nextPageUrl)
        }
    }

    private suspend fun <T> fetchRankingReq(): T = withContext(ioDispatcher) {
        handleRespone(ApiClient.getApiService(TheaterApiService::class.java)
            .fetchRanking()) as T
    }

    private suspend fun <T> fetchRankingNextReq(nextPageUrl: String?): T = withContext(ioDispatcher) {
        handleRespone(ApiClient.getApiService(TheaterApiService::class.java)
            .fetchRankingNext(nextPageUrl)) as T
    }

    private suspend fun <T> fetchFoundReq(): T = withContext(ioDispatcher) {
        handleRespone(ApiClient.getApiService(TheaterApiService::class.java)
            .fetchFound()) as T
    }

    private suspend fun <T> fetchFoundNextReq(nextPageUrl: String?): T = withContext(ioDispatcher) {
        handleRespone(ApiClient.getApiService(TheaterApiService::class.java)
            .fetchFoundNext(nextPageUrl)) as T
    }

    private suspend fun <T> fetchHotRecommendReq(): T = withContext(ioDispatcher) {
        handleRespone(ApiClient.getApiService(TheaterApiService::class.java)
            .fetchHotRecommend()) as T
    }

    private suspend fun <T> fetchHotRecommendNextReq(nextPageUrl: String?): T = withContext(ioDispatcher) {
        handleRespone(ApiClient.getApiService(TheaterApiService::class.java)
            .fetchHotRecommendNext(nextPageUrl)) as T
    }

    private suspend fun <T> fetchHotPlayReq(): T = withContext(ioDispatcher) {
        handleRespone(ApiClient.getApiService(TheaterApiService::class.java)
            .fetchHotPlay()) as T
    }

    private suspend fun <T> fetchHotPlayNextReq(nextPageUrl: String?): T = withContext(ioDispatcher) {
        handleRespone(ApiClient.getApiService(TheaterApiService::class.java)
            .fetchHotPlayNext(nextPageUrl)) as T
    }

    private suspend fun <T> fetchHotNewReq(): T = withContext(ioDispatcher) {
        handleRespone(ApiClient.getApiService(TheaterApiService::class.java)
            .fetchHotNew()) as T
    }

    private suspend fun <T> fetchHotNewNextReq(nextPageUrl: String?): T = withContext(ioDispatcher) {
        handleRespone(ApiClient.getApiService(TheaterApiService::class.java)
            .fetchHotNewNext(nextPageUrl)) as T
    }

    private suspend fun <T> fetchHotSearchReq(): T = withContext(ioDispatcher) {
        handleRespone(ApiClient.getApiService(TheaterApiService::class.java)
            .fetchHotSearch()) as T
    }

    private suspend fun <T> fetchHotSearchNextReq(nextPageUrl: String?): T = withContext(ioDispatcher) {
        handleRespone(ApiClient.getApiService(TheaterApiService::class.java)
            .fetchHotSearchNext(nextPageUrl)) as T
    }

}