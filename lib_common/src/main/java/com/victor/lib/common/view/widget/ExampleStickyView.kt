package com.victor.lib.common.view.widget

import android.view.View
import com.victor.lib.common.interfaces.StickyView

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: ExampleStickyView
 * Author: Victor
 * Date: 2022/4/1 12:13
 * Description: 
 * -----------------------------------------------------------------
 */

class ExampleStickyView : StickyView {
    override fun isStickyView(view: View?) = view?.tag?.toString()?.toBoolean() ?: false

    override fun getStickViewType() = 3
}