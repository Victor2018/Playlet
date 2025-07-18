package com.victor.lib.common.interfaces

import android.view.View

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: StickyView
 * Author: Victor
 * Date: 2022/4/1 12:13
 * Description: 
 * -----------------------------------------------------------------
 */

interface StickyView {
    /**
     * 是否是吸附view
     * @param view
     * @return
     */
    fun isStickyView(view: View?): Boolean

    /**
     * 得到吸附view的itemType
     * @return
     */
    fun getStickViewType(): Int
}