package com.victor.lib.coremodel.data.remote.vm

import androidx.lifecycle.*
import com.victor.lib.coremodel.data.remote.interfaces.ITheaterDS
import kotlinx.coroutines.launch

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: TheaterVM
 * Author: Victor
 * Date: 2021/2/24 17:25
 * Description: 
 * -----------------------------------------------------------------
 */
class TheaterVM(private val dataSource: ITheaterDS): ViewModel() {

    val rankingData = dataSource.rankingData
    fun fetchRanking() {
        // Launch a coroutine that reads from a remote data source and updates cache
        viewModelScope.launch {
            dataSource.fetchRanking()
        }
    }

    val rankingNextData = dataSource.rankingNextData
    fun fetchRankingNext(nextPageUrl: String?) {
        // Launch a coroutine that reads from a remote data source and updates cache
        viewModelScope.launch {
            dataSource.fetchRankingNext(nextPageUrl)
        }
    }

    val foundData = dataSource.foundData
    fun fetchFound() {
        // Launch a coroutine that reads from a remote data source and updates cache
        viewModelScope.launch {
            dataSource.fetchFound()
        }
    }

    val foundNextData = dataSource.foundNextData
    fun fetchFoundNext(nextPageUrl: String?) {
        // Launch a coroutine that reads from a remote data source and updates cache
        viewModelScope.launch {
            dataSource.fetchFoundNext(nextPageUrl)
        }
    }

    val hotRecommendData = dataSource.hotRecommendData
    fun fetchHotRecommend() {
        // Launch a coroutine that reads from a remote data source and updates cache
        viewModelScope.launch {
            dataSource.fetchHotRecommend()
        }
    }

    val hotRecommendNextData = dataSource.hotRecommendNextData
    fun fetchHotRecommendNext(nextPageUrl: String?) {
        // Launch a coroutine that reads from a remote data source and updates cache
        viewModelScope.launch {
            dataSource.fetchHotRecommendNext(nextPageUrl)
        }
    }

    val hotPlayData = dataSource.hotPlayData
    fun fetchHotPlay() {
        // Launch a coroutine that reads from a remote data source and updates cache
        viewModelScope.launch {
            dataSource.fetchHotPlay()
        }
    }

    val hotPlayNextData = dataSource.hotPlayNextData
    fun fetchHotPlayNext(nextPageUrl: String?) {
        // Launch a coroutine that reads from a remote data source and updates cache
        viewModelScope.launch {
            dataSource.fetchHotPlayNext(nextPageUrl)
        }
    }

    val hotNewData = dataSource.hotNewData
    fun fetchHotNew() {
        // Launch a coroutine that reads from a remote data source and updates cache
        viewModelScope.launch {
            dataSource.fetchHotNew()
        }
    }

    val hotNewNextData = dataSource.hotNewNextData
    fun fetchHotNewNext(nextPageUrl: String?) {
        // Launch a coroutine that reads from a remote data source and updates cache
        viewModelScope.launch {
            dataSource.fetchHotNewNext(nextPageUrl)
        }
    }

    val hotSearchData = dataSource.hotSearchData
    fun fetchHotSearch() {
        // Launch a coroutine that reads from a remote data source and updates cache
        viewModelScope.launch {
            dataSource.fetchHotSearch()
        }
    }

    val hotSearchNextData = dataSource.hotSearchNextData
    fun fetchHotSearchNext(nextPageUrl: String?) {
        // Launch a coroutine that reads from a remote data source and updates cache
        viewModelScope.launch {
            dataSource.fetchHotSearchNext(nextPageUrl)
        }
    }
}