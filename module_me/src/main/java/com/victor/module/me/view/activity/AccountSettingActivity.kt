package com.victor.module.me.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.victor.lib.common.app.App
import com.victor.lib.common.base.BaseActivity
import com.victor.lib.common.interfaces.OnDialogOkCancelClickListener
import com.victor.lib.common.util.PhoneUtil
import com.victor.lib.common.view.dialog.LoadingDialog
import com.victor.module.me.R
import com.victor.module.me.databinding.ActivityAccountSettingBinding
import com.victor.module.me.interfaces.OnModifyBindPhoneListener
import com.victor.module.me.view.dialog.CancelAccountTipDialog
import com.victor.module.me.view.dialog.ModifyBindPhoneTipDialog

class AccountSettingActivity : BaseActivity<ActivityAccountSettingBinding>(ActivityAccountSettingBinding::inflate),View.OnClickListener {

    companion object {
        fun intentStart (activity: AppCompatActivity) {
            var intent = Intent(activity, AccountSettingActivity::class.java)
            activity.startActivity(intent)
        }
    }

//    private val authVM: AuthVM by viewModels {
//        InjectorUtils.provideAuthVMFactory(this)
//    }

    var mLoadingDialog: LoadingDialog? = null
    var mCancelAccountTipDialog: CancelAccountTipDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
        initData()
    }

    fun initialize () {
        mLoadingDialog = LoadingDialog(this)

        subscribeUi()
        subscribeEvent()

        binding.mIvBack.setOnClickListener(this)
        binding.mTvPhone.setOnClickListener(this)
        binding.mTvCancelAccount.setOnClickListener(this)
    }

    fun initData() {
        binding.mTvPhone.text = PhoneUtil.blurPhone(App.get().getUserInfo()?.phone)
    }

    fun subscribeUi() {
        /*authVM.phoneModifyCountData.observe(this, Observer {
            mLoadingDialog?.dismiss()
            when (it) {
                is HttpResult.Success -> {
                    showPhoneModifyCountData(it.value)
                }
                is HttpResult.Error -> {
                    ToastUtils.show(it.message)
                }
            }
        })*/
    }

    fun subscribeEvent () {
        /*LiveDataBus.withMulti(MeActions.CANCEL_ACCOUNT_SUCCESS,javaClass.simpleName)
            .observeForever(this, Observer {
                finish()
            })*/
    }

    fun sendPhoneModifyCountRequest() {
        mLoadingDialog?.show()
        val phone = App.get().getLoginData()?.phone
//        authVM.fetchPhoneModifyCount(phone)
    }

   /* fun showPhoneModifyCountData(data: BaseReq<Int>) {
        EditPhoneActivity.intentStart(this@AccountSettingActivity,data.data ?: 0)
    }*/

    fun showEditBindPhoneTipDlg () {
        val mModifyBindPhoneTipDialog = ModifyBindPhoneTipDialog(this)
        mModifyBindPhoneTipDialog.phone = App.get().getUserInfo()?.phone
        mModifyBindPhoneTipDialog.mOnEditBindPhoneListener = object: OnModifyBindPhoneListener {
            override fun OnModifyBindPhone() {
                sendPhoneModifyCountRequest()
            }
        }
        mModifyBindPhoneTipDialog.show()
    }

    fun showCancelAccountTipDlg () {
        if (mCancelAccountTipDialog == null) {
            mCancelAccountTipDialog = CancelAccountTipDialog(this)
            mCancelAccountTipDialog?.mOnDialogOkCancelClickListener = object:
                OnDialogOkCancelClickListener {
                override fun OnDialogOkClick() {
                    CancelAccountActivity.intentStart(this@AccountSettingActivity)
                }

                override fun OnDialogCancelClick() {
                }
            }
        }
        mCancelAccountTipDialog?.show()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.mIvBack -> {
                finish()
            }
            R.id.mTvPhone -> {
                showEditBindPhoneTipDlg()
            }
            R.id.mTvCancelAccount -> {
                showCancelAccountTipDlg()
            }
        }
    }

}