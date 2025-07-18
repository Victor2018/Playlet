package com.victor.lib.common.view.widget

import android.content.Context
import android.util.DisplayMetrics
import androidx.recyclerview.widget.LinearSmoothScroller

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: CenterSmoothScroller
 * Author: Victor
 * Date: 2023/08/04 16:12
 * Description: 
 * -----------------------------------------------------------------
 */

class CenterSmoothScroller(context: Context?) : LinearSmoothScroller(context) {
    var duration = 100f //越小越快
    //25f是系统的smoothScrollToPosition的速度
    override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float {
        return duration / displayMetrics.densityDpi
    }

    override fun calculateDtToFit(
        viewStart: Int,
        viewEnd: Int,
        boxStart: Int,
        boxEnd: Int,
        snapPreference: Int
    ): Int {
        return boxStart + (boxEnd - boxStart) / 2 - (viewStart + (viewEnd - viewStart) / 2)
    }
}