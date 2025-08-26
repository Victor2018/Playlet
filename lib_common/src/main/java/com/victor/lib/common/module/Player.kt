package com.victor.lib.common.module

import android.graphics.SurfaceTexture
import android.media.MediaPlayer
import android.media.PlaybackParams
import android.os.Build
import android.os.Handler
import android.text.TextUtils
import android.util.Log
import android.view.Surface
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.TextureView
import android.view.View
import com.victor.lib.common.app.App
import com.victor.lib.common.data.SubTitleInfo
import com.victor.lib.common.util.Loger
import com.victor.lib.common.util.ResUtils
import com.victor.lib.video.cache.HttpProxyCacheServer
import java.util.*


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: Player
 * Author: Victor
 * Date: 2022/6/16 11:21
 * Description: 
 * -----------------------------------------------------------------
 */

class Player : TextureView.SurfaceTextureListener,
    MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnCompletionListener,
    MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener,
    MediaPlayer.OnInfoListener, MediaPlayer.OnSeekCompleteListener,
    SurfaceHolder.Callback, MediaPlayer.OnVideoSizeChangedListener {

    companion object {
        const val PLAYER_PREPARING = 0xf100
        const val PLAYER_PREPARED = 0xf101
        const val PLAYER_BUFFERING_START = 0xf102
        const val PLAYER_BUFFERING_END = 0xf103
        const val PLAYER_ERROR = 0xf104
        const val PLAYER_SEEK_END = 0xf105
        const val PLAYER_PROGRESS_INFO = 0xf106
        const val PLAYER_COMPLETE = 0xf107
        const val PLAYER_CAN_NOT_SEEK = 0xf108
        const val HIDE_PLAY_CTRL_VIEW = 0xf109
        const val PLAYER_PLAYING = 0xf110
        const val PLAYER_PAUSE = 0xf111
        const val PLAYER_SEEK_COMPLETE = 0xf112
        const val TRACK_READY = 0xf113
        const val PLAYER_REPLAY = 0xf114
    }

    private val TAG = "Player"
    private var videoWidth: Int = 0
    private var videoHeight: Int = 0
    private var mMediaPlayer: MediaPlayer? = null
    private var mTextureView: TextureView? = null

    private var mSurfaceTexture: SurfaceTexture? = null
    private var mSurface: Surface? = null

    private var mSurfaceView: SurfaceView? = null // 绘制View
    private var mSurfaceHolder: SurfaceHolder? = null // 显示一个surface的抽象接口，使你可以控制surface的大小和格式
    private var mNotifyHandler: Handler? = null

    private var mPlayUrl: String? = null
    private var mIsLive: Boolean = false
    private var replayCount: Int = 0//重播次数

    internal var mTimer: Timer? = Timer()
    internal var mTimerTask: TimerTask? = PlayTimerTask()
    private var isTimerRun: Boolean = false

    private var mBufferPercentage: Int = 0
    private var subTitleInfos: HashMap<Int, SubTitleInfo>? = null

    private constructor() {
    }

    constructor(textureView: TextureView, handler: Handler?) {
        Log.d(TAG, "create by......textureView")
        mNotifyHandler = handler
        mTextureView = textureView
        mTextureView?.surfaceTextureListener = this
        createMediaPlayer()
    }

    constructor(surfaceView: SurfaceView, handler: Handler?) {
        Log.d(TAG, "create by......surfaceView")
        mNotifyHandler = handler
        open(surfaceView)
        createMediaPlayer()
    }

    override fun onSurfaceTextureAvailable(
        surfaceTexture: SurfaceTexture,
        width: Int,
        height: Int
    ) {
        Log.d(TAG, "onSurfaceTextureAvailable()......")
        if (mSurfaceTexture == null) {
            mSurfaceTexture = surfaceTexture
            createMediaPlayer()
            setDisplay()
            openVideo()
        } else {
            mTextureView?.setSurfaceTexture(mSurfaceTexture!!)
        }
    }

    override fun onSurfaceTextureSizeChanged(
        surfaceTexture: SurfaceTexture,
        width: Int,
        height: Int
    ) {
        Log.d(TAG, "onSurfaceTextureSizeChanged()......")
    }

    override fun onSurfaceTextureUpdated(surfaceTexture: SurfaceTexture) {
    }

    override fun onSurfaceTextureDestroyed(surfaceTexture: SurfaceTexture): Boolean {
        Log.e(TAG, "onSurfaceTextureDestroyed()......")
        return mSurfaceTexture == null
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        mSurfaceHolder = holder
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        mSurfaceHolder = holder
        setDisplay()
        openVideo()
    }

    fun createMediaPlayer() {
        Log.e(TAG, "createMediaPlayer()......")
        try {
            if (mMediaPlayer != null) {
                mMediaPlayer?.setOnPreparedListener(null)
                mMediaPlayer?.setOnErrorListener(null)
                mMediaPlayer?.setOnBufferingUpdateListener(null)
                mMediaPlayer?.setOnInfoListener(null)
                mMediaPlayer?.setOnSeekCompleteListener(null)
                mMediaPlayer?.setOnCompletionListener(null)
                mMediaPlayer?.reset()
                mMediaPlayer?.release()
                mMediaPlayer = null
            }

            mMediaPlayer = MediaPlayer()
            mMediaPlayer?.setOnPreparedListener(this)
            mMediaPlayer?.setOnErrorListener(this)
            mMediaPlayer?.setOnBufferingUpdateListener(this)
            mMediaPlayer?.setOnInfoListener(this)
            mMediaPlayer?.setOnSeekCompleteListener(this)
            mMediaPlayer?.setOnCompletionListener(this)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun setDisplay() {
        if (mSurfaceTexture != null) {
            mSurface = Surface(mSurfaceTexture)
            mMediaPlayer?.setSurface(mSurface)
            mTextureView?.keepScreenOn = true
        }

        if (mSurfaceHolder != null) {
            mMediaPlayer?.setDisplay(mSurfaceHolder) // 设置画面输出
            mMediaPlayer?.setScreenOnWhilePlaying(true) // 保持屏幕高亮
        }
    }


    fun open(surfaceView: SurfaceView) {
        Log.e(TAG, "open()......")
        mSurfaceView = surfaceView
        mSurfaceHolder = mSurfaceView?.holder
        mSurfaceHolder?.addCallback(this)
        mSurfaceHolder?.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS) // 视频缓冲完就开始播放，不使用SurfaceView的缓冲区
    }

    fun openVideo() {
        Log.e(TAG, "openVideo()......")
        mNotifyHandler?.removeMessages(PLAYER_BUFFERING_END)
        if (TextUtils.isEmpty(mPlayUrl)) {
            Log.d(TAG, "mPlayUrl or is empty")
            return
        }
        if (mSurface == null && mSurfaceHolder == null) {
            Log.d(TAG, "mSurface or mSurfaceHolder is null")
            return
        }

        synchronized(this) {
            try {
                mNotifyHandler?.sendEmptyMessage(PLAYER_PREPARING)
                mMediaPlayer?.reset()
                mMediaPlayer?.setDataSource(mPlayUrl)
                mMediaPlayer?.prepareAsync()
                startNotify()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onPrepared(mp: MediaPlayer?) {
        Log.e(TAG, "onPrepared()......")
        stopTimer()
        videoWidth = mMediaPlayer!!.getVideoWidth()
        videoHeight = mMediaPlayer!!.getVideoHeight()

        Log.e(TAG, "onPrepared()......videoWidth = $videoWidth")
        Log.e(TAG, "onPrepared()......videoHeight = $videoHeight")

        //设置显示宽高比，避免画面拉伸
        setSurfaceViewParms()

        //会导致音频播放状态异常
//        if (videoHeight != 0 && videoWidth != 0) {
        mNotifyHandler?.removeMessages(PLAYER_PREPARED)
        mNotifyHandler?.sendEmptyMessage(PLAYER_PREPARED)
        mp?.start()
//        }
    }

    override fun onInfo(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        Log.e(TAG, "onInfo()......")

        when (what) {
            MediaPlayer.MEDIA_INFO_BUFFERING_START -> {
                // replay every 10s
                startTimer()
                Log.e(TAG, "onInfo-buffer start......")
                // here we delay send message to resume play.
                mNotifyHandler?.removeMessages(PLAYER_BUFFERING_START)
                mNotifyHandler?.sendEmptyMessage(PLAYER_BUFFERING_START)
            }
            MediaPlayer.MEDIA_INFO_BUFFERING_END -> {
                stopTimer()
                Log.e(TAG, "onInfo-buffer end......")

                // here we delay send message to resume play.
                mNotifyHandler?.removeMessages(PLAYER_BUFFERING_END)
                mNotifyHandler?.sendEmptyMessage(PLAYER_BUFFERING_END)
            }
            MediaPlayer.MEDIA_INFO_BAD_INTERLEAVING -> {
            }
            MediaPlayer.MEDIA_INFO_NOT_SEEKABLE -> mNotifyHandler?.sendEmptyMessage(
                PLAYER_CAN_NOT_SEEK
            )
            MediaPlayer.MEDIA_INFO_UNKNOWN -> Log.e(TAG, "UNKNOWN...")
        }
        return false
    }

    override fun onSeekComplete(mp: MediaPlayer?) {
        Log.e(TAG, "onSeekComplete()......")
        mNotifyHandler?.sendEmptyMessage(PLAYER_SEEK_END)
    }

    override fun onBufferingUpdate(mp: MediaPlayer?, percent: Int) {
        mBufferPercentage = percent
    }

    override fun onCompletion(mp: MediaPlayer?) {
        if (mIsLive) {
            replay()
            return
        }
        mNotifyHandler?.sendEmptyMessage(PLAYER_COMPLETE)
    }


    override fun onError(mp: MediaPlayer?, p1: Int, p2: Int): Boolean {
        Log.e(TAG, "onError()......")
        replay()
        return false
    }

    override fun onVideoSizeChanged(mp: MediaPlayer?, width: Int, height: Int) {
    }


    fun close() {
        Log.d(TAG, "close()......")
        mNotifyHandler?.removeCallbacks(mRunable)
        releaseMediaPlayer()
    }

    fun startNotify() {
        Log.e(TAG, "startNotify()......")
        mNotifyHandler?.post(mRunable)
    }

    internal var mRunable: Runnable = object : Runnable {

        override fun run() {
            val msg = mNotifyHandler?.obtainMessage(PLAYER_PROGRESS_INFO, showSubTitle())
            msg?.let { mNotifyHandler?.sendMessage(it) }
            mNotifyHandler?.postDelayed(this, 500)

        }
    }

    fun getBufferPercentage(): Int {
        return mBufferPercentage
    }

    fun getMediaPlayer(): MediaPlayer {
        return mMediaPlayer!!
    }

    fun isPlaying(): Boolean {
        return if (mMediaPlayer == null) false else mMediaPlayer!!.isPlaying()
    }

    fun playUrl(videoUrl: String?) {
//        playUrl(videoUrl,false)

        val proxy: HttpProxyCacheServer = App.get().mHttpProxyCacheServer
        val proxyUrl = proxy.getProxyUrl(videoUrl)
        playUrl(proxyUrl,false)
    }

    fun playUrl(videoUrl: String?, isLive: Boolean) {
        Log.e(TAG, "playUrl()......$videoUrl")
        mPlayUrl = videoUrl
        mIsLive = isLive
        openVideo()
    }

    fun pause() {
        Log.e(TAG, "pause()......")
        if (isPlaying()) {
            mMediaPlayer?.pause()
            mNotifyHandler?.sendEmptyMessage(PLAYER_PAUSE)
        }
    }

    fun resume() {
        Log.e(TAG, "resume()......")
        if (!mMediaPlayer!!.isPlaying()) {
            mMediaPlayer?.start()
            mNotifyHandler?.sendEmptyMessage(PLAYER_PLAYING)
        }
    }

    fun replay() {
        replayCount++
        stopTimer()
        Log.e(TAG, "replay()......replayCount = $replayCount")
        if (replayCount >= 3) {
            replayCount = 0
            if (!mIsLive) {
                stop()
                mNotifyHandler?.removeMessages(PLAYER_ERROR)
                mNotifyHandler?.sendEmptyMessage(PLAYER_ERROR)
                return
            }
        }
        playUrl(mPlayUrl, mIsLive)
    }

    fun stop() {
        Log.e(TAG, "stop()......")
        mPlayUrl = null
        mMediaPlayer?.stop()
    }

    fun seekTo(msec: Int) {
        Log.e(TAG, "seekTo()......")
        mMediaPlayer?.seekTo(msec)
        mNotifyHandler?.sendEmptyMessage(HIDE_PLAY_CTRL_VIEW)
    }

    fun getCurrentPosition(): Int {
        return if (mMediaPlayer == null) 0 else mMediaPlayer?.getCurrentPosition()!!
    }

    fun getDuration(): Int {
        return if (mMediaPlayer == null) 0 else mMediaPlayer?.getDuration()!!
    }

    fun setSpeed (speed: Float) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                var playbackParams = mMediaPlayer?.playbackParams
                if (playbackParams == null) {
                    playbackParams = PlaybackParams()
                }
                playbackParams.speed = speed
                mMediaPlayer?.playbackParams = playbackParams
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun setLoop (looping: Boolean) {
        mMediaPlayer?.isLooping = looping
    }

    fun setNeedMute (needMute: Boolean) {
        if (needMute) {
            mMediaPlayer?.setVolume(0f, 0f)
        } else {
            mMediaPlayer?.setVolume(1f, 1f)
        }
    }

    fun getTrackInfo (): Array<out MediaPlayer.TrackInfo>? {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            return mMediaPlayer?.trackInfo
        }
        return null
    }

    private fun releaseMediaPlayer() {
        Log.e(TAG, "releaseMediaPlayer()......")
        synchronized(this) {
            mMediaPlayer?.stop() // mMediaPlayer.reset();
            mMediaPlayer?.release()
            mMediaPlayer = null
        }

        mTextureView?.surfaceTextureListener = null
        mSurfaceTexture?.release()
        mSurfaceTexture = null
        mSurface?.release()
        mSurface = null
        mTextureView = null
        mSurfaceView = null
    }

    fun getLastPlayUrl(): String? {
        return mPlayUrl
    }

    internal inner class PlayTimerTask : TimerTask() {

        override fun run() {
            replay()
        }
    }

    private fun startTimer() {
        Log.e(TAG, "startTimer()......")
        if (mTimer == null) {
            mTimer = Timer()
        }
        if (mTimerTask == null) {
            mTimerTask = PlayTimerTask()
        }
        if (isTimerRun == false) {
            mTimer?.schedule(mTimerTask, 10000, 10000)
            isTimerRun = true
        }

    }

    private fun stopTimer() {
        Log.e(TAG, "stopTimer()......")
        mTimer?.cancel()
        mTimer = null

        mTimerTask?.cancel()
        mTimerTask = null

        isTimerRun = false
    }

    fun setSubTitle(datas: HashMap<Int, SubTitleInfo>) {
        subTitleInfos = datas
    }

    fun showSubTitle(): String {
        var subTitle = ""
        if (subTitleInfos == null) {
            return subTitle
        }
        if (subTitleInfos?.size == 0) {
            return subTitle
        }
        val currentPosition = getCurrentPosition()
        val keys = subTitleInfos?.keys?.iterator()
        while (keys!!.hasNext()) {
            val key = keys.next()
            val bean = subTitleInfos!![key]
            if (currentPosition > bean!!.beginTime && currentPosition < bean!!.endTime) {
                subTitle = bean?.srtBody!!
                break
            }
        }
        return subTitle
    }

    fun setSurfaceViewParms() {
        if (mSurfaceView != null) {
            setSurfaceViewParms(mSurfaceView)
        }
        if (mTextureView != null) {
            setSurfaceViewParms(mTextureView)
        }
    }

    private fun setSurfaceViewParms(displayView: View?) {
        if (displayView != null) {
            val layoutParams = displayView.layoutParams
            val videoWidth = videoWidth.toDouble()
            val videoHeight = videoHeight.toDouble()
            if (videoWidth > 0 && videoHeight > 0) {
                val aspectRatio = videoWidth / videoHeight
                Loger.e(TAG, "setSurfaceViewParms-aspectRatio = $aspectRatio")
                val width = ResUtils.getDimenPixRes(com.victor.screen.match.library.R.dimen.dp_750)
                layoutParams?.height = (width / aspectRatio).toInt()
            }
            displayView.layoutParams = layoutParams
        }
    }
}