package com.victor.lib.common.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.alibaba.android.arouter.launcher.ARouter
import com.victor.lib.common.R
import com.victor.lib.common.util.Loger
import com.victor.lib.common.util.StatusBarUtil

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: BaseActivity
 * Author: Victor
 * Date: 2022/3/1 18:28
 * Description: 
 * -----------------------------------------------------------------
 */

abstract class BaseActivity<VB : ViewBinding>(private val bindingInflater: (LayoutInflater) -> VB): AppCompatActivity() {
    protected var TAG = javaClass.simpleName
    var statusBarTextColorBlack = true

    private var _binding: VB? = null
    public val binding get() = _binding!!

    companion object {
        fun Context.intentStart(clazz: Class<*>) {
            val intent = Intent(this, clazz)
            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ARouter.getInstance().inject(this)

        _binding = bindingInflater(layoutInflater)
        setContentView(binding.root)
        initializeSuper()
    }

    fun initializeSuper () {
        Log.e(TAG,"initializeSuper......")
        //状态栏背景及字体颜色适配
        StatusBarUtil.translucentStatusBar(this, true,statusBarTextColorBlack,false)

        //禁止app录屏和截屏
//        window.addFlags(WindowManager.LayoutParams.FLAG_SECURE)
    }

    override fun startActivity(intent: Intent?) {
        super.startActivity(intent)
        overridePendingTransition(R.anim.anim_activity_enter, R.anim.anim_activity_exit)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.anim_activity_enter_back, R.anim.anim_activity_exit_back)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    open fun switchFragment(toFragment: Fragment, containerViewId: Int) {
        val ft = supportFragmentManager.beginTransaction()
        ft.setCustomAnimations(
            R.anim.anim_fragment_enter,
            R.anim.anim_fragment_exit,
            R.anim.anim_fragment_enter,
            R.anim.anim_fragment_exit)

        ft.add(containerViewId, toFragment)
        ft.addToBackStack(toFragment?.javaClass?.name)

        ft.commitAllowingStateLoss()

        var backStackEntryCount = supportFragmentManager.backStackEntryCount
        Loger.e(BaseFragment.TAG,"switchFragment-backStackEntryCount = $backStackEntryCount")
    }

    open fun popBackStack () {
        try {
            var backStackEntryCount = supportFragmentManager.backStackEntryCount
            Loger.e(BaseFragment.TAG,"popBackStack-backStackEntryCount = $backStackEntryCount")
            if (backStackEntryCount > 0) {
                supportFragmentManager.popBackStack()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}