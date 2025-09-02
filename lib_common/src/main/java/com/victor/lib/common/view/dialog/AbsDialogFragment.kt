package com.victor.lib.common.view.dialog

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.viewbinding.ViewBinding
import com.victor.lib.common.R


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: AbsDialogFragment
 * Author: Victor
 * Date: 2022/4/1 10:02
 * Description: DialogFragment基类
 * -----------------------------------------------------------------
 */

abstract class AbsDialogFragment<VB : ViewBinding>(private val bindingInflater: (LayoutInflater) -> VB) : DialogFragment() {
    val TAG = "AbsDialogFragment"

    private var _binding: VB? = null
    public val binding get() = _binding!!

    var mRootView: View? = null

    var mContext: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity
        setStyle(STYLE_NORMAL, R.style.BaseNoTitleDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater(layoutInflater)
        mRootView = binding.root
        initView(mRootView)
        return mRootView
    }

    open fun initView(rootView: View?) {}

    /**
     * 用于处理窗口的属性
     *
     * @param window
     */
    open fun handleWindow(window: Window?) {}

    /**
     * 处理默认的宽和高
     * 和动画效果
     *
     * @param wl
     */
    open fun handleLayoutParams(wl: WindowManager.LayoutParams?) {}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val window = dialog?.window
        //处理默认窗口属性
        handleWindow(window)
        //设置属性信息宽高或者动画
        val wlp = window?.attributes
        handleLayoutParams(wlp)
        window?.attributes = wlp

        //禁止app录屏和截屏
//        window?.addFlags(WindowManager.LayoutParams.FLAG_SECURE)
    }

    override fun show(manager: FragmentManager, tag: String?) {
        //避免,重复加载报错
        manager.executePendingTransactions()
        //提交
        if (manager.findFragmentByTag(tag) == null) {
            val ft: FragmentTransaction = manager.beginTransaction()
            ft.add(this, tag)
            ft.commitAllowingStateLoss()
        }
    }

    override fun dismiss() {
        super.dismissAllowingStateLoss()
    }

    override fun onDestroyView() {
        if (view is ViewGroup) {
            (view as ViewGroup?)?.removeAllViews()
        }
        super.onDestroyView()
        _binding = null
    }

}