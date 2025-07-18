package com.victor.lib.coremodel.action

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: LoginActions
 * Author: Victor
 * Date: 2022/3/1 18:28
 * Description: 
 * -----------------------------------------------------------------
 */

object LoginActions {

    /**
     * 微信第三方登录
     */
    const val LOGIN_WX_AUTH_ERROR = "LOGIN_WX_AUTH_ERROR"

    /**
     * 微信第三方登录
     */
    const val LOGIN_AUTH_WEIXIN = "LOGIN_AUTH_WEIXIN"

    /**
     * 启动微信登录
     */
    const val GO_WECHAT_LOGIN = "GO_WECHAT_LOGIN"

    /**
     * 启动一键登录
     */
    const val GO_ONE_KEY_LOGIN = "GO_ONE_KEY_LOGIN"

    /**
     * 一键登录页面关闭
     */
    const val ONE_KEY_LOGIN_CLOSE = "ONE_KEY_LOGIN_CLOSE"

    /**
     * 登录成功
     */
    const val LOGIN_SUCCESS = "LOGIN_SUCCESS"

    /**
     * 登出成功
     */
    const val LOGOUT_SUCCESS = "LOGOUT_SUCCESS"

    /**
     * token失效
     */
    const val TOKEN_INVALID = "TOKEN_INVALID"

    /**
     * 用户取消登录
     */
    const val CANCEL_LOGIN = "CANCEL_LOGIN"

}