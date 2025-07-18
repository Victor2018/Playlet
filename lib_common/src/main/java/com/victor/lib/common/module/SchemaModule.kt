package com.victor.lib.common.module

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.TextUtils
import com.victor.lib.common.R
import com.victor.lib.common.util.NavigationUtils
import com.victor.lib.common.app.App
import com.victor.lib.common.util.Constant
import com.victor.lib.common.util.Loger

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: SchemaModule
 * Author: Victor
 * Date: 2022/3/1 18:28
 * Description: schema跳转模块
 * -----------------------------------------------------------------
 */

object SchemaModule {
    var TAG = "SchemaModule"

    /**
     * 打开外部地址
     */
    val DOMAIN_OPEN_URL = "openurl"
    val DOMAIN_MAIL = "mail"
    val DOMAIN_COURSE_DETAIL = "CourseDetail"
    val DOMAIN_ORDER_DETAIL = "OrderDetail"
    val DOMAIN_AFTER_SALE_DETAIL = "AfterSaleDetail"
    val DOMAIN_TEACHER_DETAIL = "TeacherDetail"
    val DOMAIN_TOPIC_INVALID = "TopicInvalid"
    val DOMAIN_COURSE_CATEGORY = "CourseCategory"
    val HOT_LIVE = "HotLive"
    val PAY_ORDER = "PayOrder"

    /**
     * 当前界面传入
     *
     * @param ctx
     * @param uri
     */
    fun dispatchSchema(activity: Activity, uri: Uri?) {
        Loger.e(TAG, "dispatchSchema()-uri = $uri")
        if (uri == null) return
        val domain = uri.authority
        try {
            when (domain) {
                DOMAIN_OPEN_URL -> {
                    //打开浏览器
                    App.get().finishWebActivity()
                    schemaWeb(activity, uri)
                }
                DOMAIN_MAIL -> {
                    //打开邮件发送
                    sendEmail(activity, uri)
                }
                DOMAIN_COURSE_DETAIL -> {
                    //打开课程详情
                    val tenantIdStr = uri.getQueryParameter("tenantId")
                    val goodsId = uri.getQueryParameter("goodsId")
                    val onlineFlagStr = uri.getQueryParameter("onlineFlag")
                    val subOrderIdStr = uri.getQueryParameter("subOrderId")

                    var onlineFlag = 0
                    if (!TextUtils.isEmpty(onlineFlagStr)) {
                        try {
                            onlineFlag = onlineFlagStr?.toInt() ?: 0
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                    var subOrderId: Long = 0
                    if (!TextUtils.isEmpty(subOrderIdStr)) {
                        try {
                            subOrderId = subOrderIdStr?.toLong() ?: 0
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                    var tenantId: Long? = null
                    if (!TextUtils.isEmpty(subOrderIdStr)) {
                        try {
                            tenantId = tenantIdStr?.toLong() ?: 0
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
//                    NavigationUtils.goVideoDetailActivity(activity,tenantId,goodsId,onlineFlag,subOrderId)
                }
                DOMAIN_ORDER_DETAIL -> {
                    //打开订单详情
                    val orderNo = uri.getQueryParameter("orderNo")
//                    NavigationUtils.goOrderDetailActivity(activity,orderNo,"")
                }
                DOMAIN_AFTER_SALE_DETAIL -> {
                    //打开售后详情
                    val subOrderIdStr = uri.getQueryParameter("subOrderId")
                    val tenantIdStr = uri.getQueryParameter("tenantId")
                    var subOrderId: Long = 0
                    if (!TextUtils.isEmpty(subOrderIdStr)) {
                        try {
                            subOrderId = subOrderIdStr?.toLong() ?: 0
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                    var tenantId: Long? = null
                    if (!TextUtils.isEmpty(tenantIdStr)) {
                        try {
                            tenantId = tenantIdStr?.toLong() ?: 0
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
//                    NavigationUtils.goRefundActivity(activity,subOrderId,tenantId)
                }
                DOMAIN_TEACHER_DETAIL -> {
                    val lecturerId = uri.getQueryParameter("lecturerId")
                    val tenantIdStr = uri.getQueryParameter("tenantId")
                    var tenantId: Long? = null
                    if (!TextUtils.isEmpty(tenantIdStr)) {
                        try {
                            tenantId = tenantIdStr?.toLong() ?: 0
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
//                    NavigationUtils.goTeacherDetailActivity(activity,lecturerId,tenantId)
                }
                DOMAIN_TOPIC_INVALID -> {
                    val title = uri.getQueryParameter("title")
//                    NavigationUtils.goTopicInvalidActivity(activity,title)
                }
                DOMAIN_COURSE_CATEGORY -> {
                    val categoryId = uri.getQueryParameter("categoryId")
                    val tenantIdStr = uri.getQueryParameter("tenantId")
                    var tenantId: Long? = null
                    if (!TextUtils.isEmpty(tenantIdStr)) {
                        try {
                            tenantId = tenantIdStr?.toLong() ?: 0
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
//                    NavigationUtils.goCourseActivity(activity,categoryId,tenantId)
                }
                HOT_LIVE -> {

                    val rId = uri.getQueryParameter("rId")
                    val shopId = uri.getQueryParameter("shopId")
                    val tId = uri.getQueryParameter("tId")

                    //如果已经打开关闭VideoWebActivity
//                    SplashActivity.intentStart(activity,null,url.toString())
                }
                PAY_ORDER -> {
                    val orderNo = uri.getQueryParameter("orderNo")
                    NavigationUtils.goPayOrderActivity(activity,orderNo)
                }
                else -> {
                    NavigationUtils.goHomeActivity(activity,0)
                }
            }
        } catch (e: UnsupportedOperationException) {
            e.printStackTrace()
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }
    }

    /**
     * 从App内部点击进入
     *
     * @param uri
     */

    private fun schemaWeb(context: Context, uri: Uri) {
        val title = uri.getQueryParameter("title")
        val url = uri.getQueryParameter("url")
        Loger.e(TAG, "schemaWeb()-url = $url")
        if (TextUtils.isEmpty(url)) return
//        WebActivity.intentStart(context, title, url)
    }

    fun sendEmail (context: Context, uri: Uri) {
        val gmail = uri.getQueryParameter("gmail")
        // 必须明确使用mailto前缀来修饰邮件地址
        val uri = Uri.parse(String.format(Constant.MAIL_TO,gmail))
        val intent = Intent(Intent.ACTION_SENDTO, uri)
        context.startActivity(Intent.createChooser(intent, context.getString(R.string.mail_to_me_tip)))
    }
}