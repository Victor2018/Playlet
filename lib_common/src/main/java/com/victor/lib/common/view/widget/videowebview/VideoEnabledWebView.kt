package com.hok.lib.common.view.widget.videowebview

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.util.Log
import android.webkit.WebChromeClient
import android.webkit.WebView
import com.victor.lib.common.view.widget.videowebview.VideoEnabledWebChromeClient

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: VideoEnabledWebView
 * Author: Victor
 * Date: 2024/04/26 14:42
 * Description: 
 * -----------------------------------------------------------------
 */

class VideoEnabledWebView : WebView {

    var videoEnabledWebChromeClient: VideoEnabledWebChromeClient? = null
    var addedJavascriptInterface = false

    inner class JavascriptInterface {
        @android.webkit.JavascriptInterface
        @Suppress("unused")
        fun notifyVideoEnd() // Must match Javascript interface method of VideoEnabledWebChromeClient
        {
            Log.d("___", "GOT IT")
            // This code is not executed in the UI thread, so we must force that to happen
            Handler(Looper.getMainLooper()).post {
                videoEnabledWebChromeClient?.onHideCustomView()
            }
        }
    }

    @Suppress("unused")
    constructor(context: Context): super(context) {
        addedJavascriptInterface = false
    }

    @Suppress("unused")
    constructor(context: Context, attrs: AttributeSet?): super(context, attrs) {
        addedJavascriptInterface = false
    }

    @Suppress("unused")
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int):  super(context, attrs, defStyle) {
        addedJavascriptInterface = false
    }

    /**
     * Indicates if the video is being displayed using a custom view (typically full-screen)
     * @return true it the video is being displayed using a custom view (typically full-screen)
     */
    @Suppress("unused")
    fun isVideoFullscreen(): Boolean {
        return videoEnabledWebChromeClient != null && videoEnabledWebChromeClient!!.isVideoFullscreen()
    }

    /**
     * Pass only a VideoEnabledWebChromeClient instance.
     */
    @SuppressLint("SetJavaScriptEnabled")
    override fun setWebChromeClient(client: WebChromeClient?) {
        settings.javaScriptEnabled = true
        if (client is VideoEnabledWebChromeClient) {
            videoEnabledWebChromeClient = client
        }
        super.setWebChromeClient(client)
    }

    override fun loadData(data: String, mimeType: String?, encoding: String?) {
        addJavascriptInterface()
        super.loadData(data, mimeType, encoding)
    }

    override fun loadDataWithBaseURL(
        baseUrl: String?,
        data: String,
        mimeType: String?,
        encoding: String?,
        historyUrl: String?
    ) {
        addJavascriptInterface()
        super.loadDataWithBaseURL(baseUrl, data, mimeType, encoding, historyUrl)
    }

    override fun loadUrl(url: String) {
        addJavascriptInterface()
        super.loadUrl(url)
    }

    override fun loadUrl(url: String, additionalHttpHeaders: MutableMap<String, String>) {
        addJavascriptInterface()
        super.loadUrl(url, additionalHttpHeaders)
    }

    private fun addJavascriptInterface() {
        if (!addedJavascriptInterface) {
            // Add javascript interface to be called when the video ends (must be done before page load)
            addJavascriptInterface(
                JavascriptInterface(),
                "_VideoEnabledWebView"
            ) // Must match Javascript interface name of VideoEnabledWebChromeClient
            addedJavascriptInterface = true
        }
    }

    fun onWebPause () {
        pauseTimers()

    }

    fun onWebResume () {
        resumeTimers()
    }

    /**
     * must be called on the main thread
     */
    fun onWebDestroy() {
        try {
            clearHistory()
            clearCache(true)
            webChromeClient = null
            loadUrl("about:blank") // clearView() should be changed to loadUrl("about:blank"), since clearView() is deprecated now
            freeMemory()
            pauseTimers()
            destroy() // Note that mWebView.destroy() and mWebView = null do the exact same thing
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}