package com.victor.lib.common.util

import android.app.Activity
import android.os.Bundle
import com.victor.lib.common.base.NRouterPath
import com.victor.neuro.router.core.NeuroRouter
import org.json.JSONObject

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
        val map = HashMap<String,String>()
        map[Constant.INTENT_DATA_KEY] = pushData ?: ""
        map[Constant.POSITION_KEY] = "$pagerPosition"
        NeuroRouter.instance.navigation(NRouterPath.MainAct,map,activity)
    }

    /**
     * 去往搜索页面
     */
    fun goSearchActivity(activity: Activity, key: String?) {
        NeuroRouter.instance.navigation(NRouterPath.SearchAct, listOf(key ?: ""), activity)
    }

    /**
     * 去往播放页面
     */
    fun goPlayActivity(activity: Activity, position: Int,playPosition: Int) {
        val args = Bundle()
        args.putInt(Constant.POSITION_KEY,position)
        args.putInt(Constant.PLAY_POSITION_KEY,playPosition)
        NeuroRouter.instance.navigation(NRouterPath.PlayAct,args,activity)
    }

    /**
     * 去往提现
     */
    fun goWithdrawAct(activity: Activity) {
        NeuroRouter.instance.navigation(NRouterPath.WithdrawAct,activity)
    }

}