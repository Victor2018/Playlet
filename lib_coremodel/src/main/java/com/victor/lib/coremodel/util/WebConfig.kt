package com.victor.lib.coremodel.util

import android.net.Uri
import androidx.core.net.toUri

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: WebConfig
 * Author: Victor
 * Date: 2022/3/1 12:03
 * Description: 
 * -----------------------------------------------------------------
 */

object WebConfig {
    const val DEV_BASE_URL = "http://baobab.kaiyanapp.com/"
    const val TEST_BASE_URL = "http://baobab.kaiyanapp.com/"
    const val BETA_BASE_URL = "http://baobab.kaiyanapp.com/"
    const val ONLINE_BASE_URL = "http://baobab.kaiyanapp.com/"

    const val H5_ONLINE_BASE_URL = "http://baobab.kaiyanapp.com/"

    const val PAGE_SIZE_10 = 10
    const val PAGE_SIZE = 20

    fun getBaseUrl(): String {
        return when(AppConfig.HTTP_ENV_CODE) {
            HttpEnv.DEV.code -> DEV_BASE_URL
            HttpEnv.TEST.code -> TEST_BASE_URL
            HttpEnv.BETA.code -> BETA_BASE_URL
            HttpEnv.ONLINE.code -> ONLINE_BASE_URL
            else -> DEV_BASE_URL
        }
    }

    fun getServerIp(): String? {
        val uri: Uri = getBaseUrl().toUri()
        return uri.host
    }

    fun getRequestUrl(api: String): String {
        return getBaseUrl() + api
    }

}