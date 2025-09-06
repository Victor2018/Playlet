package com.victor.lib.common.app

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.text.TextUtils
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import com.tencent.bugly.crashreport.CrashReport
import com.victor.crash.library.SpiderCrashHandler
import com.victor.lib.common.base.BaseApplication
import com.victor.lib.common.module.UMengEventModule
import com.victor.lib.common.util.Constant
import com.victor.lib.common.util.FileUtil
import com.victor.lib.common.util.Loger
import com.victor.lib.common.util.SharedPreferencesUtils
import com.victor.lib.common.view.widget.RvPlayCellView
import com.victor.lib.coremodel.action.LoginActions
import com.victor.lib.coremodel.data.remote.entity.bean.DramaItemInfo
import com.victor.lib.coremodel.data.remote.entity.bean.LoginData
import com.victor.lib.coremodel.data.remote.entity.bean.UserInfo
import com.victor.lib.coremodel.util.AppUtil
import com.victor.lib.coremodel.util.HttpUtil
import com.victor.lib.coremodel.util.WebConfig
import com.victor.lib.video.cache.HttpProxyCacheServer
import com.victor.lib.video.cache.preload.VideoPreLoadFuture
import com.victor.library.bus.LiveDataBus
import org.victor.http.lib.ApiClient
import org.victor.http.lib.util.JsonUtils
import java.io.File
import java.lang.ref.WeakReference

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: App
 * Author: Victor
 * Date: 2022/3/1 18:28
 * Description: 
 * -----------------------------------------------------------------
 */

class App : BaseApplication(), Application.ActivityLifecycleCallbacks {
    val TAG = "App"

    //为避免内存泄漏使用弱引用
    var mCurrentActivity: WeakReference<Activity>? = null
    var allActivitys = ArrayList<Activity>()

    private var mLoginData: LoginData? = null
    private var mUserInfo: UserInfo? = null
    var mPlayInfos: List<DramaItemInfo>? = null
    lateinit var mRvPlayCellView: RvPlayCellView

    val mHttpProxyCacheServer by lazy {
        HttpProxyCacheServer.Builder(this)
            .cacheDirectory(FileUtil.getRootDirFile())
            .maxCacheSize(1024 * 1024 * 1024)
            .maxCacheFilesCount(30)
            .build()
    }

    companion object {
        private lateinit var instance: App
        fun get() = instance
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        //关闭黑夜模式
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        registerActivityLifecycleCallbacks(this)
        //crash spider
        SpiderCrashHandler.init(this)
        //bugly
        CrashReport.initCrashReport(this)
        //云信初始化
        //初始化主进程
        if (AppUtil.inMainProcess(this)) {
//            PluginCore.instance.init(this)
            ApiClient.BASE_URL = WebConfig.getBaseUrl()
        }
        UMengEventModule.preInitSdk(this)

        mRvPlayCellView = RvPlayCellView(this)
    }

    fun setLoginData(loginData: LoginData?) {
        mLoginData = loginData
        SharedPreferencesUtils.loginData = JsonUtils.toJSONString(mLoginData)

        Loger.e(TAG, "setLoginData-token = ${mLoginData?.token}")
        ApiClient.addHeader("VersionCode",HttpUtil.getHttpHeaderParm(this).VersionCode ?: "")
        ApiClient.addHeader("AccessToken", mLoginData?.token ?: "")
        SharedPreferencesUtils.setJPushAlias = false
    }

    fun getLoginData(): LoginData? {
        val logRes = SharedPreferencesUtils.loginData
        if (!TextUtils.isEmpty(logRes)) {
            mLoginData = JsonUtils.parseObject(logRes, LoginData::class.java)
            return mLoginData
        }
        return null
    }

    fun setUserInfo(userInfo: UserInfo?) {
        mUserInfo = userInfo
        SharedPreferencesUtils.userInfo = JsonUtils.toJSONString(mUserInfo)
    }

    fun getUserInfo(): UserInfo? {
        val userRes = SharedPreferencesUtils.userInfo
        if (!TextUtils.isEmpty(userRes)) {
            mUserInfo = JsonUtils.parseObject(userRes, UserInfo::class.java)
            return mUserInfo
        }
        return null
    }

    fun hasLogin(): Boolean {
        if (getLoginData() == null) {
            Loger.e(TAG,"hasLogin()......getLoginData() == null")
            return false
        }
        if (ApiClient.headerHasParm("AccessToken")) {
            Loger.e(TAG,"hasLogin()......ApiClient.headerNoToken()")
            return false
        }
        return true
    }

