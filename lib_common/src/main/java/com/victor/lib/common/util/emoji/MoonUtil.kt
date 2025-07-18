package com.victor.lib.common.util.emoji

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.text.*
import android.text.style.ClickableSpan
import android.text.style.ImageSpan
import android.view.View
import android.widget.EditText
import android.widget.TextView
import java.util.Locale
import java.util.regex.Matcher
import java.util.regex.Pattern
import kotlin.collections.ArrayList


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: MoonUtil
 * Author: Victor
 * Date: 2021/4/27 12:23
 * Description: 
 * -----------------------------------------------------------------
 */
object MoonUtil {
    const val DEF_SCALE = 0.6f
    const val SMALL_SCALE = 0.45f

    /**
     * 具体类型的view设置内容
     *
     * @param textView
     * @param mSpannableString
     */
    private fun viewSetText(
        textView: View?,
        mSpannableString: SpannableString?
    ) {
        if (textView is TextView) {
            textView.text = mSpannableString
        } else if (textView is EditText) {
            textView.setText(mSpannableString)
        }
    }

    fun replaceEmoticons(
        context: Context,
        value: String?,
        scale: Float,
        align: Int
    ): SpannableString {
        var value = value
        if (TextUtils.isEmpty(value)) {
            value = ""
        }
        val mSpannableString = SpannableString(value)
        val matcher: Matcher = EmojiManager.getPattern().matcher(value)
        while (matcher.find()) {
            val start = matcher.start()
            val end = matcher.end()
            val emot = value?.substring(start, end)
            val d = getEmotDrawable(context, emot, scale)
            if (d != null) {
                val span = ImageSpan(d, align)
                mSpannableString.setSpan(
                    span,
                    start,
                    end,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }
        return mSpannableString
    }
    fun replaceEmoticons(
        context: Context,
        mSpannableString: SpannableStringBuilder,
        scale: Float,
        align: Int
    ): SpannableStringBuilder {
        val value = mSpannableString.toString()
        val matcher: Matcher = EmojiManager.getPattern().matcher(value)
        while (matcher.find()) {
            val start = matcher.start()
            val end = matcher.end()
            val emot = value?.substring(start, end)
            val d = getEmotDrawable(context, emot, scale)
            if (d != null) {
                val span = ImageSpan(d, align)
                mSpannableString.setSpan(
                    span,
                    start,
                    end,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }
        return mSpannableString
    }

    fun getReplaceEmoticonsSpan (context: Context, value: String?, align: Int): List<ImageSpan> {

        var imageSpans = ArrayList<ImageSpan>()

        var value = value
        if (TextUtils.isEmpty(value)) {
            value = ""
        }

        val matcher: Matcher = EmojiManager.getPattern().matcher(value)
        while (matcher.find()) {
            val start = matcher.start()
            val end = matcher.end()
            val emot = value?.substring(start, end)
            val d = getEmotDrawable(context, emot, DEF_SCALE)
            if (d != null) {
                val span = ImageSpan(d, align)
                imageSpans.add(span)
               /* mSpannableString.setSpan(
                    span,
                    start,
                    end,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )*/
            }
        }
        return imageSpans
    }

    private val mATagPattern =
        Pattern.compile("<a.*?>.*?</a>")

    fun makeSpannableStringTags(
        context: Context,
        value: String,
        scale: Float,
        align: Int
    ): SpannableString? {
        return makeSpannableStringTags(context, value, DEF_SCALE, align, true)
    }

    fun makeSpannableStringTags(
        context: Context,
        value: String,
        scale: Float,
        align: Int,
        bTagClickable: Boolean
    ): SpannableString? {
        var value = value
        val tagSpans = ArrayList<ATagSpan>()
        if (TextUtils.isEmpty(value)) {
            value = ""
        }
        //a标签需要替换原始文本,放在moonutil类中
        var aTagMatcher = mATagPattern.matcher(value)
        var start = 0
        var end = 0
        while (aTagMatcher.find()) {
            start = aTagMatcher.start()
            end = aTagMatcher.end()
            val atagString = value.substring(start, end)
            val tagSpan = getTagSpan(atagString)
            value = value.substring(0, start) + tagSpan.tag + value.substring(end)
            tagSpan.setRange(start, start + tagSpan.tag!!.length)
            tagSpans.add(tagSpan)
            aTagMatcher = mATagPattern.matcher(value)
        }
        val mSpannableString = SpannableString(value)
        val matcher: Matcher = EmojiManager.getPattern().matcher(value)
        while (matcher.find()) {
            start = matcher.start()
            end = matcher.end()
            val emot = value.substring(start, end)
            val d = getEmotDrawable(context, emot, scale)
            if (d != null) {
                val span = ImageSpan(d, align)
                mSpannableString.setSpan(
                    span,
                    start,
                    end,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }
        if (bTagClickable) {
            for (tagSpan in tagSpans) {
                mSpannableString.setSpan(
                    tagSpan,
                    tagSpan.start,
                    tagSpan.end,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }
        return mSpannableString
    }

    /**
     * 识别表情
     */
    fun identifyFaceExpression(
        context: Context,
        textView: View?, value: String?, align: Int
    ) {
        identifyFaceExpression(context, textView, value, align, DEF_SCALE)
    }

    /**
     * 识别表情和标签（如：只需显示a标签对应的文本）
     */
    fun identifyFaceExpressionAndATags(
        context: Context,
        textView: View?, value: String, align: Int
    ) {
        val mSpannableString =
            makeSpannableStringTags(context, value, DEF_SCALE, align)
        viewSetText(textView, mSpannableString)
    }

    /**
     * 识别表情，可设置缩放大小
     */
    fun identifyFaceExpression(
        context: Context,
        textView: View?, value: String?, align: Int, scale: Float
    ) {
        val mSpannableString =
            replaceEmoticons(context, value, scale, align)
        viewSetText(textView, mSpannableString)
    }

    /**
     * 识别表情和标签（如：只需显示a标签对应的文本），可设置缩放大小
     */
    fun identifyFaceExpressionAndTags(
        context: Context,
        textView: View?, value: String, align: Int, scale: Float
    ) {
        val mSpannableString =
            makeSpannableStringTags(context, value, scale, align, false)
        viewSetText(textView, mSpannableString)
    }

    /**
     * EditText用来转换表情文字的方法，如果没有使用EmoticonPickerView的attachEditText方法，则需要开发人员手动调用方法来又识别EditText中的表情
     */
    fun replaceEmoticons(
        context: Context,
        editable: Editable,
        start: Int,
        count: Int
    ) {
        if (count <= 0 || editable.length < start + count) return
        val s = editable.subSequence(start, start + count)
        val matcher: Matcher = EmojiManager.getPattern().matcher(s)
        while (matcher.find()) {
            val from = start + matcher.start()
            val to = start + matcher.end()
            val emot = editable.subSequence(from, to).toString()
            val d = getEmotDrawable(context, emot, SMALL_SCALE)
            if (d != null) {
                val span =
                    ImageSpan(d, ImageSpan.ALIGN_BOTTOM)
                editable.setSpan(span, from, to, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
        }
    }

    private fun getEmotDrawable(
        context: Context,
        text: String?,
        scale: Float
    ): Drawable? {
        val drawable: Drawable? = EmojiManager.getDrawable(context, text)

        // scale
        if (drawable != null) {
            val width = (drawable.intrinsicWidth * scale).toInt()
            val height = (drawable.intrinsicHeight * scale).toInt()
            drawable.setBounds(0, 0, width, height)
        }
        return drawable
    }

    private fun getTagSpan(text: String): ATagSpan {
        var href: String? = null
        var tag: String? = null
        if (text.lowercase(Locale.getDefault()).contains("href")) {
            val start = text.indexOf("\"")
            val end = text.indexOf("\"", start + 1)
            if (end > start) href = text.substring(start + 1, end)
        }
        val start = text.indexOf(">")
        val end = text.indexOf("<", start)
        if (end > start) tag = text.substring(start + 1, end)
        return ATagSpan(tag, href)
    }

    private class ATagSpan internal constructor(val tag: String?, private var mUrl: String?) :
        ClickableSpan() {
        var start = 0
        var end = 0

        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds)
            ds.isUnderlineText = true
        }

        fun setRange(start: Int, end: Int) {
            this.start = start
            this.end = end
        }

        override fun onClick(widget: View) {
            try {
                if (TextUtils.isEmpty(mUrl)) return
                val uri = Uri.parse(mUrl)
                val scheme = uri.scheme
                if (TextUtils.isEmpty(scheme)) {
                    mUrl = "http://$mUrl"
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }

    /**
     * 获取贴图地址
     * @param stickerCategoryName
     * @param stickerName
     * @return
     */
    fun getStickerPath(
        stickerCategoryName: String?,
        stickerName: String?
    ): String? {
        return String.format(
            "file:///android_asset/sticker/%s/%s",
            stickerCategoryName,
            stickerName
        )
    }
}