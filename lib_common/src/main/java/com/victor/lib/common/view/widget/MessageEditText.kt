package com.victor.lib.common.view.widget

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.View
import android.view.View.OnKeyListener
import androidx.appcompat.widget.AppCompatEditText
import com.victor.lib.common.util.Loger

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: MessageEditText
 * Author: Victor
 * Date: 2022/5/30 15:09
 * Description: 
 * -----------------------------------------------------------------
 */

class MessageEditText : AppCompatEditText,OnKeyListener {
    val TAG = "MessageEditText"
    /**
     * 键盘监听接口
     */
    var mOnKeyBoardHideListener: OnKeyBoardHideListener? = null

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr) {
                setOnKeyListener(this)
    }

    override fun onKeyPreIme(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() === 1) {
            super.onKeyPreIme(keyCode, event)
            mOnKeyBoardHideListener?.onKeyHide(keyCode, event)
            return false
        }
        return super.onKeyPreIme(keyCode, event)
    }

    interface OnKeyBoardHideListener {
        fun onKeyHide(keyCode: Int, event: KeyEvent?)
    }

    override fun onKey(p0: View?, keyCode: Int, event: KeyEvent?): Boolean {
        Loger.e(TAG,"onKey-keyCode = $keyCode")
        return false
    }
}