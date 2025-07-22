package com.victor.lib.common.view.widget

import android.content.Context
import android.util.AttributeSet
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.victor.lib.common.R

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: ColorSwipeRefreshLayout
 * Author: Victor
 * Date: 2022/10/17 12:13
 * Description: 
 * -----------------------------------------------------------------
 */

open class ColorSwipeRefreshLayout : SwipeRefreshLayout {
    constructor(context: Context) : this(context,null)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        setColorSchemeResources(R.color.colorAccent)
    }
}