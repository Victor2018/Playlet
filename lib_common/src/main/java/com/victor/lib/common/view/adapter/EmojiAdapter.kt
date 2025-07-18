package com.victor.lib.common.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.victor.lib.common.R
import com.victor.lib.common.util.emoji.EmojiManager
import com.victor.lib.common.view.widget.EmoticonView


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: EmojiAdapter
 * Author: Victor
 * Date: 2021/4/27 14:08
 * Description: 
 * -----------------------------------------------------------------
 */
class EmojiAdapter(var context: Context?, var startIndex: Int,var showBackButton: Boolean): BaseAdapter() {
    override fun getCount(): Int {
        var count = EmojiManager.getDisplayCount() - startIndex + 1
        count = Math.min(count, EmoticonView.EMOJI_PER_PAGE + 1)
        if (!showBackButton) {
            return count - 1
        }
        return count
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return (startIndex + position).toLong()
    }

    @SuppressLint("ViewHolder")
    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup?
    ): View? {

        var mView = View.inflate(context, R.layout.nim_emoji_item, null)
        val emojiThumb = mView.findViewById<View>(R.id.imgEmoji) as ImageView

        val count = EmojiManager.getDisplayCount()
        val index = startIndex + position
        if (position == EmoticonView.EMOJI_PER_PAGE || index == count) {
            emojiThumb.setBackgroundResource(R.mipmap.nim_emoji_del)
        } else if (index < count) {
            emojiThumb.setBackgroundDrawable(EmojiManager.getDisplayDrawable(context!!, index))
        }
        return mView
    }

}