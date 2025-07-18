package com.victor.lib.common.event

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: LoginEvent
 * Author: Victor
 * Date: 2022/3/1 18:28
 * Description: 
 * -----------------------------------------------------------------
 */

object LoginEvent {

    /**
     * 登录页面浏览(一键登录)
     */
    const val LOAD_ONE_CLICK_VIEW = "Event_LoadOneClickView"

    /**
     * 登录页面浏览(短信)
     */
    const val LOAD_SMS_CLICK_VIEW = "Event_LoadSMSClick"

    /**
     * 登录按钮点击(一键登录)
     */
    const val LOAD_ONE_CLICK = "Event_LoadOneClick"

    /**
     * 登录页面浏览(短信)
     */
    const val LOAD_SMS_CLICK = "Event_LoadSMSClick"

    /**
     * 登录成功(一键登录)
     */
    const val LOAD_ONE_CLICK_SUCCESS = "Event_LoadOneClickSuccess"

    /**
     * 登录成功(微信)
     */
    const val LOAD_WEIXIN_SUCCESS = "Event_LoadWeixinSuccess"

    /**
     * 登录成功(短信)
     */
    const val LOAD_SMS_SUCCESS = "Event_LoadSMSSuccess"

    /**
     * 登录失败(一键登录)
     */
    const val LOAD_ONE_CLICK_FAIL = "Event_LoadOneClickFail"

    /**
     * 登录失败(微信)
     */
    const val LOAD_WEIXIN_FAIL = "Event_LoadWeixinFail"

    /**
     * 登录失败(短信)
     */
    const val LOAD_SMS_FAIL = "Event_LoadSMSFail"
}