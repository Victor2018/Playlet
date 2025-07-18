package com.victor.lib.common.util

import android.util.Log
import com.victor.lib.coremodel.util.AppConfig

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: Loger
 * Author: Victor
 * Date: 2022/3/1 18:28
 * Description: 日志打印工具
 * -----------------------------------------------------------------
 */

object Loger {
    fun d(TAG: String, msg: Any) {
        if (AppConfig.MODEL_DEBUG) {
            Log.d(TAG, msg.toString())
        }
    }

    fun e(TAG: String, msg: Any) {
        if (AppConfig.MODEL_DEBUG) {
            Log.e(TAG, msg.toString())
        }
    }

    fun i(TAG: String, msg: Any) {
        if (AppConfig.MODEL_DEBUG) {
            Log.i(TAG, msg.toString())
        }
    }

    fun v(TAG: String, msg: Any) {
        if (AppConfig.MODEL_DEBUG) {
            Log.v(TAG, msg.toString())
        }
    }

    fun w(TAG: String, msg: Any) {
        if (AppConfig.MODEL_DEBUG) {
            Log.w(TAG, msg.toString())
        }
    }
}