package com.victor.lib.coremodel.datasource

import androidx.lifecycle.MutableLiveData
import com.victor.lib.coremodel.data.bean.GankDetailEntity
import com.victor.lib.coremodel.interfaces.IGankGirlDS
import com.victor.lib.coremodel.service.GankApiService
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
 * File: GankGirlDS
 * Author: Victor
 * Date: 2021/2/24 16:42
 * Description: 
 * -----------------------------------------------------------------
 */
class GankGirlDS(private val ioDispatcher: CoroutineDispatcher): AbsDS(),IGankGirlDS {
    override val gankGirlData = MutableLiveData<HttpResult<GankDetailEntity>>()

    override suspend fun fetchGankGirl() {
        // Force Main thread
        withContext(Dispatchers.Main) {
            gankGirlData.value = fetchGankGirlReq()
        }

    }

    private suspend fun <T> fetchGankGirlReq(): T = withContext(ioDispatcher) {

        handleRespone(ApiClient.getApiService(GankApiService::class.java)
            .fetchGankGirl()) as T
    }
}