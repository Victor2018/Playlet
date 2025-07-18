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
    private const val COURSE = "/course"
    private const val CUSTOMER_SERVICE = "/customer_service"
    private const val MESSAGE = "/message"
    private const val SHOPPING_CART = "/shopping_cart"
    private const val ME = "/me"
    private const val LIVE = "/live"
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

    /**课程分类 activity */
    const val CourseAct = "$COURSE/module/CourseActivity"

    /**课程 Fragment */
    const val CourseFgt = "$COURSE/module/CourseFragment"

    /**课程详情 Activity */
    const val VideoDetailAct = "$COURSE/module/VideoDetailActivity"

    /**线上课程详情 Activity */
    const val OnlineCourseDetailAct = "$COURSE/module/OnlineCourseDetailActivity"

    /**线下课程详情 Activity */
    const val OfflineCourseDetailAct = "$COURSE/module/OfflineCourseDetailActivity"

    /**图文课程详情 Activity */
    const val GraphicDetailAct = "$COURSE/module/GraphicDetailActivity"

    /**线下已购课程详情-无视频 Activity */
    const val OfflineCourseUnPlayDetailAct = "$COURSE/module/OfflineCourseUnPlayDetailActivity"

    /**线下已购课程详情-有视频 Activity */
    const val OfflineVideoPlayDetailAct = "$COURSE/module/OfflineVideoPlayDetailActivity"

    /**线上已购课程详情 Activity */
    const val VideoPlayDetailAct = "$COURSE/module/VideoPlayDetailActivity"

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

    /**直播详情activity */
    const val LiveDetailAct = "$LIVE/module/LiveDetailActivity"

    /**线上直播课程详情activity */
    const val CourseLiveDetailActivity = "$COURSE/module/CourseLiveDetailActivity"

    /**线上课程图文详情activity */
    const val CourseGraphicDetailsActivity = "$COURSE/module/CourseGraphicDetailsActivity"

    /**线上课程试看详情activity */
    const val TryPlayAct = "$COURSE/module/TryPlayActivity"

    /**课程评价activity */
    const val CourseEvaluateAct = "$COURSE/module/CourseEvaluateActivity"

    /**课程练习activity */
    const val CourseExerciseAct = "$COURSE/module/CourseExerciseActivity"

    /**客服activity */
    const val CustomerServiceActivity = "$CUSTOMER_SERVICE/module/CustomerServiceActivity"

    /**服务中心activity */
    const val ServiceCenterAct = "$CUSTOMER_SERVICE/module/ServiceCenterActivity"

    /**意见反馈activity */
    const val FeedbackAct = "$CUSTOMER_SERVICE/module/FeedbackActivity"

    /**通知activity */
    const val NotificationAct = "$MESSAGE/module/NotificationActivity"

}