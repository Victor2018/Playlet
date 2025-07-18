package com.victor.lib.coremodel.data.response

import android.text.TextUtils

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: BaseRes
 * Author: Victor
 * Date: 2025/7/17 12:06
 * Description: 
 * -----------------------------------------------------------------
 */

open class BaseRes<T> {
    var code: Int = 0//0 成功，否则失败
    var msg: String? = null//请求失败的错误信息
    var message: String? = null//请求失败的错误信息
    var data: T? = null

    fun getErrorMsg (): String? {
        if (TextUtils.isEmpty(msg)) {
            return message
        }
        return msg
    }
}