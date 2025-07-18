package com.victor.lib.common.util

import android.content.Context
import android.graphics.Color
import android.text.Editable
import android.text.Html
import android.text.Spanned
import android.text.TextUtils
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import org.victor.http.lib.util.JsonUtils
import org.xml.sax.XMLReader
import java.lang.reflect.Field
import java.util.Locale


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: HtmlTagHandler
 * Author: Victor
 * Date: 2024/06/13 18:34
 * Description: 
 * -----------------------------------------------------------------
 */

class HtmlTagHandler : Html.TagHandler {
    private val TAG = "HtmlTagHandler"
    private var tag: String? = null
    private var context: Context? = null
    private var attributes = HashMap<String, String>()
    private var startIndex = 0

    constructor(tag: String?, context: Context?) {
        this.tag = tag
        this.context = context
    }

    override fun handleTag(opening: Boolean, tag: String?, output: Editable, xmlReader: XMLReader) {
        Loger.e(TAG,"handleTag-tag = $tag")
        Loger.e(TAG,"handleTag-output = $output")
        if ("p".equals(tag,ignoreCase = true)) {
            if (!opening) {
                output.append("\n");
            }
        }
        if (this.tag.equals(tag,ignoreCase = true)) {
            processAttributes(xmlReader)
            if (opening) {
                startHandleTag(output)
            } else {
                endHandleTag(output)
                attributes.clear()
            }
        }
    }

    private fun processAttributes(xmlReader: XMLReader) {
        try {
            val elementField: Field = xmlReader.javaClass.getDeclaredField("theNewElement")
            elementField.isAccessible = true
            val element: Any = elementField.get(xmlReader)!!
            val attsField: Field = element.javaClass.getDeclaredField("theAtts")
            attsField.isAccessible = true
            val atts: Any = attsField.get(element)!!
            val dataField: Field = atts.javaClass.getDeclaredField("data")
            dataField.isAccessible = true
            val data = dataField.get(atts) as Array<String>
            val lengthField: Field = atts.javaClass.getDeclaredField("length")
            lengthField.isAccessible = true
            val attsLength: Any = lengthField.get(atts)
            val len = if (attsLength == null) 0 else (attsLength as Int)
            for (i in 0 until len) {
                assert(data != null)
                attributes[data[i * 5 + 1]] = data[i * 5 + 4]
            }
        } catch (ignored: Exception) {
        }
    }

    private fun startHandleTag(output: Editable) {
        startIndex = output.length
    }

    private fun endHandleTag(output: Editable) {
        val stopIndex = output.length
        val style = attributes["style"] ?: ""
        if (!TextUtils.isEmpty(style)) {
            assert(style != null)
            analysisStyle(startIndex, stopIndex, output, style)
        }
    }

    /**
     * 解析style属性
     *
     * @param startIndex startIndex
     * @param stopIndex stopIndex
     * @param editable editable
     * @param style style
     */
    private fun analysisStyle(startIndex: Int, stopIndex: Int, editable: Editable, style: String) {
        Loger.e(TAG,"analysisStyle-style = $style")
        val attrArray = style.split(";".toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray()
        val attrMap: MutableMap<String, String> = HashMap()
        for (attr in attrArray) {
            val keyValueArray = attr.split(":".toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray()
            if (keyValueArray.size == 2) {
                // 去除前后空格
                attrMap[keyValueArray[0].trim { it <= ' ' }] = keyValueArray[1].trim { it <= ' ' }
            }
        }
        Loger.e(TAG,"analysisStyle-attrMap = ${JsonUtils.toJSONString(attrMap)}")
        var color = attrMap["color"]
        if (!TextUtils.isEmpty(color)) {
            assert(color != null)
            if (color!!.startsWith("rgb")) {
                color = color.replace("rgb(", "")
                color = color.replace(")", "")
                val rgbs = color.split(", ".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray()
                color = toHex(rgbs[0].toInt(), rgbs[1].toInt(), rgbs[2].toInt())
            }
            try {
                editable.setSpan(
                    ForegroundColorSpan(Color.parseColor(color)),
                    startIndex,
                    stopIndex,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
        var fontSize = attrMap["font-size"]
        if (!TextUtils.isEmpty(fontSize)) {
            assert(fontSize != null)
            fontSize =
                fontSize!!.split("px".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]
            try {
                val size = sp2px(context!!, fontSize.toInt().toFloat())
                editable.setSpan(
                    AbsoluteSizeSpan(size),
                    startIndex,
                    stopIndex,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
    }


    private fun toHex(r: Int, g: Int, b: Int): String {
        return ("#" + toBrowserHexValue(r) + toBrowserHexValue(g)
                + toBrowserHexValue(b))
    }

    private fun toBrowserHexValue(number: Int): String {
        val builder = StringBuilder(
            Integer.toHexString(number and 0xff)
        )
        while (builder.length < 2) {
            builder.append("0")
        }
        return builder.toString().uppercase(Locale.getDefault())
    }

    private fun sp2px(context: Context, spValue: Float): Int {
        val fontScale = context.resources.displayMetrics.scaledDensity
        return (spValue * fontScale + 0.5f).toInt()
    }
}