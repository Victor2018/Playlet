package com.victor.lib.common.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.LayoutInflater
import android.view.View

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: ViewUtils
 * Author: Victor
 * Date: 2022/3/1 18:28
 * Description: 
 * -----------------------------------------------------------------
 */

object ViewUtils {
    fun View.show() {
        visibility = View.VISIBLE
    }

    fun View.hide() {
        visibility = View.GONE
    }
    fun View.invisible() {
        visibility = View.INVISIBLE
    }

    fun getViewByLayout (context: Context?,layoutId: Int): View {
        var inflater = LayoutInflater.from(context)
        return inflater.inflate(layoutId, null)
    }

    fun getViewByLayout (inflater: LayoutInflater,layoutId: Int): View {
        return inflater.inflate(layoutId, null)
    }

    fun View.bitmap(): Bitmap {
        //不加下面两句，会报错：width and height must be > 0
        measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))

        layout(0, 0, measuredWidth, measuredHeight)

        val bitmap = Bitmap.createBitmap(measuredWidth, measuredHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        draw(canvas)
        return bitmap
    }
}