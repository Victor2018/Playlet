package com.victor.lib.common.etfilter

import android.text.InputFilter
import android.text.Spanned

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: DecimalDigitsInputFilter
 * Author: Victor
 * Date: 2022/3/1 18:28
 * Description: Input filter that limits the number of decimal digits that are allowed to be entered.
 * -----------------------------------------------------------------
 */

class DecimalDigitsInputFilter(var decimalDigits: Int): InputFilter {

    override fun filter(source: CharSequence?, start: Int, end: Int, dest: Spanned?, dstart: Int, dend: Int): CharSequence? {
        var dotPos = -1
        val len = dest?.length
        for (i in 0 until len!!) {
            val c = dest[i]
            if (c == '.' || c == ',') {
                dotPos = i
                break
            }
        }
        if (dotPos >= 0) {

            // protects against many dots
            if (source == "." || source == ",") {
                return ""
            }
            // if the text is entered before the dot
            if (dend <= dotPos) {
                return null
            }
            if (len - dotPos > decimalDigits) {
                return ""
            }
        }
        return null
    }

}