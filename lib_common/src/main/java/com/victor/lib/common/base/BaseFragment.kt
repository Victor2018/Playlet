package com.victor.lib.common.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.alibaba.android.arouter.launcher.ARouter
import com.victor.lib.common.util.Loger

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: BaseFragment
 * Author: Victor
 * Date: 2022/3/1 18:28
 * Description: 
 * -----------------------------------------------------------------
 */

abstract class BaseFragment<VB : ViewBinding>( private val bindingInflater: (LayoutInflater) -> VB) : Fragment(),View.OnTouchListener {
    companion object {
        val ID_KEY = "ID_KEY"
        val TAG = javaClass.simpleName
    }

    private var _binding: VB? = null
    public val binding get() = _binding!!

    /**
     * 是否初始化过布局
     */
    protected var isViewInitiated = false

    //Fragment对用户可见的标记
    var isVisibleToUser: Boolean = false
    //是否加载过数据
    var isDataInitiated = false


    abstract fun handleBackEvent(): Boolean
    abstract fun freshFragData()

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        Loger.e(TAG,"setUserVisibleHint()......isVisibleToUser = ${isVisibleToUser}")
        //isVisibleToUser这个boolean值表示:该Fragment的UI 用户是否可见
        if (isVisibleToUser) {
            this.isVisibleToUser = true
            lazyLoad()
        } else {
            this.isVisibleToUser = false
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        Log.e(TAG,"onHiddenChanged()......hidden = ${hidden}")
        if (!hidden) {
            this.isVisibleToUser = true
            lazyLoad()
        } else {
            this.isVisibleToUser = false
        }
    }

    fun lazyLoad() {
        //这里进行双重标记判断,是因为setUserVisibleHint会多次回调,并且会在onCreateView执行前回调,必须确保onCreateView加载完毕且页面可见,才加载数据
        if (isVisibleToUser && isViewInitiated && !isDataInitiated) {
            freshFragData()
            //数据加载完毕,恢复标记,防止重复加载
            isDataInitiated = true
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = bindingInflater(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ARouter.getInstance().inject(this)
        isViewInitiated = true
        view.setOnTouchListener(this)
    }

    /**
     * 已弃用
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        isVisibleToUser = true
        if (!isHidden) {
            lazyLoad()
        }
    }

    override fun onPause() {
        super.onPause()
        isVisibleToUser = false
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        return true//处理多个fragment叠加点击事件穿透问题
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        isViewInitiated = false
        isVisibleToUser = false
        isDataInitiated = false
    }


    open fun switchFragment(toFragment: Fragment,containerViewId: Int) {
        val ft = childFragmentManager.beginTransaction()
        ft.setCustomAnimations(
            com.victor.lib.common.R.anim.anim_fragment_enter,
            com.victor.lib.common.R.anim.anim_fragment_exit,
            com.victor.lib.common.R.anim.anim_fragment_enter,
            com.victor.lib.common.R.anim.anim_fragment_exit)

        ft.add(containerViewId, toFragment)
        ft.addToBackStack(toFragment?.javaClass?.name)

        ft.commitAllowingStateLoss()

        var backStackEntryCount = childFragmentManager.backStackEntryCount
        Loger.e(TAG,"switchFragment-backStackEntryCount = $backStackEntryCount")
    }

    open fun popBackStack () {
        try {
            var backStackEntryCount = childFragmentManager.backStackEntryCount
            Loger.e(TAG,"popBackStack-backStackEntryCount = $backStackEntryCount")
            if (backStackEntryCount > 0) {
                childFragmentManager.popBackStack()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}