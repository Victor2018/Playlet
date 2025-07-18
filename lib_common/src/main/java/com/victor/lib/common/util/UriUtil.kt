package com.victor.lib.common.util

import android.net.Uri

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: UriUtil
 * Author: Victor
 * Date: 2024/05/28 16:18
 * Description: 
 * -----------------------------------------------------------------
 */

object UriUtil {
    fun getParm(url: String?,key: String?): String {
        //   //https://huoke5.hokkj.cc/pages/liveSub/pages/liveWXAuthH5/index?
        //        // rId=1795277264894889986&shopId=1000&tId=1565254610684264450&t=1716884460445
        var value = ""
        try {
            val uri = Uri.parse(url)
            value = uri.getQueryParameter(key) ?: ""
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return value
    }
}