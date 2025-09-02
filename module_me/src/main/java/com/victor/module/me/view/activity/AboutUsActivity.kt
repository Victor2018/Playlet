package com.victor.module.me.view.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.victor.lib.common.base.BaseActivity
import com.victor.lib.common.interfaces.OnAppUpdateListener
import com.victor.lib.common.interfaces.OnDialogOkCancelClickListener
import com.victor.lib.common.util.DateUtil
import com.victor.lib.common.util.ToastUtils
import com.victor.lib.common.util.ViewUtils.hide
import com.victor.lib.common.util.ViewUtils.show
import com.victor.lib.common.view.activity.WebActivity
import com.victor.lib.common.view.dialog.AppUpdateDialog
import com.victor.lib.common.view.dialog.LoadingDialog
import com.victor.lib.common.view.dialog.UpdatePermissionGuideDialog
import com.victor.lib.coremodel.data.remote.api.HtmlApi
import com.victor.lib.coremodel.data.remote.entity.bean.LatestVersionData
import com.victor.lib.coremodel.util.AppConfig
import com.victor.lib.coremodel.util.AppUtil
import com.victor.module.me.R
import com.victor.module.me.databinding.ActivityAboutUsBinding

class AboutUsActivity : BaseActivity<ActivityAboutUsBinding>(ActivityAboutUsBinding::inflate),View.OnClickListener,
    OnAppUpdateListener {

    companion object {
        fun intentStart (activity: AppCompatActivity) {
            var intent = Intent(activity, AboutUsActivity::class.java)
            activity.startActivity(intent)
        }
    }

//    private val userVM: UserVM by viewModels {
//        InjectorUtils.provideUserVMFactory(this)
//    }

    var mLoadingDialog: LoadingDialog? = null
    var isClickUpdate = false
    var mLatestVersionData: LatestVersionData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
        initData()
    }

    fun initialize () {
        subscribeUi()

        mLoadingDialog = LoadingDialog(this)

        binding.mIvBack.setOnClickListener(this)
        binding.mTvUserServicesAgreement.setOnClickListener(this)
        binding.mTvAboutHok.setOnClickListener(this)
        binding.mTvPrivacyAgreement.setOnClickListener(this)
        binding.mTvUpgrade.setOnClickListener(this)
    }

    fun initData() {
        binding.mTvAppName.text = AppUtil.getAppName(this)

        var nowYear = DateUtil.getNowYear()
        binding.mTvCopyRight.text = "Copyright © 2021-$nowYear"

        var vName = "Version " + AppUtil.getAppVersionName(this)

        if (AppConfig.MODEL_DEBUG) {
//            vName += "_" + AppConfig.BUILD_NUMBER
            if (AppConfig.MODEL_DEV) {
                vName += "_dev"
            }
            if (AppConfig.MODEL_TEST) {
                vName += "_test"
            }
            if (AppConfig.MODEL_BETA) {
                vName += "_beta"
            }
        }
        binding.mTvAppVersion.text = vName

        sendCheckVersionRequest()
    }

    fun subscribeUi() {
        /*userVM.checkVersionData.observe(this, Observer {
            mLoadingDialog?.dismiss()
            when (it) {
                is HttpResult.Success -> {
                    showAppUpdateDlg(it.value.data)
                }
                is HttpResult.Error -> {
                    ToastUtils.show(it.message)
                }
            }
        })*/
    }

    fun sendCheckVersionRequest () {

//        mLoadingDialog?.show()

        val pkgName = AppUtil.getPackageName(this)
        var version = AppUtil.getAppVersionName(this)
//        userVM.checkVersion(pkgName,version)
    }

    fun showAppUpdateDlg (data: LatestVersionData?) {
        if (data == null) {
            if (isClickUpdate) {
                ToastUtils.show("当前已是最新版本")
            }
            binding.mViewUpdateTip.hide()
            return
        }

        mLatestVersionData = data

        binding.mViewUpdateTip.show()

        var appUpdateDialog = AppUpdateDialog(this)
        appUpdateDialog.mOnAppUpdateListener = this
        appUpdateDialog.mLatestVersionData = data
        appUpdateDialog.show()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.mIvBack -> {
                finish()
            }
            R.id.mTvUserServicesAgreement -> {
                WebActivity.intentStart(this,"用户服务协议", HtmlApi.USER_SERVICES_AGREEMENT)
            }
            R.id.mTvAboutHok -> {
                WebActivity.intentStart(this,"关于获课",HtmlApi.ABOUS_HOK)
            }
            R.id.mTvPrivacyAgreement -> {
                WebActivity.intentStart(this,"隐私协议",HtmlApi.PRIVACY_POLICY)
            }
            R.id.mTvUpgrade -> {
                isClickUpdate = true
                sendCheckVersionRequest()
            }
        }
    }

    override fun OnAppUpdate(error: String?,dimiss: Boolean) {
        if (!TextUtils.isEmpty(error)) {
            showUpdatePermissionGuideDlg()
        }
    }

    fun showUpdatePermissionGuideDlg () {
        var mUpdatePermissionGuideDialog = UpdatePermissionGuideDialog(this)
        mUpdatePermissionGuideDialog.mOnDialogOkCancelClickListener = object :
            OnDialogOkCancelClickListener {
            override fun OnDialogOkClick() {
//                requestRWPermission()
            }

            override fun OnDialogCancelClick() {
            }
        }
        mUpdatePermissionGuideDialog.show()
    }

}