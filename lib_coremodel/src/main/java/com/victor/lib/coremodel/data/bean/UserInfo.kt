package com.victor.lib.coremodel.data.bean

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: UserInfo
 * Author: Victor
 * Date: 2022/3/1 12:06
 * Description: 
 * -----------------------------------------------------------------
 */

open class UserInfo {
    var address: String? = null//详细地址
    var areaName: String? = null//地区名称
    var birthday: Long? = null//生日
    var cityCode: String? = null//城市编码 6位
    var company: String? = null//公司
    var headImg: String? = null//头像url
    var idCard: String? = null//身份证号
    var mailbox: String? = null//邮箱
    var name: String? = null//用户名称
    var introduce: String? = null//简介
    var openId: String? = null//微信开放平台-open_id
    var phone: String? = null//手机号
    var position: String? = null//职位
    var realName: String? = null//真实姓名
    var sex: Int = 0//1为男性，2为女性
    var status: Int = 0//用户状态 -1：删除 0：禁用 1：正常
    var type: Int = 0//用户类型
    var collectionCount: Int = 0//收藏数
    var learningTime: Long = 0//学习时长 单位：秒
    var uid: String? = null//用户uid
    var unionId: String? = null//微信开放平台-用户统一标识
    var wechatNumber: String? = null//微信号-用户统一标识
    var id: String? = null

    var token: String? = null
}