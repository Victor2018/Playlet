package com.victor.lib.common.base

import android.app.Application
import androidx.multidex.MultiDex
import com.alibaba.android.arouter.launcher.ARouter
import com.victor.lib.coremodel.util.AppConfig

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: BaseApplication
 * Author: Victor
 * Date: 2022/3/1 18:28
 * Description: 
 * -----------------------------------------------------------------
 */

abstract class BaseApplication: Application() {
    companion object {
        private lateinit var instance : BaseApplication
        fun get() = instance
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        if (AppConfig.MODEL_DEBUG) {
            //开启InstantRun之后，一定要在ARouter.init之前调用openDebug
            ARouter.openDebug()
            ARouter.openLog()
        }
        ARouter.init(this)
        MultiDex.install(this)
    }
}