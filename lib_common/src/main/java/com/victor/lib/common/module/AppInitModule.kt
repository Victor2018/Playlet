package com.victor.lib.common.module

import android.app.Application
import android.text.TextUtils
import com.victor.lib.common.util.SharedPreferencesUtils
import com.victor.lib.common.app.App
import com.victor.lib.common.util.Loger
import com.victor.lib.coremodel.util.DeviceUtils
import com.victor.lib.coremodel.util.HttpUtil
import org.victor.http.lib.ApiClient


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: AppInitModule
 * Author: Victor
 * Date: 2022/3/1 18:28
 * Description: 第三方sdk初始化涉及到设备的信息采集必须在同意隐私政策之后调用
 * -----------------------------------------------------------------
 */

object AppInitModule {

    val TAG = "AppInitModule"

    /**
     * 初始化第三方sdk
     */
    fun initThirdSdk (context: Application) {
        //友盟
        UMengEventModule.initSdk(context)

        //初始化http请求头数据
//        ApiClient.addHeader(JsonUtils.toJSONString(HttpUtil.getHttpHeaderParm(context)))

        val token = App.get().getLoginData()?.token ?: ""
        Loger.e(TAG,"initThirdSdk-token = $token")
        ApiClient.addHeader("VersionCode",HttpUtil.getHttpHeaderParm(context).VersionCode ?: "")
        ApiClient.addHeader("AccessToken", token)


        //添加udid header 未登录推荐使用
        var udid = SharedPreferencesUtils.udid
        if (TextUtils.isEmpty(udid)) {
            udid = DeviceUtils.getUDID(context)
        }
        ApiClient.addHeader("DeviceUDid", udid)
    }
}