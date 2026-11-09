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
     * 手动上传至fir、蒲公英渠道名称
     * 1:dev_debug; 2:_test_debug; 3:beta_debug; 4:online_debug; 5:online_release;
     * 6:huawei_release;7:xiaomi_release;8:oppo_release;9:vivo_release;10:tencent_release;
     */
    const val UPLOAD_CHANNEL_BUILD_TYPE = 5

    /**
     * 上传apk到https://fir.im/apps api_token
     */
    const val FIR_API_TOKEN = "d7352002b538043cc94e94b6d6095ab6"

    /**
     * 上传apk到https://www.pgyer.com/ api_key
     */
    const val PGYER_API_KEY_TEST = "4c75404d7c40b535dc8b4833694e9221"
    const val PGYER_API_KEY_BETA = "4c75404d7c40b535dc8b4833694e9221"
    const val PGYER_API_KEY_ONLINE = "4c75404d7c40b535dc8b4833694e9221"

    /**
     * fir 上传 url地址
     */
    const val FIR_UPLOAD_URL = "http://api.bq04.com/apps"

    /**
     * 是否上传到fir
     */
    const val UPLOAD_TO_FIR = false

    /**
     * 是否上传到pgyer
     */
    const val UPLOAD_TO_PGYER = true

    /**
     * 获取编译apk文件名
     */
    fun getBuildApkFileName(channel: String,buildType: String,reinforceApk: Boolean): String {
        val sb = StringBuffer()
        sb.append("${AppBuildConfig.BUILD_NAME}_${channel}_${buildType}")
        sb.append("_v${AppBuildConfig.VERSION_NAME}_${AppBuildConfig.VERSION_CODE}")
        sb.append("_${AppBuildConfig.getBuildDate()}_${AppBuildConfig.BUILD_NUMBER}")
        if (reinforceApk) {
            sb.append("_${AppBuildConfig.VERSION_NAME.replace(".","")}_jiagu_sign")
        }
        sb.append(".apk")

        return sb.toString()
    }
}