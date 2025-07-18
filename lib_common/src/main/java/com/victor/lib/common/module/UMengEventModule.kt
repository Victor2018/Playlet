package com.victor.lib.common.module

import android.content.Context
import com.umeng.analytics.MobclickAgent
import com.umeng.commonsdk.UMConfigure
import com.victor.lib.common.util.Loger
import com.victor.lib.coremodel.util.AppConfig
import com.victor.lib.coremodel.util.AppUtil

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: UMengEventModule
 * Author: Victor
 * Date: 2022/3/1 18:28
 * Description: UMeng 时间上报模块 release 线上版本才上报
 * -----------------------------------------------------------------
 */

object UMengEventModule {

    val TAG = "UMengEventModule"
//    val canReport = !AppConfig.MODEL_DEBUG && AppConfig.MODEL_ONLINE

    fun reportEnable (): Boolean {
        var canReport = AppUtil.isTestChannel()
        if (AppConfig.MODEL_ONLINE) {
            canReport = !AppConfig.MODEL_DEBUG
        }
        Loger.e(TAG,"reportEnable()-canReport = $canReport")
        return canReport
    }

    fun preInitSdk (context: Context?) {
        if (!reportEnable()) return
        UMConfigure.preInit(context, getUmengAppKey(), AppConfig.UMENG_CHANNEL)
    }

    fun initSdk(context: Context?) {
        if (!reportEnable()) return
        //设置LOG开关，默认为false
        UMConfigure.setLogEnabled(AppConfig.MODEL_DEBUG)

        UMConfigure.init(context, getUmengAppKey(), AppConfig.UMENG_CHANNEL,
            UMConfigure.DEVICE_TYPE_PHONE,null)

        //统计SDK是否支持采集在子进程中打点的自定义事件，默认不支持
        UMConfigure.setProcessEvent(true) //支持多进程打点

        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO)
    }

    fun getUmengAppKey (): String {
        var umengAppKey = AppConfig.UMENG_APP_KEY
        if (AppUtil.isTestChannel()) {
            umengAppKey = AppConfig.UMENG_TEST_APP_KEY
        }
        Loger.e(TAG,"getUmengAppKey()-umengAppKey = $umengAppKey")
        return umengAppKey
    }

    fun report (context: Context?,eventID: String?) {
        if (!reportEnable()) return
        Loger.e(TAG,"report()-eventID = $eventID")
        MobclickAgent.onEvent(context,eventID)
    }

    fun report (context: Context?,eventID: String?,param: String?) {
        if (!reportEnable()) return
        MobclickAgent.onEvent(context,eventID,param)
    }

    fun report (context: Context?,eventID: String?,map: Map<String, Any?>?) {
        if (!reportEnable()) return
        MobclickAgent.onEventObject(context,eventID,map)
    }

}