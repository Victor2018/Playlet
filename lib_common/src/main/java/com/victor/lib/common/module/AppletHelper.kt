package com.victor.lib.common.module

import android.content.Context
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.victor.lib.common.R
import com.victor.lib.common.app.App
import com.victor.lib.common.util.ToastUtils
import com.victor.lib.coremodel.util.AppConfig
import com.victor.lib.coremodel.util.AppUtil

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: AppletHelper
 * Author: Victor
 * Date: 2023/2/2 17:44
 * Description: 
 * -----------------------------------------------------------------
 */

class AppletHelper {

    private object Holder { val instance = AppletHelper() }

    companion object {
        val instance: AppletHelper by lazy { Holder.instance }
    }

    private var mIWxApi: IWXAPI? = null

    fun launchApplet (context: Context?,userName: String?,map: HashMap<String,String>) {
        mIWxApi = getIWXAPI()
        if (!AppUtil.isWXAppInstalledAndSupported(mIWxApi!!)) {
            ToastUtils.show(R.string.not_install_wx_tip)
            return
        }
        AppUtil.launchApplet(context,userName,map)
    }

    fun launchApplet (context: Context?,userName: String?,path: String?,extData: String?) {
        mIWxApi = getIWXAPI()
        if (!AppUtil.isWXAppInstalledAndSupported(mIWxApi!!)) {
            ToastUtils.show(R.string.not_install_wx_tip)
            return
        }
        AppUtil.launchApplet(context,userName,path,extData)
    }

    private fun getIWXAPI(): IWXAPI? {
        if (mIWxApi == null) {
            mIWxApi = WXAPIFactory.createWXAPI(App.get(), AppConfig.WECHAT_APP_ID, true)
            mIWxApi?.registerApp(AppConfig.WECHAT_APP_ID)
        }
        return mIWxApi
    }
}