package com.victor.lib.coremodel.data.remote.datasource

import androidx.lifecycle.MutableLiveData
import com.victor.lib.coremodel.data.remote.entity.bean.DramaItemInfo
import com.victor.lib.coremodel.data.remote.entity.response.BaseRes
import com.victor.lib.coremodel.data.remote.interfaces.ITheaterDS
import com.victor.lib.coremodel.data.remote.service.TheaterApiService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.victor.http.lib.ApiClient
import org.victor.http.lib.data.HttpResult
import org.victor.http.lib.util.handleResponse

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
class TheaterDS(private val ioDispatcher: CoroutineDispatcher): ITheaterDS {

    override val rankingData = MutableLiveData<HttpResult<BaseRes<DramaItemInfo>>>()
    override suspend fun fetchRanking() {
        // Force Main thread
        withContext(Dispatchers.Main) {
            rankingData.value = fetchRankingReq()
        }
    }

    override val rankingNextData = MutableLiveData<HttpResult<BaseRes<DramaItemInfo>>>()
    override suspend fun fetchRankingNext(nextPageUrl: String?) {
        // Force Main thread
        withContext(Dispatchers.Main) {
            rankingNextData.value = fetchRankingNextReq(nextPageUrl)
        }
    }

    override val foundData = MutableLiveData<HttpResult<BaseRes<DramaItemInfo>>>()
    override suspend fun fetchFound() {
        // Force Main thread
        withContext(Dispatchers.Main) {
            foundData.value = fetchFoundReq()
        }
    }

    override val foundNextData = MutableLiveData<HttpResult<BaseRes<DramaItemInfo>>>()
    override suspend fun fetchFoundNext(nextPageUrl: String?) {
        // Force Main thread
        withContext(Dispatchers.Main) {
            foundNextData.value = fetchFoundNextReq(nextPageUrl)
        }
    }

    override val hotRecommendData = MutableLiveData<HttpResult<BaseRes<DramaItemInfo>>>()
    override suspend fun fetchHotRecommend() {
        // Force Main thread
        withContext(Dispatchers.Main) {
            hotRecommendData.value = fetchHotRecommendReq()
        }
    }

    override val hotRecommendNextData = MutableLiveData<HttpResult<BaseRes<DramaItemInfo>>>()
    override suspend fun fetchHotRecommendNext(nextPageUrl: String?) {
        // Force Main thread
        withContext(Dispatchers.Main) {
            hotRecommendNextData.value = fetchHotRecommendNextReq(nextPageUrl)
        }
    }

    override val hotPlayData = MutableLiveData<HttpResult<BaseRes<DramaItemInfo>>>()
    override suspend fun fetchHotPlay() {
        // Force Main thread
        withContext(Dispatchers.Main) {
            hotPlayData.value = fetchHotPlayReq()
        }
    }

    override val hotPlayNextData = MutableLiveData<HttpResult<BaseRes<DramaItemInfo>>>()
    override suspend fun fetchHotPlayNext(nextPageUrl: String?) {
        // Force Main thread
        withContext(Dispatchers.Main) {
            hotPlayNextData.value = fetchHotPlayNextReq(nextPageUrl)
        }
    }

    override val hotNewData = MutableLiveData<HttpResult<BaseRes<DramaItemInfo>>>()
    override suspend fun fetchHotNew() {
        // Force Main thread
        withContext(Dispatchers.Main) {
            hotNewData.value = fetchHotNewReq()
        }
    }

    override val hotNewNextData = MutableLiveData<HttpResult<BaseRes<DramaItemInfo>>>()
    override suspend fun fetchHotNewNext(nextPageUrl: String?) {
        // Force Main thread
        withContext(Dispatchers.Main) {
            hotNewNextData.value = fetchHotNewNextReq(nextPageUrl)
        }
    }

    override val hotSearchData = MutableLiveData<HttpResult<BaseRes<DramaItemInfo>>>()
    override suspend fun fetchHotSearch() {
        // Force Main thread
        withContext(Dispatchers.Main) {
            hotSearchData.value = fetchHotSearchReq()
        }
    }

    override val hotSearchNextData = MutableLiveData<HttpResult<BaseRes<DramaItemInfo>>>()
    override suspend fun fetchHotSearchNext(nextPageUrl: String?) {
        // Force Main thread
        withContext(Dispatchers.Main) {
            hotSearchNextData.value = fetchHotSearchNextReq(nextPageUrl)
        }
    }

    private suspend fun fetchRankingReq() = withContext(ioDispatcher) {
        ApiClient.getApiService(TheaterApiService::class.java)
            .fetchRanking().handleResponse()
    }

    private suspend fun fetchRankingNextReq(nextPageUrl: String?) = withContext(ioDispatcher) {
        ApiClient.getApiService(TheaterApiService::class.java)
            .fetchRankingNext(nextPageUrl).handleResponse()
    }

    private suspend fun fetchFoundReq() = withContext(ioDispatcher) {
        ApiClient.getApiService(TheaterApiService::class.java)
            .fetchFound().handleResponse()
    }

    private suspend fun fetchFoundNextReq(nextPageUrl: String?) = withContext(ioDispatcher) {
        ApiClient.getApiService(TheaterApiService::class.java)
            .fetchFoundNext(nextPageUrl).handleResponse()
    }

    private suspend fun fetchHotRecommendReq() = withContext(ioDispatcher) {
        ApiClient.getApiService(TheaterApiService::class.java)
            .fetchHotRecommend().handleResponse()
    }

    private suspend fun fetchHotRecommendNextReq(nextPageUrl: String?) = withContext(ioDispatcher) {
        ApiClient.getApiService(TheaterApiService::class.java)
            .fetchHotRecommendNext(nextPageUrl).handleResponse()
    }

    private suspend fun fetchHotPlayReq() = withContext(ioDispatcher) {
        ApiClient.getApiService(TheaterApiService::class.java)
            .fetchHotPlay().handleResponse()
    }

    private suspend fun fetchHotPlayNextReq(nextPageUrl: String?) = withContext(ioDispatcher) {
        ApiClient.getApiService(TheaterApiService::class.java)
            .fetchHotPlayNext(nextPageUrl).handleResponse()
    }

    private suspend fun fetchHotNewReq() = withContext(ioDispatcher) {
        ApiClient.getApiService(TheaterApiService::class.java)
            .fetchHotNew().handleResponse()
    }

    private suspend fun fetchHotNewNextReq(nextPageUrl: String?) = withContext(ioDispatcher) {
        ApiClient.getApiService(TheaterApiService::class.java)
            .fetchHotNewNext(nextPageUrl).handleResponse()
    }

    private suspend fun fetchHotSearchReq() = withContext(ioDispatcher) {
        ApiClient.getApiService(TheaterApiService::class.java)
            .fetchHotSearch().handleResponse()
    }

    private suspend fun fetchHotSearchNextReq(nextPageUrl: String?) = withContext(ioDispatcher) {
        ApiClient.getApiService(TheaterApiService::class.java)
            .fetchHotSearchNext(nextPageUrl).handleResponse()
    }

}