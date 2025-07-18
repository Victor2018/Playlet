package com.victor.lib.common.module

import android.app.Activity
import android.content.Context
import android.media.AudioManager
import com.victor.lib.common.util.ScreenOrientationUtil

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: PlayAdjustHelper
 * Author: Victor
 * Date: 2022/6/22 19:54
 * Description: 播放调节模块
 * -----------------------------------------------------------------
 */

class PlayAdjustHelper(var context: Context) {

    var screenOrientationUtil: ScreenOrientationUtil? = null
    var mAudioManager: AudioManager? = null
    var mMaxVolume = 0
    var mVolume = -1
    var mBrightness = -1f

    init {
        mAudioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager?
        mMaxVolume = mAudioManager?.getStreamMaxVolume(AudioManager.STREAM_MUSIC) ?: 0
    }

    /**
     * 声音调节
     */
    fun onVolSlide(percent: Float): Int {
        if (mAudioManager == null) return 0
        if (mVolume == -1) {
            mVolume = mAudioManager?.getStreamVolume(AudioManager.STREAM_MUSIC) ?: 0
            if (mVolume < 0) {
                mVolume = 0
            }
        }
        var progress = (percent * mMaxVolume).toInt() + mVolume
        if (progress > mMaxVolume) {
            progress = mMaxVolume
        } else if (progress < 0) {
            progress = 0
        }
        mAudioManager?.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0)
        return progress * 100 / mMaxVolume
    }

    /**
     * 音量调节
     */
    fun onBrightnessSlide(percent: Float,activity: Activity?): Int {
        if (mBrightness < 0) {
            mBrightness = activity?.window?.attributes?.screenBrightness ?: 0f
            if (mBrightness <= 0.00f) {
                mBrightness = 0.50f
            }
            if (mBrightness < 0.01f) {
                mBrightness = 0.01f
            }
        }
        val lpa = activity?.window?.attributes
        lpa?.screenBrightness = mBrightness + percent

        var screenBrightness = lpa?.screenBrightness ?: 0f
        if (screenBrightness > 1.0f) {
            screenBrightness = 1.0f
        } else if (screenBrightness < 0.01f) {
            screenBrightness = 0.01f
        }
        lpa?.screenBrightness = screenBrightness
        activity?.window?.attributes = lpa

        return (screenBrightness * 100).toInt()
    }

    fun isPortrait(): Boolean {
        return screenOrientationUtil?.isPortrait() ?: false
    }

    fun toggleScreen() {
        screenOrientationUtil?.toggleScreen()
    }

    fun setLandscape(isLandscape: Boolean) {
        screenOrientationUtil?.isLandscape = isLandscape
    }

    fun onStart(activity: Activity?) {
        screenOrientationUtil?.start(activity)
    }

    fun onStop() {
        screenOrientationUtil!!.stop()
    }

    fun onDestroy() {
        screenOrientationUtil = null
        mAudioManager = null
    }
}