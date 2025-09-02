package com.victor.module.me.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.victor.lib.common.app.App
import com.victor.lib.common.base.BaseActivity
import com.victor.lib.common.interfaces.OnCountDownTimerListener
import com.victor.lib.common.module.SmsCountDownTimer
import com.victor.lib.common.util.KeyBoardUtil
import com.victor.lib.common.view.dialog.LoadingDialog
import com.victor.lib.common.view.widget.MNPasswordEditText
import com.victor.library.bus.LiveDataBus
import com.victor.module.me.R
import com.victor.module.me.databinding.ActivityAccountCancelVerficationBinding

class AccountCancelVerificationActivity : BaseActivity<ActivityAccountCancelVerficationBinding>
    (ActivityAccountCancelVerficationBinding::inflate),View.OnClickListener,
    MNPasswordEditText.OnTextChangeListener, OnCountDownTimerListener {

    companion object {
        fun intentStart (activity: AppCompatActivity) {
            var intent = Intent(activity, AccountCancelVerificationActivity::class.java)
            activity.startActivity(intent)
        }
    }

//    private val userVM: UserVM by viewModels {
//        InjectorUtils.provideUserVMFactory(this)
//    }

    var mLoadingDialog: LoadingDialog? = null
    var mSmsCountDownTimer: SmsCountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
        initData()
    }

    fun initialize () {
        subscribeUi()

        mLoadingDialog = LoadingDialog(this)
        mSmsCountDownTimer = SmsCountDownTimer(60 * 1000, 1000, this)

        binding.mIvBack.setOnClickListener(this)
        binding.mTvSendSms.setOnClickListener(this)

        binding.mEtCode.setOnTextChangeListener(this)

        requestPasswordViewFocus()
    }

    fun initData() {
        var phone = App.get().getUserInfo()?.phone ?: ""
        binding.mTvPhone.text = "短信已发送至+86 $phone"

        sendSmsRequest()
    }

    fun subscribeUi() {
        /*userVM.cancelAccountCodeData.observe(this, Observer {
            mLoadingDialog?.dismiss()
            when (it) {
                is HttpResult.Success -> {
                    ToastUtils.show("验证码发送成功")
                    var isRunning = mSmsCountDownTimer?.isRunning ?: false
                    if (isRunning) return@Observer
                    mSmsCountDownTimer?.start()
                }
                is HttpResult.Error -> {
                    ToastUtils.show(it.message)
                }
            }
        })

        userVM.cancelAccountData.observe(this, Observer {
            mLoadingDialog?.dismiss()
            when (it) {
                is HttpResult.Success -> {
                    LiveDataBus.sendMulti(MeActions.CANCEL_ACCOUNT_SUCCESS)
                    ToastUtils.show("账户注销成功")
                    App.get().resetLoginData()
                    finish()
                }
                is HttpResult.Error -> {
                    ToastUtils.show(it.message)
                    requestPasswordViewFocus()
                }
            }
        })*/
    }

    fun requestPasswordViewFocus () {
        //设置可获得焦点
        binding.mEtCode.isFocusable = true
        binding.mEtCode.isFocusableInTouchMode = true
        //请求获得焦点
        binding.mEtCode.requestFocus()

        binding.mEtCode.postDelayed(Runnable {
            KeyBoardUtil.showKeyBoard(this,binding.mEtCode)
        },300)
    }

    fun clearPasswordViewFocus () {
        binding.mEtCode.clearFocus()
        binding.mEtCode.isFocusable = false
        binding.mEtCode.isFocusableInTouchMode = false

        KeyBoardUtil.hideKeyBoard(this,binding.mEtCode)
    }

    fun sendSmsRequest() {
        /*if (!App.get().hasLogin()) {
            LiveDataBus.send(LoginActions.GO_ONE_KEY_LOGIN)
            return
        }

        mLoadingDialog?.show()
        userVM.getCancelAccountCode()*/
    }

    fun sendCancelAccountRequest (code: String?) {
        /*if (!App.get().hasLogin()) {
            LiveDataBus.send(LoginActions.GO_ONE_KEY_LOGIN)
            return
        }

        mLoadingDialog?.show()

        val body = CancelAccountParm()
        body.code = code

        userVM.cancelAccount(body)*/
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.mIvBack -> {
                finish()
            }
            R.id.mTvSendSms -> {
                sendSmsRequest()
            }
        }
    }

    override fun onTick(millisUntilFinished: Long) {
        //防止计时过程中重复点击
        if (isFinishing) return
        binding.mTvSendSms.isEnabled = false
        binding.mTvSendSms.text = "重新发送(${millisUntilFinished / 1000}s)"
        binding.mTvSendSms.alpha = 0.6f
    }

    override fun onFinish() {
        if (isFinishing) return
        binding.mTvSendSms.isEnabled = true
        binding.mTvSendSms.text = ""
        binding.mTvSendSms.text = "重新发送"
        binding.mTvSendSms.alpha = 1.0f
    }

    override fun onTextChange(text: String?, isComplete: Boolean) {
        if (isComplete) {
            clearPasswordViewFocus()
            sendCancelAccountRequest(text)
        }
    }

}