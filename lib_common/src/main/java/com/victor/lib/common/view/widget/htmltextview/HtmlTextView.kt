package com.victor.lib.common.view.widget.htmltextview

import android.content.Context
import android.text.Html
import android.text.method.LinkMovementMethod
import android.text.method.ScrollingMovementMethod
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: HtmlTextView
 * Author: Victor
 * Date: 2024/04/08 17:27
 * Description: 
 * -----------------------------------------------------------------
 */

class HtmlTextView : AppCompatTextView {

    constructor(context: Context) : this(context,null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs,0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        movementMethod = ScrollingMovementMethod()
        movementMethod = LinkMovementMethod.getInstance()
        isClickable = true
    }

    fun loadData(html: String) {
        //避免宽度比例计算出错
        post {
            val imgGetter = HtmlHttpImageGetter(this,true)
            val htmlSpan = Html.fromHtml(html, imgGetter, null)
            text = htmlSpan
        }
    }
}