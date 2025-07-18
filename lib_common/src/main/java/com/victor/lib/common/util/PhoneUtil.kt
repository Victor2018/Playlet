package com.victor.lib.common.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.TextUtils
import java.lang.Exception

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: PhoneUtil
 * Author: Victor
 * Date: 2022/3/1 18:28
 * Description: 
 * -----------------------------------------------------------------
 */

object PhoneUtil {

    /**
     * 手机号脱敏筛选正则
     */
    const val PHONE_BLUR_REGEX: String = "(\\d{3})\\d{4}(\\d{4})"

    /**
     * 手机号脱敏替换正则
     */
    const val PHONE_BLUR_REPLACE_REGEX = "$1****$2"

    /**
     * 手机号脱敏处理
     * @param phone
     * @return
     */
    fun blurPhone(phone: String?): String? {
        return try {
            phone?.replace(PHONE_BLUR_REGEX.toRegex(), PHONE_BLUR_REPLACE_REGEX)
        } catch (e: Exception) {
            e.printStackTrace()
            return phone
        }
    }

    /**
     * 拨打电话（直接拨打电话）
     * @param phoneNum 电话号码
     */
    fun callPhone(context: Context?, phoneNum: String?){
        if (TextUtils.isEmpty(phoneNum)) {
            ToastUtils.show("号码不能为空")
            return
        }
        var intent = Intent(Intent.ACTION_CALL)
        var data = Uri.parse("tel:$phoneNum")
        intent.data = data
        context?.startActivity(intent)
    }


    /**
     * 拨打电话（跳转到拨号界面，用户手动点击拨打）
     *
     * @param phoneNum 电话号码
     */
    fun toCallPhone(context: Context?, phoneNum: String?) {
        if (TextUtils.isEmpty(phoneNum)) {
            ToastUtils.show("号码不能为空")
            return
        }
        var intent = Intent(Intent.ACTION_DIAL)
        var data = Uri.parse("tel:$phoneNum")
        intent.data = data
        context?.startActivity(intent)
    }
}