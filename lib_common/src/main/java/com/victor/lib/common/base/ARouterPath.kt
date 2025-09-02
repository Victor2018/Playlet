package com.hok.lib.common.base

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: ARouterPath
 * Author: Victor
 * Date: 2022/3/1 18:28
 * Description: 
 * -----------------------------------------------------------------
 */

object ARouterPath {
    private const val SERVICE = "/service"

    private const val LOGIN = "/login"
    private const val MAIN = "/main"

    private const val HOME = "/home"
    private const val THEATER = "/theater"
    private const val CUSTOMER_SERVICE = "/customer_service"
    private const val MESSAGE = "/message"
    private const val SHOPPING_CART = "/shopping_cart"
    private const val ME = "/me"
    private const val WELFARE = "/welfare"
    private const val STUDY_CENTER = "/study_center"

    /**登录服务 */
    const val loginService = "$SERVICE/common/LoginService"
    /**腾讯IM服务 */
    const val tencentIMService = "$SERVICE/common/TencentIMService"

    /**主 Activity */
    const val MainAct = "$MAIN/app/MainActivity"

    /**机构主 Activity */
    const val OrganizationAct = "$MAIN/app/OrganizationActivity"

    /**首页 Fragment */
    const val HomeFgt = "$HOME/module/HomeFragment"

    /**机构首页 Fragment */
    const val OrganHomeFgt = "$HOME/module/OrganHomeFragment"

    /**教师详情 activity */
    const val TeacherDetailAct = "$HOME/module/TeacherDetailActivity"

    /**已购买教师详情 activity */
    const val TeacherPaidDetailAct = "$HOME/module/TeacherPaidDetailActivity"

    /**搜索 activity */
    const val SearchAct = "$HOME/module/SearchActivity"

    /**专题失效 activity */
    const val TopicInvalidAct = "$HOME/module/TopicInvalidActivity"

    /**错误 activity */
    const val ErrorAct = "$HOME/module/ErrorActivity"

    /**剧场 Fragment */
    const val TheaterFgt = "$THEATER/module/TheaterFragment"

    /**消息 Fragment */
    const val MessageFgt = "$MESSAGE/module/MessageFragment"

    /**购物车 Fragment */
    const val ShoppingCartFgt = "$SHOPPING_CART/module/ShoppingCartFragment"

    /**支付订单 Activity */
    const val PayOrderAct = "$SHOPPING_CART/module/PayOrderActivity"

    /**订单详情 Activity */
    const val OrderDetailAct = "$SHOPPING_CART/module/OrderDetailActivity"

    /**售后详情 Activity */
    const val RefundAct = "$SHOPPING_CART/module/RefundActivity"

    /**支付成功 Activity */
    const val PaySuccessAct = "$SHOPPING_CART/module/PaySuccessActivity"

    /**支付失败 Activity */
    const val PayFailedAct = "$SHOPPING_CART/module/PayFailedActivity"

    /**申请售后 Activity */
    const val AfterSaleAct = "$SHOPPING_CART/module/AfterSaleActivity"

    /**售后-超时已关闭 Activity */
    const val AfterSaleOrderCloseAct = "$SHOPPING_CART/module/AfterSaleOrderCloseActivity"

    /**物流 Activity */
    const val LogisticsAct = "$SHOPPING_CART/module/LogisticsActivity"

    /**我的 Fragment */
    const val MeFgt = "$ME/module/MeFragment"

    /**我的 Activity */
    const val MeAct = "$ME/module/MeActivity"

    /**提现 Activity */
    const val WithdrawAct = "$ME/module/WithdrawActivity"


    /**学习中心 Fragment */
    const val StudyCenterFgt = "$STUDY_CENTER/module/StudyCenterFragment"

    /**我的已购 activity */
    const val PurchasedCourseAct = "$STUDY_CENTER/module/PurchasedCourseActivity"

    /**我的订单 activity */
    const val MyPurchasedAct = "$ME/module/MyPurchasedActivity"

    /**我的优惠券 activity */
    const val MyCouponAct = "$ME/module/MyCouponActivity"

    /**资料下载 activity */
    const val MyDownloadAct = "$ME/module/MyDownloadActivity"

    /**观看历史 activity */
    const val WatchHistoryAct = "$ME/module/WatchHistoryActivity"

    /**我的练习 activity */
    const val MyPracticeAct = "$ME/module/MyPracticeActivity"

    /**推广页 activity */
    const val PromoteAct = "$ME/module/PromoteActivity"

    /**我的推广页 activity */
    const val MyPromoteAct = "$ME/module/MyPromoteActivity"

    /**开票详情 activity */
    const val InvoiceDetailAct = "$ME/module/InvoiceDetailActivity"

    /**开票 activity */
    const val NotInvoicedAct = "$ME/module/NotInvoicedActivity"

    /**我的奖品 activity */
    const val MyPrizeAct = "$ME/module/MyPrizeActivity"

    /**登录 Fragment */
    const val LoginFgt = "$LOGIN/module/LoginFragment"

    /**验证码登录 Activity */
    const val CodeLoginAct = "$LOGIN/module/CodeLoginActivity"

    /**绑定手机号 Fragment */
    const val BindPhoneAct = "$LOGIN/module/BindPhoneActivity"

    /**福利 Fragment */
    const val WelfareFgt = "$WELFARE/module/WelfareFragment"


    /**客服activity */
    const val CustomerServiceActivity = "$CUSTOMER_SERVICE/module/CustomerServiceActivity"

    /**服务中心activity */
    const val ServiceCenterAct = "$CUSTOMER_SERVICE/module/ServiceCenterActivity"

    /**意见反馈activity */
    const val FeedbackAct = "$CUSTOMER_SERVICE/module/FeedbackActivity"

    /**通知activity */
    const val NotificationAct = "$MESSAGE/module/NotificationActivity"

}