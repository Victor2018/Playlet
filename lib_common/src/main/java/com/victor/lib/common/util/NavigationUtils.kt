package com.victor.lib.common.util

import android.app.Activity
import com.alibaba.android.arouter.launcher.ARouter
import com.hok.lib.common.base.ARouterPath
import com.victor.lib.common.R

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: NavigationUtils
 * Author: Victor
 * Date: 2022/3/1 18:28
 * Description: ARouter路由跳转工具类
 * -----------------------------------------------------------------
 */

object NavigationUtils {
    val TITLE_KEY = "TITLE_KEY"
    val TYPE_KEY = "TYPE_KEY"
    val PHONE_KEY = "PHONE_KEY"
    val CODE_KEY = "CODE_KEY"
    val POSITION_KEY = "POSITION_KEY"
    val ID_KEY = "ID_KEY"
    val AUTH_SECRET_KEY = "AUTH_SECRET_KEY"
    val ONE_KEY_LOGIN_ENVAVAIABLE_KEY = "ONE_KEY_LOGIN_ENVAVAIABLE_KEY"

    /**
     * 去往首页
     */

   fun goHomeActivity(activity: Activity, pagerPosition: Int) {
        goHomeActivity(activity,pagerPosition,null)
    }

    fun goHomeActivity(activity: Activity, pagerPosition: Int, pushData: String?) {
        ARouter.getInstance().build(ARouterPath.MainAct)
            .withTransition(R.anim.anim_activity_enter, R.anim.anim_activity_exit)
            .withString(Constant.INTENT_DATA_KEY, pushData)
            .withSerializable(Constant.INTENT_AD_DATA_KEY, null)
            .withSerializable(Constant.INVITE_URL_KEY, null)
            .withInt(Constant.POSITION_KEY, pagerPosition)
            .navigation(activity)
    }


    /**
     * 去往支付订单
     */
    fun goPayOrderActivity(activity: Activity, orderNo: String?) {
        ARouter.getInstance().build(ARouterPath.PayOrderAct)
            .withTransition(R.anim.anim_activity_enter, R.anim.anim_activity_exit)
            .withSerializable(Constant.ORDER_NO_KEY, orderNo)
            .navigation(activity)
    }

    /**
     * 去往验证码登录
     */
    fun goCodeLoginActivity(activity: Activity?) {
        ARouter.getInstance().build(ARouterPath.CodeLoginAct)
            .withTransition(R.anim.anim_activity_enter, R.anim.anim_activity_exit)
            .navigation(activity)
    }

    /**
     * 去往绑定手机号
     */
    fun goBindPhoneActivity(activity: Activity, openId: String?) {
        ARouter.getInstance().build(ARouterPath.BindPhoneAct)
            .withTransition(R.anim.anim_activity_enter, R.anim.anim_activity_exit)
            .withString(Constant.INTENT_DATA_KEY, openId)
            .navigation(activity)
    }

    /**
     * 去往搜索页面
     */
    fun goSearchActivity(activity: Activity, key: String?) {
        ARouter.getInstance().build(ARouterPath.SearchAct)
            .withTransition(R.anim.anim_activity_enter, R.anim.anim_activity_exit)
            .withString(Constant.INTENT_DATA_KEY, key)
            .navigation(activity)
    }

    /**
     * 去往播放页面
     */
    fun goPlayActivity(activity: Activity, position: Int,playPosition: Int) {
        ARouter.getInstance().build(ARouterPath.PlayAct)
            .withTransition(R.anim.anim_activity_enter, R.anim.anim_activity_exit)
            .withInt(Constant.POSITION_KEY, position)
            .withInt(Constant.PLAY_POSITION_KEY, playPosition)
            .navigation(activity)
    }


    /**
     * 去往我的
     */
    fun goMeAct(activity: Activity) {
        ARouter.getInstance().build(ARouterPath.MeAct)
            .withTransition(R.anim.anim_activity_enter, R.anim.anim_activity_exit)
            .navigation(activity)
    }

    /**
     * 去往提现
     */
    fun goWithdrawAct(activity: Activity) {
        ARouter.getInstance().build(ARouterPath.WithdrawAct)
            .withTransition(R.anim.anim_activity_enter, R.anim.anim_activity_exit)
            .navigation(activity)
    }

}