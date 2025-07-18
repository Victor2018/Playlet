package com.victor.lib.common.interfaces

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: OnCountDownTimerTickListener
 * Author: Victor
 * Date: 2022/3/1 18:28
 * Description: 
 * -----------------------------------------------------------------
 */

interface OnCountDownTimerListener {
    fun onTick(millisUntilFinished: Long)
    fun onFinish()
}