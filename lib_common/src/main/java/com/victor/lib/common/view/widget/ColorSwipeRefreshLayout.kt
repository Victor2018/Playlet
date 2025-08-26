package com.victor.lib.common.view.widget

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.victor.lib.common.R
import com.victor.lib.common.util.ResUtils

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
    private var srlProgressBackgroundColorScheme: Int = Color.BLUE
    private var srlProgressViewStartOffset = 0
    private var srlProgressViewEndOffset = 100
    private var srlProgressViewScale = false

    constructor(context: Context) : this(context,null)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initAttrs(attrs)
    }

    private fun initAttrs(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ColorSwipeRefreshLayout)
        srlProgressBackgroundColorScheme = typedArray.getColor(R.styleable.ColorSwipeRefreshLayout_srlProgressBackgroundColorScheme, ResUtils.getColorRes(R.color.colorAccent))
        srlProgressViewStartOffset = typedArray.getDimensionPixelSize(R.styleable.ColorSwipeRefreshLayout_srlProgressViewStartOffset, 0)
        srlProgressViewEndOffset = typedArray.getDimensionPixelSize(R.styleable.ColorSwipeRefreshLayout_srlProgressViewEndOffset, 100)
        srlProgressViewScale = typedArray.getBoolean(R.styleable.ColorSwipeRefreshLayout_srlProgressViewScale, false)
        typedArray.recycle()

        setColorSchemeColors(srlProgressBackgroundColorScheme)
        setProgressViewOffset(srlProgressViewScale,srlProgressViewStartOffset, srlProgressViewEndOffset)
    }
}