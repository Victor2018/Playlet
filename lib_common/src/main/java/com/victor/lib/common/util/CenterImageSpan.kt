package com.victor.lib.common.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.text.style.ImageSpan


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: CenterImageSpan
 * Author: Victor
 * Date: 2023/2/16 11:52
 * Description: 
 * -----------------------------------------------------------------
 */

class CenterImageSpan(context: Context, bitmap: Bitmap) : ImageSpan(context, bitmap) {

    override fun getSize(
        paint: Paint,
        text: CharSequence?,
        start: Int,
        end: Int,
        fm: Paint.FontMetricsInt?
    ): Int {
        fm?.let {
            val fontHeight = paint.fontMetricsInt.descent - paint.fontMetricsInt.ascent
            val imageHeight = drawable.bounds.height()
            it.ascent = paint.fontMetricsInt.ascent - ((imageHeight - fontHeight) / 2.0f).toInt()
            it.top = it.ascent
            it.descent = it.ascent + imageHeight
            it.bottom = it.descent
        }
        return drawable.bounds.right
    }

    override fun draw(
        canvas: Canvas,
        text: CharSequence?,
        start: Int,
        end: Int,
        x: Float,
        top: Int,//line top,It is the largest top in line.
        y: Int,//baseline y position
        bottom: Int,//line bottom,contain lineSpacingExtra if there is more than one line.
        paint: Paint
    ) {
        val fontHeight = paint.fontMetricsInt.descent - paint.fontMetricsInt.ascent
        val imageAscent = paint.fontMetricsInt.ascent - ((drawable.bounds.height() - fontHeight) / 2.0f).toInt()
        canvas.save()
        canvas.translate(x, (y + imageAscent).toFloat())
        drawable.draw(canvas)
        canvas.restore()
    }
}