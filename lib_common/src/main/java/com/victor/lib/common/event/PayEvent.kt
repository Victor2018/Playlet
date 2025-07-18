package com.victor.lib.common.event

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: PayEvent
 * Author: Victor
 * Date: 2022/3/1 18:28
 * Description: 
 * -----------------------------------------------------------------
 */

object PayEvent {

    /**
     * 线下课直接购买点击
     */
    const val OFFLINE_BUY_CLICK = "Event_OfflineBuyClick"

    /**
     * 支付成功页面
     */
    const val PAY_SUCCESS_VIEW = "Event_PaySuccessView"

    /**
     * 支付失败页面
     */
    const val PAY_FAIL_VIEW = "Event_PayFailView"

}