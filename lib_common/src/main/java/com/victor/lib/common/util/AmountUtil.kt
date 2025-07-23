package com.victor.lib.common.util

import android.text.SpannableString
import android.text.TextUtils
import java.math.BigDecimal
import java.text.DecimalFormat
import java.util.regex.Pattern

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: AmountUtil
 * Author: Victor
 * Date: 2022/3/1 18:28
 * Description: 金额转换工具
 * -----------------------------------------------------------------
 */

object AmountUtil {

    val TAG = "AmountUtil"
    /**
     * 四舍五入
     *
     * @param value 数值
     * @param digit 保留小数位
     * @return
     */
    fun getRoundUp(value: Double?, digit: Int): String? {
        var result = "0.0"
        try {
            var inputValue = value.toString()
            if (TextUtils.isEmpty(inputValue)) {
                inputValue = "0.0"
            }
            val bigDecimal = BigDecimal(inputValue)
            result =  bigDecimal.setScale(digit, BigDecimal.ROUND_HALF_UP).toString()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return result
    }

    fun getRoundUpDouble(value: Double?, digit: Int): Double {
        var result = 0.0
        try {
            var inputValue = value.toString()
            if (TextUtils.isEmpty(inputValue)) {
                inputValue = "0.0"
            }
            val bigDecimal = BigDecimal(inputValue)
            result =  bigDecimal.setScale(digit, BigDecimal.ROUND_HALF_UP).toDouble()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return result
    }

    fun getRoundUpString(value: Double?, digit: Int): String {
        var result = "0.0"
        try {
            var inputValue = value.toString()
            if (TextUtils.isEmpty(inputValue)) {
                inputValue = "0.0"
            }
            val bigDecimal = BigDecimal(inputValue)
            result =  bigDecimal.setScale(digit, BigDecimal.ROUND_HALF_UP).toString()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return result
    }

    /**
     * 将每三个数字加上逗号处理,最后保留两位小数（通常使用金额方面的编辑）示例：9，702.44
     *
     * @param str
     * @return
     */
    fun addCommaDots(value: Double?): String {
        var result = "0.00"
        try {
            val myformat = DecimalFormat()
            myformat.applyPattern("##0.00")
//            myformat.applyPattern(",##0.00")
//            myformat.applyPattern(",##0.##")//去除小数点后多余的0
            result = myformat.format(value)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return result
    }

    fun addCommaDotsNoZero(value: Double,digit: Int): String {
        var result = "0.00"
        try {
            val myformat = DecimalFormat()
            var pattenSb = StringBuffer(",##0.")
            if (digit == 0) {
                pattenSb = StringBuffer(",##0")
            } else {
                for (i in 0 until digit) {
                    pattenSb.append("#")
                }
            }
            myformat.applyPattern(pattenSb.toString())//去除小数点后多余的0
            result = myformat.format(value)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return result
    }

    fun getShowAmount (value: Double?,dotTextSize: Int): SpannableString? {
        //格式化后的金额字符串
        var text = addCommaDots(value)
        if (TextUtils.isEmpty(text)) return null
        if (text.length < 3) return null
        var spanText = text.substring(text.length - 3,text.length)
        return SpannableUtil.getSpannableTextSize(dotTextSize,
            text,spanText)
    }

    fun getShowAmount (text: String,dotTextSize: Int): SpannableString? {
        //格式化后的金额字符串
        if (TextUtils.isEmpty(text)) return null
        if (text.length < 3) return null
        var spanText = text.substring(text.length - 3,text.length)
        return SpannableUtil.getSpannableTextSize(dotTextSize,
            text,spanText)
    }
    fun getShowAmount (text: String,endSpanCount: Int,dotTextSize: Int): SpannableString? {
        //格式化后的金额字符串
        if (TextUtils.isEmpty(text)) return null
        if (text.length < endSpanCount) return null
        var spanText = text.substring(text.length - endSpanCount,text.length)
        return SpannableUtil.getSpannableTextSize(dotTextSize,
            text,spanText)
    }

    fun getShowAmountSpan (text: String,spanText: String,dotTextSize: Int): SpannableString? {
        //格式化后的金额字符串
        if (TextUtils.isEmpty(text)) return null
        return SpannableUtil.getSpannableTextSize(dotTextSize,
            text,spanText)
    }

    fun getShowAmount (prefixStr:String?,text: String,dotTextSize: Int): SpannableString? {
        //格式化后的金额字符串
        if (TextUtils.isEmpty(text)) return null
        if (text.length < 3) return null
        var spanText = text.substring(text.length - 3,text.length)

        var spanStrs = ArrayList<String>()
        spanStrs.add(prefixStr ?: "")
        spanStrs.add(spanText)
        return SpannableUtil.getSpannableTextSize(dotTextSize,
            text,spanStrs)
    }

    fun getEvaluationCount(value: Int): String? {
        try {
            if (value < 1000) return value.toString()

            if (value % 1000.0 > 0) {
                val bigDecimal = BigDecimal((value / 1000.0).toString())
                val result: Double = bigDecimal.setScale(1, BigDecimal.ROUND_HALF_UP).toDouble()
                return "${result}k"
            }

            return "${value / 1000}K"
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun getViewCount(value: Int): String? {
        try {
            if (value < 10000) return value.toString()

            if (value % 10000.0 > 0) {
                val bigDecimal = BigDecimal((value / 10000.0).toString())
                val result: Double = bigDecimal.setScale(1, BigDecimal.ROUND_HALF_UP).toDouble()
                return "${result}w"
            }

            return "${value / 10000}K"
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun isNumber(userId: String?): Boolean {
        return try {
            val pattern = Pattern.compile("\\d+");
            pattern.matcher(userId).matches()
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

}