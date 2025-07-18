package com.victor.lib.coremodel.util

import android.net.Uri

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
    const val DEV_BASE_URL = "https://dev.cloud.hokkj.cn/"
    const val TEST_BASE_URL = "https://test.cloud.hokkj.cn/"
    const val BETA_BASE_URL = "https://beta.cloud.hokkj.cn/"
    const val ONLINE_BASE_URL = "https://cloud.hokkj.cn/"

    const val H5_DEV_BASE_URL = "https://dev.h5.hokkj.cn/"
    const val H5_TEST_BASE_URL = "https://test.h5.hokkj.cn/"
    const val H5_BETA_BASE_URL = "https://beta.h5.hokkj.cn/"
    const val H5_ONLINE_BASE_URL = "https://h5.hokkj.cn/"

    const val PAGE_SIZE_10 = 10
    const val PAGE_SIZE = 20

    fun getBaseUrl(): String {
        if (AppConfig.MODEL_TEST) {
            return TEST_BASE_URL
        }

        if (AppConfig.MODEL_BETA) {
            return BETA_BASE_URL
        }

        if (AppConfig.MODEL_ONLINE) {
            return ONLINE_BASE_URL
        }
        return DEV_BASE_URL
    }

    fun getH5BaseUrl(): String {
        if (AppConfig.MODEL_TEST) {
            return H5_TEST_BASE_URL
        }

        if (AppConfig.MODEL_BETA) {
            return H5_BETA_BASE_URL
        }

        if (AppConfig.MODEL_ONLINE) {
            return H5_ONLINE_BASE_URL
        }
        return H5_DEV_BASE_URL
    }

    fun getServerIp(): String? {
        val uri: Uri = Uri.parse(getBaseUrl())
        return uri.host
    }

    fun getRequestUrl(api: String): String? {
        return getBaseUrl() + api
    }

}