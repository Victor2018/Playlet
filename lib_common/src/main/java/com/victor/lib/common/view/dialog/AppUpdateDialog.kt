package com.victor.lib.common.view.dialog

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import com.victor.lib.common.R
import com.victor.lib.common.data.ProgressInfo
import com.victor.lib.common.databinding.DlgAppUpdateBinding
import com.victor.lib.common.interfaces.OnAppUpdateListener
import com.victor.lib.common.interfaces.OnDownloadProgressListener
import com.victor.lib.common.util.InstallApkUtil
import com.victor.lib.common.util.Loger
import com.victor.lib.common.util.ResUtils
import com.victor.lib.common.util.ScreenUtils
import com.victor.lib.common.util.SpannableUtil
import com.victor.lib.common.util.ToastUtils
import com.victor.lib.coremodel.data.remote.entity.bean.LatestVersionData
import java.io.File

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: AppUpdateDialog
 * Author: Victor
 * Date: 2022/4/19 18:01
 * Description: 
 * -----------------------------------------------------------------
 */

class AppUpdateDialog(context: Context) : AbsDialog<DlgAppUpdateBinding>(context,DlgAppUpdateBinding::inflate), View.OnClickListener,
    DialogInterface.OnKeyListener, OnDownloadProgressListener {

    val TAG = "AppUpdateDialog"

    var mOnAppUpdateListener: OnAppUpdateListener? = null
    var mLatestVersionData: LatestVersionData? = null

    var mExitTime: Long = 0
    var mProgressInfo: ProgressInfo? = null

    override fun handleWindow(window: Window) {
        window.setGravity(Gravity.CENTER)
    }

    override fun handleLayoutParams(wlp: WindowManager.LayoutParams?) {
        wlp?.width = (ScreenUtils.getWidth(context) * 0.75).toInt()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
        initData()
    }

    fun initialize() {
        setCanceledOnTouchOutside(false)
        binding.mIvClose.setOnClickListener(this)
        binding.mTvUpdateNow.setOnClickListener(this)

        setOnKeyListener(this)
    }

    fun initData() {
        var findNewVersion = "发现新版本V${mLatestVersionData?.version}"
        var version = "V${mLatestVersionData?.version}"
        SpannableUtil.setSpannableColor(binding.mTvFindNewVersion,
            ResUtils.getColorRes(R.color.color_EB4F3A),findNewVersion,version)

        binding.mTvUpdateContent.text = mLatestVersionData?.content

        var updateSetting = mLatestVersionData?.updateSetting ?: 0
        if (updateSetting == 1) {
            binding.mIvClose.visibility = View.GONE
        } else {
            binding.mIvClose.visibility = View.VISIBLE
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.mIvClose -> {
                mOnAppUpdateListener?.OnAppUpdate(null,true)
                dismiss()
            }
            R.id.mTvUpdateNow -> {
                if (mProgressInfo != null) {
                    install(true, mProgressInfo?.getFile()!!)
                    return
                }
                download()
            }
        }
    }

    fun install(install: Boolean, file: File?) {
        Loger.e(TAG, "install()......")
        binding.mTvUpdateNow.text = "安装"
        binding.mTvUpdateNow.visibility = View.VISIBLE
        binding.mPbDownloadProgress.visibility = View.GONE
        if (install) {
            InstallApkUtil.install(context, file!!)
        }
    }

    fun download() {
        binding.mTvUpdateNow.visibility = View.INVISIBLE
        binding.mPbDownloadProgress.visibility = View.VISIBLE

//        DownloadModule.instance.downloadFile(context, mLatestVersionData, this)
    }

    override fun onKey(dialog: DialogInterface?, keyCode: Int, event: KeyEvent?): Boolean {
        if (event?.action == KeyEvent.ACTION_DOWN) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                var updateSetting = mLatestVersionData?.updateSetting ?: 0
                if (updateSetting == 1) {
                    if (System.currentTimeMillis() - mExitTime < 2000) {
                        android.os.Process.killProcess(android.os.Process.myPid())
                        System.exit(0)
                    } else {
                        mExitTime = System.currentTimeMillis()
                        ToastUtils.show("再按一次退出")
                    }
                    return true
                }
            }
        }

        return false
    }

    override fun OnDownloadProgress(data: ProgressInfo?) {
        Loger.e(TAG, "OnDownloadProgress-percent = " + data?.percent())
        binding.mPbDownloadProgress?.progress = data?.percent()?.toInt() ?: 0
    }

    override fun OnDownloadCompleted(data: ProgressInfo?) {
        /*if (DownloadModule.instance.isFileExists(data)) {
            mProgressInfo = data
            install(true, data?.getFile()!!)
        }*/
    }

    override fun OnError(error: String) {
        ToastUtils.show("下载失败：$error")
        binding.mTvUpdateNow.text = "下载失败点击重试"
        binding.mTvUpdateNow.visibility = View.VISIBLE
        binding.mPbDownloadProgress.visibility = View.GONE
    }

}