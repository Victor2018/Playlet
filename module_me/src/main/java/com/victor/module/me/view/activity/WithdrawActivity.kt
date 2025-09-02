package com.victor.module.me.view.activity

import android.os.Bundle
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.victor.lib.common.base.BaseActivity
import com.victor.module.me.databinding.ActivityWithdrawBinding

class WithdrawActivity: BaseActivity<ActivityWithdrawBinding>(ActivityWithdrawBinding::inflate),OnOffsetChangedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        statusBarTextColorBlack = false
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() {
        binding.appbar.addOnOffsetChangedListener(this)
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        binding.mSrlRefresh.isEnabled = verticalOffset >= 0
    }

}