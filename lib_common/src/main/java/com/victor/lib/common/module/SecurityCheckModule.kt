package com.victor.lib.common.module

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.victor.lib.common.util.security.SecurityCheckUtil
import com.victor.lib.common.app.App

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: SecurityCheckModule
 * Author: Victor
 * Date: 2022/3/1 18:28
 * Description: 备注检查
 * -----------------------------------------------------------------
 */

object SecurityCheckModule {
    val isDeviceSecurity = checkDeviceSecurity()

    fun checkDeviceSecurity(): LiveData<Boolean> = liveData {
        emit(SecurityCheckUtil.isDeviceSecurity(App.get()))
    }

}