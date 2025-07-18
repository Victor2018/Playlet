package com.victor.lib.common.view.activity

import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.victor.lib.common.R
import com.victor.lib.common.app.App
import com.victor.lib.common.base.BaseActivity
import com.victor.lib.common.databinding.ActivitySplashBinding
import com.victor.lib.common.interfaces.OnCountDownTimerListener
import com.victor.lib.common.module.AppInitModule
import com.victor.lib.common.module.SmsCountDownTimer
import com.victor.lib.common.util.Constant
import com.victor.lib.common.util.Loger
import com.victor.lib.common.util.NavigationUtils
import com.victor.lib.common.util.SharedPreferencesUtils
import com.victor.lib.common.util.StatusBarUtil
import com.victor.lib.common.util.ViewUtils.hide
import com.victor.lib.common.util.ViewUtils.show
import com.victor.lib.common.view.adapter.SplashViewPagerAdapter
import com.victor.lib.common.view.widget.IndicatorView

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: SplashActivity
 * Author: Victor
 * Date: 2022/4/18 17:44
 * Description: 
 * -----------------------------------------------------------------
 */

class SplashActivity: BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate), View.OnClickListener, ViewPager.OnPageChangeListener,
    OnCountDownTimerListener {

//    private val homeVM by viewModels<HomeVM> {
//        InjectorUtils.provideHomeVMFactory(this)
//    }
    var guideRootView: View? = null
    var splashRootView: View? = null
    var mSplashViewPagerAdapter: SplashViewPagerAdapter? = null
    var mSmsCountDownTimer: SmsCountDownTimer? = null
    val countDownTime: Long = 4

    var pushData: String? = null
    var inviteUrl: String? = null


    companion object {
        fun  intentStart (activity: Activity, pushData: String?, inviteUrl: String?) {
            var intent = Intent(activity, SplashActivity::class.java)
            intent.putExtra(Constant.INTENT_DATA_KEY,pushData)
            intent.putExtra(Constant.INVITE_URL_KEY,inviteUrl)
            activity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData(intent)
    }

    override fun onResume() {
        super.onResume()
        StatusBarUtil.hideNavigationBar(this)
    }

    fun initialize (hasShowGuide: Boolean) {
        if (hasShowGuide) {
            if (splashRootView == null) {
                splashRootView = binding.stubSplash.inflate()
                splashRootView?.findViewById<TextView>(R.id.mTvSkip)?.setOnClickListener(this)
                splashRootView?.findViewById<TextView>(R.id.mIvSplashAd)?.setOnClickListener(this)
                splashRootView?.findViewById<TextView>(R.id.mTvSkip)?.setOnClickListener(this)
            }
        } else {
            mSplashViewPagerAdapter = SplashViewPagerAdapter(this)
            mSplashViewPagerAdapter?.mOnClickListener = this

            if (guideRootView == null) {
                guideRootView = binding.stubGuide.inflate()
                guideRootView?.findViewById<ViewPager>(R.id.mVpSplash)?.adapter = mSplashViewPagerAdapter
                guideRootView?.findViewById<ViewPager>(R.id.mVpSplash)?.addOnPageChangeListener(this)
                guideRootView?.findViewById<IndicatorView>(R.id.mSplashIndicator)?.setupWithViewPager(guideRootView?.findViewById<ViewPager>(R.id.mVpSplash))
                guideRootView?.findViewById<IndicatorView>(R.id.mSplashIndicator)?.setIndicatorTransformer(IndicatorView.TranslationIndicatorTransformer())
                guideRootView?.findViewById<TextView>(R.id.mTvGo)?.setOnClickListener(this)
            }

            val datas = ArrayList<Int>()
            var imgArray = resources.obtainTypedArray(R.array.splash_imgs)
            for (i in 0 until  imgArray.length()) {
                datas.add(imgArray.getResourceId(i,0))
            }
            mSplashViewPagerAdapter?.datas = datas
            mSplashViewPagerAdapter?.notifyDataSetChanged()
        }
    }

    fun initData (intent: Intent?) {
        subscribeUi()

        pushData = intent?.getStringExtra(Constant.INTENT_DATA_KEY)
        inviteUrl = intent?.getStringExtra(Constant.INVITE_URL_KEY)

        if (App.get().isMainActivityLaunched()) {
            toMain()
            return
        }

        var hasShowGuide = SharedPreferencesUtils.hasShowGuide
        initialize(hasShowGuide)

        var agreePrivacyPolicy = SharedPreferencesUtils.agreePrivacyPolicy
        if (agreePrivacyPolicy) {
            AppInitModule.initThirdSdk(App.get())
            if (hasShowGuide) {
                sendAdRequest()
            }
        } else {
            showPrivacyPolicyDlg()
        }
    }

    fun subscribeUi() {
//        homeVM.adData.observe(this, Observer {
//            when (it) {
//                is HttpResult.Success -> {
//                    showAdData(it.value)
//                }
//                is HttpResult.Error -> {
//                    if (it.code != 100402) {//广告位不存在
//                        ToastUtils.show(it.message)
//                    }
//                    toMain()
//                }
//            }
//        })
    }

    fun sendAdRequest () {
//        homeVM.fetchAD(null,1)
    }

//    fun showAdData(data: BaseRes<AdData>) {
//        mAdData = data.data
//        var imageUrl = data.data?.adInfoVos?.firstOrNull()?.url ?: ""
//
//        if (TextUtils.isEmpty(imageUrl)) {
//            toMain()
//            return
//        }
//
//        ImageUtils.instance.loadImage(this,splashRootView?.mIvSplashAd,imageUrl,
//            R.mipmap.img_placeholder_vertical)
//
//        startTimer()
//    }

    fun startTimer () {
        mSmsCountDownTimer?.cancel()
        mSmsCountDownTimer = SmsCountDownTimer(
            countDownTime * 1000, 1000, this)
        mSmsCountDownTimer?.start()
    }

    fun toMain () {
        Loger.e(TAG,"toMain()......")
        if (isFinishing) return

        if (App.get().isMainActivityLaunched()) {
            //如果主界面已经打开直接处理jpush跳转
            parseIntentAction()
        } else {
            NavigationUtils.goHomeActivity(this,0,pushData)
        }

        finish()
    }

    fun showPrivacyPolicyDlg () {
        /*var privacyPolicyDialog = PrivacyPolicyDialog(this)
        privacyPolicyDialog.mOnDialogOkCancelClickListener = object :
            OnDialogOkCancelClickListener {

            override fun OnDialogOkClick() {
                SharedPreferencesUtils.agreePrivacyPolicy = true
                SharedPreferencesUtils.hasShowGuide = true
                AppInitModule.initThirdSdk(App.get())
            }

            override fun OnDialogCancelClick() {
                finish()
            }
        }
        privacyPolicyDialog.show()*/
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.mTvGo, R.id.mTvSkip -> {
                toMain()
            }
            R.id.mIvSplashAd -> {
//                mAdInfo = mAdData?.adInfoVos?.firstOrNull()
                toMain()
//                UMengEventModule.report(this, HomeEvent.Event_OPEN_SCREEN_AD_CLICK)
            }
        }
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        when (position) {
            0 -> {
                guideRootView?.findViewById<TextView>(R.id.mTvGo)?.hide()
            }
            1 -> {
                guideRootView?.findViewById<TextView>(R.id.mTvGo)?.hide()
            }
            2 -> {
                guideRootView?.findViewById<TextView>(R.id.mTvGo)?.show()
                ObjectAnimator
                    .ofFloat(guideRootView?.findViewById<TextView>(R.id.mTvGo), "alpha", 0f, 1f)
                    .setDuration(1000)
                    .start()
            }
        }
    }

    override fun onTick(millisUntilFinished: Long) {
        guideRootView?.findViewById<TextView>(R.id.mTvSkip)?.text = "${millisUntilFinished / 1000} 跳过"
    }

    override fun onFinish() {
        toMain()
    }

    fun parseIntentAction() {
        Loger.e(TAG,"initPushData-pushData = $pushData")
        //处理极光推送跳转
        /*val parseIntentSuccess = JPushOpenHelper.parseIntentAction(this,pushData)
        Loger.e(TAG,"initPushData-parseIntentSuccess = $parseIntentSuccess")
        if (!parseIntentSuccess) {
            App.get().finishOtherThenSplashAndMainActivity()
            NavigationUtils.goHomeActivity(this,0,pushData)
        }*/
    }

    override fun onDestroy() {
        super.onDestroy()
        mSmsCountDownTimer?.onFinish()
        mSmsCountDownTimer = null
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        initData(intent)
    }
}