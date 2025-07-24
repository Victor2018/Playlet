package com.victor.lib.coremodel.data.remote.vm

import androidx.lifecycle.*
import com.victor.lib.coremodel.data.remote.interfaces.IHomeDS
import kotlinx.coroutines.launch

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: HomeVM
 * Author: Victor
 * Date: 2021/2/24 17:25
 * Description: 
 * -----------------------------------------------------------------
 */
class HomeVM(private val dataSource: IHomeDS): ViewModel() {

    val dramaListData = dataSource.dramaListData
    fun fetchDramaList() {
        // Launch a coroutine that reads from a remote data source and updates cache
        viewModelScope.launch {
            dataSource.fetchDramaList()
        }
    }

    val hotRecommendData = dataSource.hotRecommendData
    fun fetchHotRecommend() {
        // Launch a coroutine that reads from a remote data source and updates cache
        viewModelScope.launch {
            dataSource.fetchHotRecommend()
        }
    }

    val hotPlayData = dataSource.hotPlayData
    fun fetchHotPlay() {
        // Launch a coroutine that reads from a remote data source and updates cache
        viewModelScope.launch {
            dataSource.fetchHotPlay()
        }
    }

    val hotNewData = dataSource.hotNewData
    fun fetchHotNew() {
        // Launch a coroutine that reads from a remote data source and updates cache
        viewModelScope.launch {
            dataSource.fetchHotNew()
        }
    }

    val hotSearchData = dataSource.hotSearchData
    fun fetchHotSearch() {
        // Launch a coroutine that reads from a remote data source and updates cache
        viewModelScope.launch {
            dataSource.fetchHotSearch()
        }
    }

    val homePlayingData = dataSource.homePlayingData
    fun fetchHomePlaying() {
        // Launch a coroutine that reads from a remote data source and updates cache
        viewModelScope.launch {
            dataSource.fetchHomePlaying()
        }
    }
}