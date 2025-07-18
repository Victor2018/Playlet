package com.victor.lib.common.util.security

import android.content.Context
import com.victor.lib.common.util.Loger
import com.victor.lib.coremodel.util.AppConfig

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File:  SecurityCheckUtil
 * Author: Victor
 * Date: 2022/3/1 18:28
 * Description: 会获取已安装应用列表，华为应用商店审核会失败，所以华为渠道直接 return true
 * -----------------------------------------------------------------
 */

object SecurityCheckUtil {
    const val TAG = "SecurityCheckUtil"

    fun isDeviceSecurity (context: Context): Boolean {
        if (AppConfig.MODEL_DEBUG) return true

        val isRoot = CheckRootUtil.isDeviceRooted()
//        val isHook = CheckHookUtil.isHook(context)
        var isEmulator = EmulatorUtil.isEmulator(context)
//        val isRunInVirtual = CheckVirtualUtil.isRunInVirtual()
        Loger.e(TAG,"isDeviceSecurity-isRoot = $isRoot")
//        Loger.e(TAG,"isDeviceSecurity-isHook = $isHook")
        Loger.e(TAG,"isDeviceSecurity-isEmulator = $isEmulator")
//        Loger.e(TAG,"isDeviceSecurity-isRunInVirtual = $isRunInVirtual")
        return !isRoot && !isEmulator
    }

}