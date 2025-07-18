package com.victor.lib.common.util

import android.app.Activity
import android.content.pm.ActivityInfo
import android.util.Log
import android.view.OrientationEventListener

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: ScreenOrientationUtil
 * Author: Victor
 * Date: 2022/6/22 19:42
 * Description: 
 * -----------------------------------------------------------------
 */

 class ScreenOrientationUtil {
     private val TAG = "ScreenOrientationUtil"
     private object Holder {val instance = ScreenOrientationUtil() }

     companion object {
         val  instance: ScreenOrientationUtil by lazy { Holder.instance }
     }

     var mOrientation = 0
     var mOrEventListener: OrientationEventListener? = null

     var mOrientation1 = 0
     var mOrEventListener1: OrientationEventListener? = null

     var mActivity: Activity? = null
     var isLandscape = false

     fun start(activity: Activity?) {
         mActivity = activity
         if (mOrEventListener == null) {
             initListener()
         }
         mOrEventListener?.enable()
     }

     fun stop() {
         mOrEventListener?.disable()
         mOrEventListener1?.disable()
     }

     fun toggleScreen() {
         mOrEventListener!!.disable()
         mOrEventListener1!!.enable()
         var orientation = 0
         if (mOrientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
             orientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE
         } else if (mOrientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
             orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
         } else if (mOrientation == ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE) {
             orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
         } else if (mOrientation == ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT) {
             orientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE
         }
         mOrientation = orientation
         mActivity!!.requestedOrientation = mOrientation
     }

     private fun convert2Orientation(rotation: Int): Int {
         var orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
         orientation = if (rotation >= 0 && rotation <= 45 || rotation > 315) {
             ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
         } else if (rotation > 45 && rotation <= 135) {
             ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE
         } else if (rotation > 135 && rotation <= 225) {
             ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT
         } else if (rotation > 225 && rotation <= 315) {
             ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
         } else {
             ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
         }
         return orientation
     }

     fun isPortrait(): Boolean {
         return mOrientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                 || mOrientation == ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT
     }

     private fun initListener() {
         mOrEventListener = object : OrientationEventListener(mActivity) {
             override fun onOrientationChanged(rotation: Int) {
                 Log.e("test", "" + rotation)
                 if (rotation == ORIENTATION_UNKNOWN) {
                     return
                 }
                 val orientation: Int = convert2Orientation(rotation)
                 // 方向没有变化,跳过
                 if (orientation == mOrientation) {
                     return
                 }
                 if (isLandscape) {
                     if (orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                         || orientation == ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT
                     ) {
                         return
                     }
                 }
                 mOrientation = orientation
                 mActivity?.requestedOrientation = mOrientation
             }
         }
         mOrEventListener1 = object : OrientationEventListener(mActivity) {
             override fun onOrientationChanged(rotation: Int) {
                 if (rotation == ORIENTATION_UNKNOWN) {
                     return
                 }
                 val orientation: Int = convert2Orientation(rotation)
                 // 方向没有变化,跳过
                 if (orientation == mOrientation1) {
                     return
                 }
                 mOrientation1 = orientation
                 if (mOrientation1 == mOrientation) {
                     mOrEventListener1?.disable()
                     mOrEventListener?.enable()
                 }
             }
         }
     }
}