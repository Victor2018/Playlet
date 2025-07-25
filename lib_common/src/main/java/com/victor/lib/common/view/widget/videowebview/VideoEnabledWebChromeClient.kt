package com.victor.lib.common.view.widget.videowebview

import android.media.MediaPlayer
import android.view.SurfaceView
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.widget.FrameLayout
import android.widget.VideoView
import com.hok.lib.common.view.widget.videowebview.VideoEnabledWebView

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: VideoEnabledWebChromeClient
 * Author: Victor
 * Date: 2024/04/26 14:41
 * Description: 
 * -----------------------------------------------------------------
 */


open class VideoEnabledWebChromeClient : WebChromeClient, MediaPlayer.OnPreparedListener,
    MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener {
    interface ToggledFullscreenCallback {
        fun toggledFullscreen(fullscreen: Boolean)
    }

    private var activityNonVideoView: View? = null
    private var activityVideoView: ViewGroup? = null
    private var loadingView: View? = null
    private var webView: VideoEnabledWebView? = null

    private var isVideoFullscreen =
        false // Indicates if the video is being displayed using a custom view (typically full-screen)

    private var videoViewContainer: FrameLayout? = null
    private var videoViewCallback: CustomViewCallback? = null

    private var toggledFullscreenCallback: ToggledFullscreenCallback? = null

    /**
     * Never use this constructor alone.
     * This constructor allows this class to be defined as an inline inner class in which the user can override methods
     */
    @Suppress("unused")
    constructor() {
    }

    /**
     * Builds a video enabled WebChromeClient.
     * @param activityNonVideoView A View in the activity's layout that contains every other view that should be hidden when the video goes full-screen.
     * @param activityVideoView A ViewGroup in the activity's layout that will display the video. Typically you would like this to fill the whole layout.
     */
    @Suppress("unused")
    fun constructor(activityNonVideoView: View?, activityVideoView: ViewGroup?) {
        this.activityNonVideoView = activityNonVideoView
        this.activityVideoView = activityVideoView
        loadingView = null
        webView = null
        isVideoFullscreen = false
    }

    /**
     * Builds a video enabled WebChromeClient.
     * @param activityNonVideoView A View in the activity's layout that contains every other view that should be hidden when the video goes full-screen.
     * @param activityVideoView A ViewGroup in the activity's layout that will display the video. Typically you would like this to fill the whole layout.
     * @param loadingView A View to be shown while the video is loading (typically only used in API level <11). Must be already inflated and not attached to a parent view.
     */
    @Suppress("unused")
    constructor(
        activityNonVideoView: View?,
        activityVideoView: ViewGroup?,
        loadingView: View?
    ) {
        this.activityNonVideoView = activityNonVideoView
        this.activityVideoView = activityVideoView
        this.loadingView = loadingView
        webView = null
        isVideoFullscreen = false
    }

    /**
     * Builds a video enabled WebChromeClient.
     * @param activityNonVideoView A View in the activity's layout that contains every other view that should be hidden when the video goes full-screen.
     * @param activityVideoView A ViewGroup in the activity's layout that will display the video. Typically you would like this to fill the whole layout.
     * @param loadingView A View to be shown while the video is loading (typically only used in API level <11). Must be already inflated and not attached to a parent view.
     * @param webView The owner VideoEnabledWebView. Passing it will enable the VideoEnabledWebChromeClient to detect the HTML5 video ended event and exit full-screen.
     * Note: The web page must only contain one video tag in order for the HTML5 video ended event to work. This could be improved if needed (see Javascript code).
     */
    @Suppress("unused")
    constructor(
        activityNonVideoView: View?,
        activityVideoView: ViewGroup?,
        loadingView: View?,
        webView: VideoEnabledWebView?
    ) {
        this.activityNonVideoView = activityNonVideoView
        this.activityVideoView = activityVideoView
        this.loadingView = loadingView
        this.webView = webView
        isVideoFullscreen = false
    }

    /**
     * Indicates if the video is being displayed using a custom view (typically full-screen)
     * @return true it the video is being displayed using a custom view (typically full-screen)
     */
    fun isVideoFullscreen(): Boolean {
        return isVideoFullscreen
    }

    /**
     * Set a callback that will be fired when the video starts or finishes displaying using a custom view (typically full-screen)
     * @param callback A VideoEnabledWebChromeClient.ToggledFullscreenCallback callback
     */
    @Suppress("unused")
    fun setOnToggledFullscreen(callback: ToggledFullscreenCallback?) {
        toggledFullscreenCallback = callback
    }

    override fun onShowCustomView(view: View?, callback: CustomViewCallback?) {
        if (view is FrameLayout) {
            // A video wants to be shown
            val frameLayout = view
            val focusedChild = frameLayout.focusedChild

            // Save video related variables
            isVideoFullscreen = true
            videoViewContainer = frameLayout
            videoViewCallback = callback

            // Hide the non-video view, add the video view, and show it
            activityNonVideoView?.visibility = View.INVISIBLE
            activityVideoView?.addView(
                videoViewContainer,
                ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            )
            activityVideoView?.visibility = View.VISIBLE
            if (focusedChild is VideoView) {
                // android.widget.VideoView (typically API level <11)
                val videoView = focusedChild

                // Handle all the required events
                videoView.setOnPreparedListener(this)
                videoView.setOnCompletionListener(this)
                videoView.setOnErrorListener(this)
            } else {
                // Other classes, including:
                // - android.webkit.HTML5VideoFullScreen$VideoSurfaceView, which inherits from android.view.SurfaceView (typically API level 11-18)
                // - android.webkit.HTML5VideoFullScreen$VideoTextureView, which inherits from android.view.TextureView (typically API level 11-18)
                // - com.android.org.chromium.content.browser.ContentVideoView$VideoSurfaceView, which inherits from android.view.SurfaceView (typically API level 19+)

                // Handle HTML5 video ended event only if the class is a SurfaceView
                // Test case: TextureView of Sony Xperia T API level 16 doesn't work fullscreen when loading the javascript below
                if (webView != null && webView?.settings?.javaScriptEnabled == true && focusedChild is SurfaceView
                ) {
                    // Run javascript code that detects the video end and notifies the Javascript interface
                    var js = "javascript:"
                    js += "var _ytrp_html5_video_last;"
                    js += "var _ytrp_html5_video = document.getElementsByTagName('video')[0];"
                    js += "if (_ytrp_html5_video != undefined && _ytrp_html5_video != _ytrp_html5_video_last) {"
                    run {
                        js += "_ytrp_html5_video_last = _ytrp_html5_video;"
                        js += "function _ytrp_html5_video_ended() {"
                        run {
                            js += "_VideoEnabledWebView.notifyVideoEnd();" // Must match Javascript interface name and method of VideoEnableWebView
                        }
                        js += "}"
                        js += "_ytrp_html5_video.addEventListener('ended', _ytrp_html5_video_ended);"
                    }
                    js += "}"
                    webView?.loadUrl(js)
                }
            }

            // Notify full-screen change
            toggledFullscreenCallback?.toggledFullscreen(true)
        }
    }

    @Suppress("deprecation")
    override fun onShowCustomView(
        view: View?,
        requestedOrientation: Int,
        callback: CustomViewCallback?
    ) // Available in API level 14+, deprecated in API level 18+
    {
        onShowCustomView(view, callback)
    }

    override fun onHideCustomView() {
        // This method should be manually called on video end in all cases because it's not always called automatically.
        // This method must be manually called on back key press (from this class' onBackPressed() method).
        if (isVideoFullscreen) {
            // Hide the video view, remove it, and show the non-video view
            activityVideoView?.visibility = View.INVISIBLE
            activityVideoView?.removeView(videoViewContainer)
            activityNonVideoView?.visibility = View.VISIBLE

            // Call back (only in API level <19, because in API level 19+ with chromium webview it crashes)
            if (videoViewCallback != null && videoViewCallback?.javaClass?.name?.contains(".chromium.") == false) {
                videoViewCallback?.onCustomViewHidden()
            }

            // Reset video related variables
            isVideoFullscreen = false
            videoViewContainer = null
            videoViewCallback = null

            // Notify full-screen change
            toggledFullscreenCallback?.toggledFullscreen(false)
        }
    }

    override fun getVideoLoadingProgressView(): View? // Video will start loading
    {
        return if (loadingView != null) {
            loadingView?.visibility = View.VISIBLE
            loadingView
        } else {
            super.getVideoLoadingProgressView()
        }
    }

    override fun onPrepared(mp: MediaPlayer?) // Video will start playing, only called in the case of android.widget.VideoView (typically API level <11)
    {
        loadingView?.visibility = View.GONE
    }

    override fun onCompletion(mp: MediaPlayer?) // Video finished playing, only called in the case of android.widget.VideoView (typically API level <11)
    {
        onHideCustomView()
    }

    override fun onError(
        mp: MediaPlayer?,
        what: Int,
        extra: Int
    ): Boolean // Error while playing video, only called in the case of android.widget.VideoView (typically API level <11)
    {
        return false // By returning false, onCompletion() will be called
    }

    /**
     * Notifies the class that the back key has been pressed by the user.
     * This must be called from the Activity's onBackPressed(), and if it returns false, the activity itself should handle it. Otherwise don't do anything.
     * @return Returns true if the event was handled, and false if was not (video view is not visible)
     */
    @Suppress("unused")
    fun onBackPressed(): Boolean {
        return if (isVideoFullscreen) {
            onHideCustomView()
            true
        } else {
            false
        }
    }
}