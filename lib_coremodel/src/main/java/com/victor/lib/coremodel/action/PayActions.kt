package com.victor.lib.coremodel.action

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: PayActions
 * Author: Victor
 * Date: 2022/3/1 18:28
 * Description: 
 * -----------------------------------------------------------------
 */

object PayActions {
    /**
     * 微信支付成功回调
     */
    const val EVENT_WXPAY_RESULT = "EVENT_WXPAY_RESULT"

    /**
     * 支付宝支付成功回调
     */
    const val EVENT_ALIPAY_RESULT = "EVENT_ALIPAY_RESULT"

    /**
     * 支付成功回调
     */
    const val PAY_SUCCESS = "PAY_SUCCESS"
}