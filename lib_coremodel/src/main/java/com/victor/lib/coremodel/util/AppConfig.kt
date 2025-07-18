package com.victor.lib.coremodel.util

import com.victor.lib.coremodel.BuildConfig

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: AppConfig
 * Author: Victor
 * Date: 2022/3/1 12:03
 * Description: 
 * -----------------------------------------------------------------
 */

object AppConfig {
    /**
     * DEBUG 调试模式
     */
    val MODEL_DEBUG = BuildConfig.MODEL_DEBUG

    /**
     * 开发模式
     */
    val MODEL_DEV = BuildConfig.MODEL_DEV

    /**
     * 测试模式
     */
    val MODEL_TEST = BuildConfig.MODEL_TEST

    /**
     * UAT测试模式
     */
    val MODEL_BETA = BuildConfig.MODEL_BETA
    /**
     * 线上模式
     */
    val MODEL_ONLINE = BuildConfig.MODEL_ONLINE


    /**
     * 微信AppID
     */
    const val WECHAT_APP_ID = "wx5bbbb66cb79afe87"

    /**
     * qq AppID
     */
    const val QQ_APP_ID = "1111965449"

    const val UMENG_APP_KEY = "6259616fd024421570bcdbbd"
    const val UMENG_TEST_APP_KEY = "63e5fec8ba6a5259c4fff915"
    const val UMENG_CHANNEL = BuildConfig.UMENG_CHANNEL

    const val HTTP = "http"
    const val SCHEMA = "victor://"

    const val PACKAGE_QQ = "com.tencent.mobileqq"
    const val WECHAT_PROFILE = "weixin://biz/ww/profile"


    // 支付宝的包名
    const val ALI_PAY_PACKAGE_NAME = "com.eg.android.AlipayGphone"

}