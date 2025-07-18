package com.victor.lib.common.util

import android.text.TextUtils

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: ConfigLocal
 * Author: Victor
 * Date: 2022/3/1 18:28
 * Description: 
 * -----------------------------------------------------------------
 */

object ConfigLocal {
    private const val PLAY_SIDE_GUIDE = "PLAY_SIDE_GUIDE"
    private const val COURSE_STUDY_SHOW_GUIDE = "COURSE_STUDY_SHOW_GUIDE"

    /**
     * 是否显示播放滑动提示
     * 针对用户
     *
     * @return
     */
    fun needShowPlaySideGuide(userId: String?): Boolean {
        return SharedPreferencesUtils.getBoolean("$PLAY_SIDE_GUIDE:$userId", true)
    }

    fun updateShowPlaySideGuide(userId: String?, enable: Boolean) {
        SharedPreferencesUtils.putBoolean("$PLAY_SIDE_GUIDE:$userId", enable)
    }
    /**
     * 是否显示已购买弹窗提示
     * 针对用户
     *
     * @return
     */
    fun needShowCourseStudyGuide(userId: String?,goodsId: String?): Boolean {
        var today = DateUtil.getTodayDate("yyyy-MM-dd")
        var key = "$COURSE_STUDY_SHOW_GUIDE:$userId:$goodsId"
        var value = "$goodsId:$today"
        var lastValue = SharedPreferencesUtils.getString(key, "")
        if (TextUtils.isEmpty(lastValue)) return true//之前没有显示过就需要显示
        return !TextUtils.equals(value,lastValue)//已经显示过的匹配日期是否是同一天，同一天就不在显示了
    }

    fun updateShowCourseStudyGuide(userId: String?,goodsId: String?) {
        var today = DateUtil.getTodayDate("yyyy-MM-dd")
        var key = "$COURSE_STUDY_SHOW_GUIDE:$userId:$goodsId"
        var value = "$goodsId:$today"

        SharedPreferencesUtils.putString(key, value)
    }
}