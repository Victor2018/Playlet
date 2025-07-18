package com.victor.lib.common.view.widget.videowebview

import android.text.TextUtils
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.victor.lib.common.util.Loger
import com.victor.lib.coremodel.util.AppConfig
import com.victor.lib.coremodel.util.AppUtil


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: InsideWebViewClient
 * Author: Victor
 * Date: 2024/04/28 18:07
 * Description: 
 * -----------------------------------------------------------------
 */

class InsideWebViewClient : WebViewClient() {
    val TAG = "InsideWebViewClient"
    // Force links to be opened inside WebView and not in Default Browser
    // Thanks http://stackoverflow.com/a/33681975/1815624
    override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
        if (!url.startsWith(AppConfig.HTTP)) {
            return deepLink(view, url)
        }

        return super.shouldOverrideUrlLoading(view, url)
    }

    override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
        var url = request.url?.toString() ?: ""
        Loger.e(TAG, "shouldOverrideUrlLoading()-req-url = $url")
        if (!url.startsWith(AppConfig.HTTP)) {
            return deepLink(view, url)
        }

        return super.shouldOverrideUrlLoading(view, request)
    }

    fun deepLink(view: WebView,url: String): Boolean {
        Loger.e(TAG, "deepLink()-url = $url")
        if (TextUtils.isEmpty(url)) return false
        return try {
            //不是http 开始就是 scheme URL 使用系统拉起scheme deeplink
            AppUtil.launchWeb(view.context,url,false)
            true
        } catch (e: Exception) {//防止crash (如果手机上没有安装处理某个scheme开头的url的APP, 会导致crash)
            e.printStackTrace()
            true//没有安装该app时，返回true，表示拦截自定义链接，但不跳转，避免弹出上面的错误页面
        }
    }

}