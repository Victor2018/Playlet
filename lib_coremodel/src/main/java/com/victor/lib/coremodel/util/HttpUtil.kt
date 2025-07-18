package com.victor.lib.coremodel.util

import android.content.Context
import com.victor.lib.coremodel.data.request.HttpHeaderReq

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: HttpUtil
 * Author: Victor
 * Date: 2022/3/1 12:03
 * Description: 
 * -----------------------------------------------------------------
 */

object HttpUtil {
    fun getHttpHeaderParm(context: Context?): HttpHeaderReq {
        val header = HttpHeaderReq()
        header.deviceId = DeviceUtils.getUDID(context)
        header.deviceBrand = DeviceUtils.getPhoneBrand()
        header.deviceModel = DeviceUtils.getPhoneModel()
        header.systemVersion = DeviceUtils.getSysVersion()
        header.systemType = "Android"
        header.VersionCode = "ANDROID_${AppUtil.getAppVersionName(context)}"
        return header
    }
}