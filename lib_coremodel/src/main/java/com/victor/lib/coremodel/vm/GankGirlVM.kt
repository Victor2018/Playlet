package com.victor.lib.coremodel.vm

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import com.victor.lib.coremodel.interfaces.IGankGirlDS

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: GankGirlVm
 * Author: Victor
 * Date: 2021/2/24 17:25
 * Description: 
 * -----------------------------------------------------------------
 */
class GankGirlVM(private val dataSource: IGankGirlDS): ViewModel() {

    val gankGirlData = dataSource.gankGirlData
    fun fetchGankGirl() {
        // Launch a coroutine that reads from a remote data source and updates cache
        viewModelScope.launch {
            dataSource.fetchGankGirl()
        }
    }
}