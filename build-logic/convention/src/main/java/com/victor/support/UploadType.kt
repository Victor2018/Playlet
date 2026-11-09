package com.victor.support

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2025-2035, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: UploadType
 * Author: Victor
 * Date: 2026/9/1 17:28
 * Description: 
 * -----------------------------------------------------------------
 */

enum class UploadType(
    val code: Int,
    val channel: String,
    val buildType: String,
    val taskName: String,
    val firApiKey: String,
    val pgyerApiKey: String,
    val reinforcer360: Boolean,
) {
    //业务场景编码,示例值
    DEV(1, "dev", "debug","assembleDevDebug","","",false), // 开发环境
    TEST(2, "_test", "debug","assemble_TestDebug", UploadApkConfig.FIR_API_TOKEN,UploadApkConfig.PGYER_API_KEY_TEST,false),//测试环境
    BETA(3, "beta", "debug","assembleBetaDebug",UploadApkConfig.FIR_API_TOKEN,UploadApkConfig.PGYER_API_KEY_BETA,false),//beta环境
    ONLINE_DEBUG(4, "online", "debug","assembleOnlineDebug","","",false),//线上环境debug
    ONLINE_RELEASE(5, "online", "release","assembleOnlineRelease",UploadApkConfig.FIR_API_TOKEN,UploadApkConfig.PGYER_API_KEY_ONLINE,false),//线上环境release
    HUAWEI_RELEASE(6, "huawei", "release","assembleHuaweiRelease","","",false),//华为线上release
    XIAOMI_RELEASE(7, "xiaomi", "release","assembleXiaomiRelease","","",false),//小米线上release
    OPPO_RELEASE(8, "oppo", "release","assembleOppoRelease","","",false),//oppo线上release
    VIVO_RELEASE(9, "vivo", "release","assembleVivoRelease","","",false),//vivo线上release
    TENCENT_RELEASE(10, "tencent", "release","assembleTencentRelease","","",false);//tencent线上release

    companion object {
        fun typeOf(code: Int): UploadType {
            return values().find { it.code == code } ?: TEST
        }
        fun taskNameOf(taskName: String?): UploadType {
            return values().find { it.taskName == taskName} ?: TEST
        }
    }
}