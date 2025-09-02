package com.victor.module.me.view.activity

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import com.victor.lib.common.base.BaseActivity
import com.victor.lib.common.util.CacheCleanUtils
import com.victor.lib.common.util.ToastUtils
import com.victor.module.me.R
import com.victor.module.me.databinding.ActivitySettingsBinding

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: SettingsActivity
 * Author: Victor
 * Date: 2025/8/27 14:00
 * Description: 
 * -----------------------------------------------------------------
 */
class SettingsActivity: BaseActivity<ActivitySettingsBinding>(ActivitySettingsBinding::inflate),OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initData()
    }

    private fun initView() {
        binding.mIvBack.setOnClickListener(this)
        binding.mTvAccountSet.setOnClickListener(this)
        binding.mTvYouthMode.setOnClickListener(this)
        binding.mTvPrivacySetting.setOnClickListener(this)
        binding.mTvClearCache.setOnClickListener(this)
        binding.mTvAboutUs.setOnClickListener(this)
        binding.mTvLogout.setOnClickListener(this)
    }

    fun initData() {
        try {
            var text: String? = CacheCleanUtils.getTotalCacheSize(this)
            binding.mTvCache.text = text
        } catch (e: Exception) {
            e.printStackTrace()
            binding.mTvCache.text = "0KB"
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.mIvBack -> {
                finish()
            }
            R.id.mTvAccountSet -> {
                AccountSettingActivity.intentStart(this)
            }
            R.id.mTvYouthMode -> {
            }
            R.id.mTvPrivacySetting -> {
                PrivacySettingActivity.intentStart(this)
            }
            R.id.mTvClearCache -> {
                CacheCleanUtils.clearAllCache(this)
                binding.mTvCache.text = "0KB"
                ToastUtils.show("缓存已清除")
            }
            R.id.mTvAboutUs -> {
                AboutUsActivity.intentStart(this)
            }
            R.id.mTvLogout -> {
            }
        }
    }
}