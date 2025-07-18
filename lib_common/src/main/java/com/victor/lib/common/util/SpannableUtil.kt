package com.victor.lib.common.util

import android.content.Context
import android.graphics.Typeface
import android.text.*
import android.text.style.*
import android.view.View
import android.widget.TextView
import com.victor.lib.common.R
import com.victor.lib.common.util.ViewUtils.bitmap
import com.victor.lib.common.app.App
import com.victor.lib.common.util.emoji.MoonUtil
import com.victor.lib.common.view.widget.QCenterAlignImageSpan
import com.victor.lib.common.view.widget.URLSpanNoUnderline


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: SpannableUtil
 * Author: Victor
 * Date: 2022/3/1 18:28
 * Description: 
 * -----------------------------------------------------------------
 */

object SpannableUtil {

    val TAG = "SpannableUtil"

    /**
     * 设置不同颜色
     */
    fun setSpannableColor(textView: TextView?,color: Int,text: String?,spanText: String?) {
        if (TextUtils.isEmpty(text)) return
        if (TextUtils.isEmpty(spanText)) {
            textView?.text = text
            return
        }
        try {
            val mSearchCount = getWordCount(spanText)
            val spannableString = SpannableString(text)
            var index = 0
            while (index != -1) {
                index = text?.indexOf(spanText ?: "", index) ?: 0
                if (index == -1) break
                spannableString.setSpan(
                    ForegroundColorSpan(color),
                    index,
                    (index + mSearchCount).also { index = it },
                    Spanned.SPAN_INCLUSIVE_EXCLUSIVE
                )
            }
            textView?.text = spannableString
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getSpannableTextSize(textSize: Int,text: String?,spanText: String?): SpannableString? {
        if (TextUtils.isEmpty(text)) return null
        val spannableString = SpannableString(text)
        try {
            val mSearchCount = getWordCount(spanText)
            var index = 0
            while (index != -1) {
                index = text?.indexOf(spanText ?: "", index) ?: 0
                if (index == -1) break
                spannableString.setSpan(
                    AbsoluteSizeSpan(textSize),
                    index,
                    (index + mSearchCount).also { index = it },
                    Spanned.SPAN_INCLUSIVE_EXCLUSIVE
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return spannableString
    }

    fun getBoldSizeSpannable(text: String?,spanTexts: List<String>?,textSize: Int): SpannableString? {
        if (spanTexts == null) return null
        if (spanTexts.isEmpty()) return null

        try {

        } catch (e: Exception) {
            e.printStackTrace()
        }

        val spannableString = SpannableString(text)

        try {
            spanTexts.forEach {spanText ->
                val mSearchCount = getWordCount(spanText)

                var index = 0
                while (index != -1) {
                    index = text?.indexOf(spanText, index) ?: 0
                    if (index == -1) break
                    spannableString.setSpan(
                        AbsoluteSizeSpan(textSize),
                        index,
                        (index + mSearchCount),
                        Spanned.SPAN_INCLUSIVE_EXCLUSIVE
                    )
                    spannableString.setSpan(
                        StyleSpan(Typeface.BOLD),
                        index,
                        (index + mSearchCount),
                        Spanned.SPAN_INCLUSIVE_EXCLUSIVE
                    )
                    index += mSearchCount
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return spannableString
    }

    fun getBoldColorSizeSpannable(text: String?,spanTexts: List<String>?,textSize: Int,color: Int): SpannableString? {
        if (spanTexts == null) return null
        if (spanTexts.size == 0) return null

        val spannableString = SpannableString(text)

        try {
            spanTexts.forEach {spanText ->
                val mSearchCount = getWordCount(spanText)

                var index = 0
                while (index != -1) {
                    index = text?.indexOf(spanText, index) ?: 0
                    if (index == -1) break
                    spannableString.setSpan(
                        ForegroundColorSpan(color),
                        index,
                        (index + mSearchCount),
                        Spanned.SPAN_INCLUSIVE_EXCLUSIVE
                    )
                    spannableString.setSpan(
                        AbsoluteSizeSpan(textSize),
                        index,
                        (index + mSearchCount),
                        Spanned.SPAN_INCLUSIVE_EXCLUSIVE
                    )
                    spannableString.setSpan(
                        StyleSpan(Typeface.BOLD),
                        index,
                        (index + mSearchCount),
                        Spanned.SPAN_INCLUSIVE_EXCLUSIVE
                    )
                    index += mSearchCount
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return spannableString
    }

    fun getSpannableBoldText(text: String?,spanText: String?): SpannableString? {
        if (TextUtils.isEmpty(text)) return null
        val spannableString = SpannableString(text)
        try {
            val mSearchCount = getWordCount(spanText)
            var index = 0
            while (index != -1) {
                index = text?.indexOf(spanText ?: "", index) ?: 0
                if (index == -1) break
                spannableString.setSpan(
                    StyleSpan(Typeface.BOLD),
                    index,
                    (index + mSearchCount).also { index = it },
                    Spanned.SPAN_INCLUSIVE_EXCLUSIVE
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return spannableString
    }

    fun getSpannableBoldText(text: SpannableString?,spanText: String?): SpannableString? {
        if (text == null) return null
        if (TextUtils.isEmpty(text)) return null
        val spannableString = SpannableString(text)
        try {
            val mSearchCount = getWordCount(spanText)
            var index = 0
            while (index != -1) {
                index = text?.indexOf(spanText ?: "", index) ?: 0
                if (index == -1) break
                spannableString.setSpan(
                    StyleSpan(Typeface.BOLD),
                    index,
                    (index + mSearchCount).also { index = it },
                    Spanned.SPAN_INCLUSIVE_EXCLUSIVE
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return spannableString
    }

    fun getSpannableColorText(text: SpannableString?,spanText: String?,color: Int): SpannableString? {
        if (text == null) return null
        if (TextUtils.isEmpty(text)) return null
        val spannableString = SpannableString(text)
        try {
            val mSearchCount = getWordCount(spanText)
            var index = 0
            while (index != -1) {
                index = text?.indexOf(spanText ?: "", index) ?: 0
                if (index == -1) break
                spannableString.setSpan(
                    ForegroundColorSpan(color),
                    index,
                    (index + mSearchCount).also { index = it },
                    Spanned.SPAN_INCLUSIVE_EXCLUSIVE
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return spannableString
    }

    fun getSpannableTextSize(textSize: Int,text: String?,spanTexts: List<String>?): SpannableString? {
        if (TextUtils.isEmpty(text)) return null

        val spannableString = SpannableString(text)

        try {
            spanTexts?.forEach {
                var spanText = it
                val redCount = getWordCount(spanText)

                var index = 0
                while (index != -1) {
                    index = text?.indexOf(spanText, index) ?: 0
                    if (index == -1) break
                    spannableString.setSpan(
                        AbsoluteSizeSpan(textSize),
                        index,
                        (index + redCount).also { index = it },
                        Spanned.SPAN_INCLUSIVE_EXCLUSIVE
                    )
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return spannableString
    }

    fun setSpannableColor(textView: TextView?,color: Int,text: String?,spanTexts: List<String>?) {
        val spannableString = SpannableString(text)

        try {
            var size = spanTexts?.size ?: 0
            for (i in 0 until size) {
                var spanText = spanTexts?.get(i) ?: ""
                val redCount = getWordCount(spanText)

                var index = 0
                while (index != -1) {
                    index = text?.indexOf(spanText, index) ?: 0
                    if (index == -1) break
                    spannableString.setSpan(
                        ForegroundColorSpan(color),
                        index,
                        (index + redCount).also { index = it },
                        Spanned.SPAN_INCLUSIVE_EXCLUSIVE
                    )
                }
            }

            textView?.text = spannableString
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 添加下划线
     */
    fun setSpannableUnderline(textView: TextView?,text: String?,spanText: String?) {
        if (textView == null) return
        if (TextUtils.isEmpty(text)) return
        if (TextUtils.isEmpty(spanText)) return

        try {
            val mUnderlineCount = getWordCount(spanText)
            val spannableString = SpannableString(text)

            var underlineIndex = 0
            while (underlineIndex != -1) {
                underlineIndex = text?.indexOf(spanText ?: "", underlineIndex) ?: 0
                if (underlineIndex == -1) break
                spannableString.setSpan(
                    UnderlineSpan(),
                    underlineIndex,
                    (underlineIndex + mUnderlineCount).also { underlineIndex = it },
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }

            textView.text = spannableString
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 添加删除线
     */
    fun setSpannableDeleteline(textView: TextView?,text: String?,spanText: String?) {
        if (textView == null) return
        if (TextUtils.isEmpty(text)) return
        if (TextUtils.isEmpty(spanText)) return

        try {
            val mUnderlineCount = getWordCount(spanText)
            val spannableString = SpannableString(text)

            var underlineIndex = 0
            while (underlineIndex != -1) {
                underlineIndex = text?.indexOf(spanText ?: "", underlineIndex) ?: 0
                if (underlineIndex == -1) break
                spannableString.setSpan(
                    StrikethroughSpan(),
                    underlineIndex,
                    (underlineIndex + mUnderlineCount).also { underlineIndex = it },
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }

            textView.text = spannableString
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun stripUnderlines(textView: TextView?) {
        try {
            if (null != textView && textView.text is Spannable) {
                val s: Spannable = textView.text as Spannable
                val spans: Array<URLSpan> = s.getSpans(0, s.length, URLSpan::class.java)
                for (span in spans) {
                    val start: Int = s.getSpanStart(span)
                    val end: Int = s.getSpanEnd(span)
                    s.removeSpan(span)
                    var mSpan = URLSpanNoUnderline(span.url)
                    s.setSpan(mSpan, start, end, 0)
                }
                textView.text = s
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getWordCount(s: String?): Int {
        try {
            var s = s
            s = s?.replace("[\\u4e00-\\u9fa5]".toRegex(), "*")
            return s?.length ?: 0
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return 0
    }

    fun getImageSpanSpannable(
        views: List<View>, nickName: String?, message: String?,
        nameColor: Int, messageColor: Int,
    ): SpannableStringBuilder {
        val totalSpannableString = SpannableStringBuilder()
        try {
            var currentIndex = 0
            //拼接头部的几个tagImageSpan
            views.forEach {
                val imageSpan = getCenterImageSpanByView(it)
                currentIndex += imageSpanAppend(imageSpan, currentIndex, totalSpannableString)
            }
            //拼接昵称
            currentIndex += nickNameSpanAppend(nickName,nameColor, currentIndex, totalSpannableString)

            //拼接文本文本消息
            currentIndex += messageSpanAppend(message,messageColor, currentIndex, totalSpannableString)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return totalSpannableString
    }

    fun getImageSpanSpannable (views: List<View>,contentName: String?): SpannableStringBuilder {
        val totalSpannableString = SpannableStringBuilder()
        try {
            var currentIndex = 0
            //拼接头部的几个tagImageSpan
            views.forEach {
                val imageSpan = getCenterImageSpanByView(it)
                currentIndex += imageSpanAppend(imageSpan, currentIndex, totalSpannableString)
            }
            //拼接文本文本消息
            currentIndex += messageSpanAppend(contentName,
                ResUtils.getColorRes(R.color.color_333333), currentIndex, totalSpannableString)

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return totalSpannableString
    }

    fun getImageSpanSpannable (views: List<View>,contentName: String?,textColorRes: Int): SpannableStringBuilder {
        val totalSpannableString = SpannableStringBuilder()
        try {
            var currentIndex = 0
            //拼接头部的几个tagImageSpan
            views.forEach {
                val imageSpan = getCenterImageSpanByView(it)
                currentIndex += imageSpanAppend(imageSpan, currentIndex, totalSpannableString)
            }
            //拼接文本文本消息
            currentIndex += messageSpanAppend(contentName,
                ResUtils.getColorRes(textColorRes), currentIndex, totalSpannableString)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return totalSpannableString
    }

    fun getImageSpanSpannable(
        context: Context, layoutIds: List<Int>, nickName: String?,
        message: String?, nameColor: Int, messageColor: Int,
    ): SpannableStringBuilder {
        val totalSpannableString = SpannableStringBuilder()
        try {
            var currentIndex = 0
            //拼接头部的几个tagImageSpan
            layoutIds.forEach {
                val imageSpan = getImageSpanByLayout(context,it)
                currentIndex += imageSpanAppend(imageSpan, currentIndex, totalSpannableString)
            }
            //拼接昵称
            currentIndex += nickNameSpanAppend(nickName,nameColor, currentIndex, totalSpannableString)

            //拼接文本文本消息
            currentIndex += messageSpanAppend(message,messageColor, currentIndex, totalSpannableString)

            return totalSpannableString
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return totalSpannableString
    }

    fun imageSpanAppend(imageSpan: CharacterStyle?, currentIndex: Int, totalSpannableString: SpannableStringBuilder): Int {
        try {
            imageSpan?.let {
                totalSpannableString.append("|")//设置1个空字符串来占坑
                totalSpannableString.setSpan(imageSpan, currentIndex, currentIndex + 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
                totalSpannableString.append(" ") //这个是拼接好imageSpan后，拼接空格
                return 2 //这个2 = 被替换那个空字符串的长度 + 空格
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return 0
    }

    fun nickNameSpanAppend(
        nickName: String?, nameColor: Int, currentIndex: Int,
        totalSpannableString: SpannableStringBuilder,
    ): Int {
        try {
            totalSpannableString.append(nickName)
            var nameTotalLength = nickName?.length ?: 0
            //是用户写的文字消息
            totalSpannableString.append("：")
            nameTotalLength += 1 //+1是那个冒号

            //用户昵称
            val nameColorSpan = ForegroundColorSpan(nameColor)
            totalSpannableString.setSpan(nameColorSpan, currentIndex, currentIndex + nameTotalLength,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE)
            return nameTotalLength
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return 0
    }

    fun messageSpanAppend (message: String?,messageColor: Int,currentIndex: Int, totalSpannableString: SpannableStringBuilder): Int {

        try {
            //是用户写的文字消息
            var messageTotalLength = message?.length ?: 0

            //用户写的文字消息
            val chatColorSpan = ForegroundColorSpan(messageColor)

            var replaceEmoticonsSpan = MoonUtil.replaceEmoticons(
                App.get(), message,
                MoonUtil.DEF_SCALE,ImageSpan.ALIGN_BOTTOM)
            totalSpannableString.append(replaceEmoticonsSpan)

            totalSpannableString.setSpan(chatColorSpan, currentIndex, currentIndex + messageTotalLength, Spannable.SPAN_INCLUSIVE_INCLUSIVE)

            return messageTotalLength
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return 0
    }

    fun getImageSpanByView (view: View): ImageSpan {
        return QCenterAlignImageSpan(view.context , view.bitmap())
    }
    fun getCenterImageSpanByView (view: View): ImageSpan {
        return CenterImageSpan(view.context , view.bitmap())
    }

    fun getImageSpanByLayout (context: Context,layoutId: Int): ImageSpan {
        var view = ViewUtils.getViewByLayout(context, layoutId)
        return getImageSpanByView(view)
    }
}