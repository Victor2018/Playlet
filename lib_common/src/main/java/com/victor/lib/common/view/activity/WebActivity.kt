package com.victor.lib.common.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.victor.lib.common.R
import com.victor.lib.common.base.BaseActivity
import com.victor.lib.common.databinding.ActivityWebBinding
import com.victor.lib.common.util.Loger

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: WebActivity
 * Author: Victor
 * Date: 2022/3/1 18:28
 * Description: 
 * -----------------------------------------------------------------
 */

class WebActivity: BaseActivity<ActivityWebBinding>(ActivityWebBinding::inflate), View.OnClickListener {
    var url: String? = null
    companion object {
        val WEB_TITLE_KEY = "WEB_TITLE_KEY"
        val WEB_URL_KEY = "WEB_URL_KEY"
        val WEB_VIEW_BLACK_KEY = "WEB_VIEW_BLACK_KEY"

        fun intentStart (activity: Context?, title: String?, url: String?) {
            Loger.e("WebActivity", "intentStart()......title = $title")
            Loger.e("WebActivity", "intentStart()......url = $url")
            var intent = Intent(activity, WebActivity::class.java)
            intent.putExtra(WEB_TITLE_KEY, title)
            intent.putExtra(WEB_URL_KEY, url)
            activity?.startActivity(intent)
        }
        fun intentStart (activity: AppCompatActivity, title: String?, url: String?, isBlackBackground: Boolean) {
            var intent = Intent(activity, WebActivity::class.java)
            intent.putExtra(WEB_TITLE_KEY, title)
            intent.putExtra(WEB_URL_KEY, url)
            intent.putExtra(WEB_VIEW_BLACK_KEY, isBlackBackground)
            activity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
        initData()
    }

    fun initialize () {
        binding.mIvBack.setOnClickListener(this)
        binding.mTvClose.setOnClickListener(this)
    }

    fun initData (){
        val title = intent.getStringExtra(WEB_TITLE_KEY)
        url = intent.getStringExtra(WEB_URL_KEY)
        val isBlack = intent.getBooleanExtra(WEB_VIEW_BLACK_KEY, false)

        Loger.e(TAG,"title = $title")
        Loger.e(TAG,"url = $url")

        binding.mTvTitle.text = title
        binding.mPWeb.setWebViewBackgroundColor(isBlack)
        binding.mPWeb.loadUrl(url ?: "")
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.mIvBack -> {
                if (binding.mPWeb.canGoBack()) {
                    return
                }
                finish()
            }
            R.id.mTvClose -> {
                finish()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.mPWeb.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.mPWeb.onPause()
    }

    override fun onDestroy() {
        binding.mPWeb.onDestory()
        super.onDestroy()
    }
}