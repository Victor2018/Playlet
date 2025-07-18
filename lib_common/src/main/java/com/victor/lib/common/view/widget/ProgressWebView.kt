package com.victor.lib.common.view.widget

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Environment
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.widget.ProgressBar
import androidx.constraintlayout.widget.ConstraintLayout
import com.victor.lib.common.R
import com.victor.lib.common.util.Loger
import com.victor.lib.common.util.ViewUtils.show
import com.victor.lib.coremodel.util.AppConfig
import com.victor.lib.coremodel.util.AppUtil

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: ProgressWebView
 * Author: Victor
 * Date: 2022/3/1 18:28
 * Description: 
 * -----------------------------------------------------------------
 */

class ProgressWebView constructor(context: Context, attributeset: AttributeSet) : ConstraintLayout(context, attributeset),
    DownloadListener {

    val TAG = "ProgressWebView"
    var progressBar: ProgressBar
    var webview: WebView
    var isLastLoadSuccess = false//是否成功加载完成过web，成功过后的网络异常 不改变web
    var isError = false

    var openLinkBySysBrowser = false//是否使用系统浏览器打开http链接

    init {
        val rootView = LayoutInflater.from(context).inflate(R.layout.web_progress_view, this, true)
        progressBar = rootView.findViewById(R.id.pb_webview)
        webview = rootView.findViewById(R.id.web_view)
        webview.webChromeClient = MyWebChromeClient()
        webview.webViewClient = MyWebViewClient()
        webview.settings.javaScriptEnabled = true
        webview.settings.domStorageEnabled = true
        webview.settings.allowFileAccess = true
        webview.settings.allowFileAccessFromFileURLs = true
        webview.settings.allowUniversalAccessFromFileURLs = true
        webview.settings.cacheMode = WebSettings.LOAD_NO_CACHE
        webview.setDownloadListener(this)
    }

    private inner class MyWebChromeClient : WebChromeClient() {

        override fun onProgressChanged(view: WebView, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
            setProgress(newProgress)
        }

        override fun onReceivedTitle(view: WebView, title: String) {
            super.onReceivedTitle(view, title)
            if (title.contains("html")) {
                return
            }
            listener?.onTitle(title)
        }
    }

    private fun setProgress(newProgress: Int) {
        if (newProgress == 100) {
            progressBar.visibility = View.GONE
        } else {
            progressBar.progress = newProgress
        }
    }

    private inner class MyWebViewClient : WebViewClient() {

        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            Loger.e(TAG, "shouldOverrideUrlLoading()-url = $url")
            if (TextUtils.isEmpty(url)) return false

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

        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
            //在访问失败的时候会首先回调onReceivedError，然后再回调onPageFinished。
            if (!isError) {
                isLastLoadSuccess = true
                listener?.success()
            }
        }

        override fun onReceivedError(view: WebView, request: WebResourceRequest, error: WebResourceError) {
            super.onReceivedError(view, request, error)
            //在访问失败的时候会首先回调onReceivedError，然后再回调onPageFinished。
            isError = true
            if (!isLastLoadSuccess) {//之前成功加载完成过，不会回调
                listener?.error()
            }
        }
    }

    /**
     * 千万不要更改这个 "SSDJsBirdge"  注意！！！！！
     */
    @SuppressLint("JavascriptInterface")
    fun addJavascriptInterface(jsInterface: Any) {
        webview.addJavascriptInterface(jsInterface, "SSDJsBirdge")
    }

    fun reload() {
        isError = false
        webview.reload()
    }

    fun loadUrl(url: String) {
        isError = false
        try {
            webview.show()
            webview.loadUrl(url)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun loadData (htmlData: String) {
        if (TextUtils.isEmpty(htmlData)) return
        webview.loadData(htmlData,"text/html","utf-8")
    }

    fun loadData (htmlData: String,secondLinkBySysBrowser: Boolean) {
        if (TextUtils.isEmpty(htmlData)) return
        openLinkBySysBrowser = secondLinkBySysBrowser
        webview.loadData(htmlData,"text/html","utf-8")
    }

    fun downloadFile(url: String?,contentDisposition: String?,mimeType: String?) {
        val request = DownloadManager.Request(Uri.parse(url))
        // 允许媒体扫描，根据下载的文件类型被加入相册、音乐等媒体库
        request.allowScanningByMediaScanner()
        // 设置通知的显示类型，下载进行时和完成后显示通知
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        // 设置通知栏的标题，如果不设置，默认使用文件名
        request.setTitle("下载完成")
        // 设置通知栏的描述
//                    request.setDescription("This is description");
        // 允许在计费流量下下载
        request.setAllowedOverMetered(true)
        // 允许该记录在下载管理界面可见
        request.setVisibleInDownloadsUi(true)
        // 允许漫游时下载
        request.setAllowedOverRoaming(true)

        val fileName = URLUtil.guessFileName(url, contentDisposition, mimeType)
        Loger.e(TAG, "downloadFile()-fileName = $fileName")
        request.setDestinationInExternalPublicDir(Environment.getExternalStorageDirectory().toString() + "/Download/", fileName)


        val downloadManager = webview.context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        // 添加一个下载任务
        val downloadId = downloadManager.enqueue(request)
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

    override fun onDownloadStart(url: String?, userAgent: String?, contentDisposition: String?, mimeType: String?, contentLength: Long) {
        Loger.e(TAG,"onDownloadStart()......url = $url")
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        context.startActivity(intent)

//        downloadFile(url,contentDisposition,mimeType)
    }

    fun canGoBack(): Boolean {
        val canGoBack = webview.canGoBack()
        if (canGoBack) {
            webview.goBack()
        }
        return canGoBack
    }

    fun onPause () {
        webview.pauseTimers()

    }

    fun onResume () {
        webview.resumeTimers()
    }

    /**
     * must be called on the main thread
     */
    fun onDestory() {
        try {
            webview.clearHistory()
            webview.clearCache(true)
            webview.webChromeClient = null
            webview.loadUrl("about:blank") // clearView() should be changed to loadUrl("about:blank"), since clearView() is deprecated now
            webview.settings.javaScriptEnabled = false
            webview.parent.let {
                if (it is ViewGroup) {
                    it.removeView(webview)
                }
            }
            webview.removeAllViewsInLayout()
            webview.removeAllViews()
            webview.freeMemory()
            webview.pauseTimers()
            webview.destroy() // Note that mWebView.destroy() and mWebView = null do the exact same thing
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun setWebViewBackgroundColor(isBlack: Boolean) {
        if (isBlack) {
            //防止加载html白屏(针对播放视频)
            setBackgroundColor(Color.BLACK)
        }
    }

    private var listener: OnWebLoadStatusListener? = null

    fun setOnLoadStatueListener(listener: OnWebLoadStatusListener) {
        this.listener = listener
    }

    interface OnWebLoadStatusListener {
        fun error()

        fun success()

        fun onTitle(title: String)
    }

    private fun setLoadProgressListener() {

    }
}