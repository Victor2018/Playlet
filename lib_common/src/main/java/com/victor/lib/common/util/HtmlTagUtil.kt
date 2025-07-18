package com.victor.lib.common.util

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: HtmlTagUtil
 * Author: Victor
 * Date: 2024/06/13 18:41
 * Description: 
 * -----------------------------------------------------------------
 */

object HtmlTagUtil {
    private const val TAG = "HtmlTagUtil"
    const val NEW_SPAN = "newSpan"
    private const val OLD_SPAN = "span"

    fun replaceSpan(content: String): String {
        return content.replace(OLD_SPAN, NEW_SPAN)
    }

    fun getLineHeight(htmlContent: String): Float {
        var lineHeight: Float? = 0f
        try {
            // 正则表达式匹配 style="line-height: 任意数值;"
            val regex = """line-height:\s*([^;]+);""".toRegex()

            // 查找匹配项
            val matchResult = regex.find(htmlContent)

            // 如果找到匹配项，则提取值
            lineHeight = matchResult?.groups?.get(1)?.value?.toFloat()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        Loger.e(TAG,"lineHeight = $lineHeight")
        return lineHeight ?: 0f
    }

}