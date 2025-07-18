package com.victor.lib.common.util

import android.graphics.Rect
import android.view.View
import android.view.ViewTreeObserver
import androidx.appcompat.app.AppCompatActivity
import com.victor.lib.common.interfaces.OnKeyboardStateListener

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: KeyboardStateModule
 * Author: Victor
 * Date: 2022/6/1 12:20
 * Description: 系统软键盘弹出、收起监听模块
 * -----------------------------------------------------------------
 */

class KeyboardStateModule: ViewTreeObserver.OnGlobalLayoutListener {
    private var rootView: View? = null
    private var rootViewVisibleHeight: Int = 0  //纪录根视图的显示高度
    var mOnKeyboardStateListener: OnKeyboardStateListener? = null

    private object Holder {val instance = KeyboardStateModule() }

    companion object {
        val  instance: KeyboardStateModule by lazy { Holder.instance }
    }

    fun addKeyboardStateObserver(activity: AppCompatActivity?,listener: OnKeyboardStateListener?) {
        mOnKeyboardStateListener = listener
        //获取activity的根视图
        rootView = activity?.window?.decorView
        //监听视图树中全局布局发生改变或者视图树中的某个视图的可视状态发生改变
        rootView?.viewTreeObserver?.addOnGlobalLayoutListener(this)
    }

    fun removeKeyboardStateObserver() {
        //监听视图树中全局布局发生改变或者视图树中的某个视图的可视状态发生改变
        rootView?.viewTreeObserver?.removeOnGlobalLayoutListener(this)
    }

    override fun onGlobalLayout() {
        //获取当前根视图在屏幕上显示的大小
        var r = Rect()
        rootView?.getWindowVisibleDisplayFrame(r)
        var visibleHeight = r.height()
        if (rootViewVisibleHeight == 0) {
            rootViewVisibleHeight = visibleHeight
            return;
        }
        //根视图显示高度没有变化，可以看作软键盘显示／隐藏状态没有改变
        if (rootViewVisibleHeight == visibleHeight) {
            return
        }
        //根视图显示高度变小超过200，可以看作软键盘显示了
        if (rootViewVisibleHeight - visibleHeight > 200) {
            mOnKeyboardStateListener?.OnKeyBoardShow(rootViewVisibleHeight - visibleHeight)
            rootViewVisibleHeight = visibleHeight
            return
        }
        //根视图显示高度变大超过200，可以看作软键盘隐藏了
        if (visibleHeight - rootViewVisibleHeight > 200) {
            mOnKeyboardStateListener?.OnKeyBoardHide(visibleHeight - rootViewVisibleHeight)
            rootViewVisibleHeight = visibleHeight
            return
        }
    }
}