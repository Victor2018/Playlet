package com.victor.module.me.view.activity

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.victor.lib.common.base.NRouterPath
import com.victor.lib.common.base.BaseActivity
import com.victor.module.me.R
import com.victor.module.me.databinding.ActivityWithdrawBinding
import com.victor.neuro.router.annotation.Route

@Route(path = NRouterPath.WithdrawAct)
class WithdrawActivity: BaseActivity<ActivityWithdrawBinding>(ActivityWithdrawBinding::inflate),OnOffsetChangedListener,OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        statusBarTextColorBlack = false
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() {
        binding.appbar.addOnOffsetChangedListener(this)
        binding.mIvBack.setOnClickListener(this)
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        binding.mSrlRefresh.isEnabled = verticalOffset >= 0
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.mIvBack -> {
                finish()
            }
        }
    }

}