    fun resetLoginData() {
        Loger.e(TAG, "resetLoginData()......")
        SharedPreferencesUtils.setJPushAlias = false
        setLoginData(null)
        setUserInfo(null)
    }

    override fun onActivityPaused(activity: Activity) {
    }

    override fun onActivityStarted(activity: Activity) {
    }

    override fun onActivityDestroyed(activity: Activity) {
        if (TextUtils.equals("LoginAuthActivity",activity.javaClass.simpleName)) {
            LiveDataBus.sendMulti(LoginActions.ONE_KEY_LOGIN_CLOSE)
        }
        allActivitys.remove(activity)
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }

    override fun onActivityStopped(activity: Activity) {
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        Loger.e(TAG, "onActivityCreated()...simpleName = " + activity.javaClass.simpleName)
        if (!allActivitys.contains(activity)) {
            allActivitys.add(activity)
        }
    }

    override fun onActivityResumed(activity: Activity) {
        Loger.e(TAG, "onActivityResumed()...simpleName = " + activity.javaClass.simpleName)
        mCurrentActivity = WeakReference(activity)
    }

    /**
     * 判断当前页面是不是主界面
     */
    fun isCurrentActivityMain(): Boolean {
        val simpleName = mCurrentActivity?.get()?.javaClass?.simpleName
        return TextUtils.equals("MainActivity", simpleName)
    }

    /**
     * 判断当前页面是不是一键登录
     */
    fun isCurrentLoginActivity(): Boolean {
        return isCurrentLoginAuthActivity() || isCurrentCodeLoginActivity() || isCurrentBindPhoneActivity()
    }

    /**
     * 判断当前页面是不是一键登录
     */
    fun isCurrentLoginAuthActivity(): Boolean {
        val simpleName = mCurrentActivity?.get()?.javaClass?.simpleName
        return TextUtils.equals("LoginAuthActivity", simpleName)
    }

    /**
     * 判断当前页面是不是一键登录
     */
    fun isCurrentCodeLoginActivity(): Boolean {
        val simpleName = mCurrentActivity?.get()?.javaClass?.simpleName
        return TextUtils.equals("CodeLoginActivity", simpleName)
    }

    /**
     * 判断当前页面是不是一键登录
     */
    fun isCurrentBindPhoneActivity(): Boolean {
        val simpleName = mCurrentActivity?.get()?.javaClass?.simpleName
        return TextUtils.equals("BindPhoneActivity", simpleName)
    }

    /**
     * 判断主界面是否已启动
     */
    fun isMainActivityLaunched(): Boolean {
        allActivitys.forEach {
            val simpleName = it.javaClass.simpleName
            if (TextUtils.equals("MainActivity", simpleName)) return true
        }
        return false
    }

    /**
     * 判断课程详情界面是否已启动
     */
    fun isVideoDetailActivityLaunched(): Boolean {
        allActivitys.forEach {
            val simpleName = it.javaClass.simpleName
            if (TextUtils.equals("VideoDetailActivity", simpleName)) return true
        }
        return false
    }

    /**
     * 关闭SplashActivity,MainActivity 以外的activity
     */
    fun finishOtherThenSplashAndMainActivity() {
        allActivitys.forEach {
            val simpleName = it.javaClass.simpleName
            if (!TextUtils.equals("MainActivity", simpleName) &&
                !TextUtils.equals("SplashActivity", simpleName)
            ) {
                it.finish()
            }
        }
    }

    fun finishLoginAuthActivity() {
        allActivitys.forEach {
            val simpleName = it.javaClass.simpleName
            if (TextUtils.equals("LoginAuthActivity", simpleName)) {
                it.finish()
            }
        }
    }
    fun finishVideoWebActivity() {
        allActivitys.forEach {
            val simpleName = it.javaClass.simpleName
            if (TextUtils.equals("VideoWebActivity", simpleName)) {
                it.finish()
            }
        }
    }
    fun finishWebActivity() {
        allActivitys.forEach {
            val simpleName = it.javaClass.simpleName
            if (TextUtils.equals("WebActivity", simpleName)) {
                it.finish()
            }
        }
    }

    fun finishAllActivity() {
        allActivitys.forEach {
            it.finish()
        }
    }

    fun removePlayViewFormParent() {
        val parent = mRvPlayCellView.getParent()
        if (parent != null && parent is ViewGroup) {
            parent.removeView(mRvPlayCellView)
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        mCurrentActivity = null
        allActivitys.clear()
        mLoginData = null
        mUserInfo = null
    }
}