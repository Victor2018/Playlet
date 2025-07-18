package com.hok.lib.common.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: CoroutineUtil
 * Author: Victor
 * Date: 2024/03/21 18:49
 * Description: 
 * -----------------------------------------------------------------
 */

object CoroutineUtil {
    fun runOnMain() = CoroutineScope(Dispatchers.Main).launch {

    }

    fun runOnIO() = CoroutineScope(Dispatchers.IO).launch {

    }
}