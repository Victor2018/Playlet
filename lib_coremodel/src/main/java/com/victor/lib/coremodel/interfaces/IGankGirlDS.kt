package com.victor.lib.coremodel.interfaces

import androidx.lifecycle.LiveData
import com.victor.lib.coremodel.data.bean.GankDetailEntity
import org.victor.http.lib.data.HttpResult


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: IGankGirlDS
 * Author: Victor
 * Date: 2021/2/24 16:37
 * Description: 
 * -----------------------------------------------------------------
 */
interface IGankGirlDS {
    val gankGirlData: LiveData<HttpResult<GankDetailEntity>>
    suspend fun fetchGankGirl()
}