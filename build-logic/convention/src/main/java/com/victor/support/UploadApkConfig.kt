package com.victor.support

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: UploadApkConfig
 * Author: Victor
 * Date: 2025/7/17 15:51
 * Description: 测试分发apk配置
 * -----------------------------------------------------------------
 */
object UploadApkConfig {
    /**
     * 上传apk到https://fir.im/apps api_token
     */
    const val FIR_API_TOKEN = "d7352002b538043cc94e94b6d6095ab6"

    /**
     * 上传apk到https://www.pgyer.com/ api_key
     */
    const val PGYER_API_KEY = "700d1e7617b5ec4f57cba00f05c7ca84"

    /**
     * fir 上传 url地址
     */
    const val FIR_UPLOAD_URL = "http://api.bq04.com/apps"

    /**
     * 上传至fir渠道名称
     */
    const val UPLOAD_CHANNEL = "beta"

    /**
     * 上传至fir buildType
     */
    const val UPLOAD_BUILD_TYPE = "debug"

    /**
     * 是否上传到fir
     */
    const val UPLOAD_TO_FIR = false

    /**
     * 是否上传到pgyer
     */
    const val UPLOAD_TO_PGYER = false

}