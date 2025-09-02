package com.victor.module.me.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.victor.lib.common.app.App
import com.victor.lib.common.base.BaseActivity
import com.victor.lib.common.util.PhoneUtil
import com.victor.lib.common.view.dialog.LoadingDialog
import com.victor.module.me.databinding.ActivityCancelAccountBinding
import com.victor.module.me.R

class CancelAccountActivity : BaseActivity<ActivityCancelAccountBinding>(ActivityCancelAccountBinding::inflate),View.OnClickListener {

    companion object {
        fun intentStart (activity: AppCompatActivity) {
            var intent = Intent(activity, CancelAccountActivity::class.java)
            activity.startActivity(intent)
        }
    }

    var mLoadingDialog: LoadingDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
        initData()
    }

    fun initialize () {
        subscribeEvent()

        mLoadingDialog = LoadingDialog(this)

        binding.mIvBack.setOnClickListener(this)
        binding.mTvThinkAgain.setOnClickListener(this)
        binding.mTvCancelAccount.setOnClickListener(this)
    }

    fun initData() {
        var phone = PhoneUtil.blurPhone(App.get().getUserInfo()?.phone) ?: ""
        binding.mTvPhone.text = "您将要注销的账号为$phone"
    }

    fun subscribeEvent () {
//        LiveDataBus.withMulti(MeActions.CANCEL_ACCOUNT_SUCCESS,javaClass.simpleName)
//            .observeForever(this, Observer {
//                finish()
//            })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.mIvBack -> {
                finish()
            }
            R.id.mTvThinkAgain -> {
                finish()
            }
            R.id.mTvCancelAccount -> {
                AccountCancelVerificationActivity.intentStart(this)
            }
        }
    }

}