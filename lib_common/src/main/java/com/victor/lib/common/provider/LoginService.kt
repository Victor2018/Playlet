package com.victor.lib.common.provider

import com.alibaba.android.arouter.facade.template.IProvider

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: LoginService
 * Author: Victor
 * Date: 2022/3/1 18:28
 * Description: 登录服务
 * -----------------------------------------------------------------
 */

interface LoginService: IProvider {
    fun toLoginView (phone: String, code: Int): String
}