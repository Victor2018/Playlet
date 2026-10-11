package com.victor.lib.common.base

import android.app.Application
import androidx.multidex.MultiDex
import com.victor.neuro.router.core.NeuroRouter

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

        NeuroRouter.instance.init(this)
        MultiDex.install(this)
    }
}