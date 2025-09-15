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

    var updatePlayPositionData = dataSource.updatePlayPositionData

    val dramaListData = dataSource.dramaListData
    fun fetchDramaList() {
        // Launch a coroutine that reads from a remote data source and updates cache
        viewModelScope.launch {
            dataSource.fetchDramaList()
        }
    }

    val dramaListNextData = dataSource.dramaListNextData
    fun fetchDramaListNext(nextPageUrl: String?) {
        // Launch a coroutine that reads from a remote data source and updates cache
        viewModelScope.launch {
            dataSource.fetchDramaListNext(nextPageUrl)
        }
    }

    val homePlayingData = dataSource.homePlayingData
    fun fetchHomePlaying(id: Int) {
        // Launch a coroutine that reads from a remote data source and updates cache
        viewModelScope.launch {
            dataSource.fetchHomePlaying(id)
        }
    }

    val homePlayingNextData = dataSource.homePlayingNextData
    fun fetchHomePlayingNext(nextPageUrl: String?) {
        // Launch a coroutine that reads from a remote data source and updates cache
        viewModelScope.launch {
            dataSource.fetchHomePlayingNext(nextPageUrl)
        }
    }
}