package com.victor.lib.coremodel.data.remote.entity.bean

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: LoginData
 * Author: Victor
 * Date: 2022/3/1 18:39
 * Description: 
 * -----------------------------------------------------------------
 */

open class LoginData {
    var id: Int = 0
    var name: String? = null//用户名称
    var phone: String? = null//手机号 【如果是微信授权登录，当返回手机号为空时，请让用户绑定手机号再重新请求】
    var token: String? = null//用户token
    var type: Int = 0//用户类型
}