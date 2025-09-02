package com.victor.module.me.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import com.victor.lib.common.base.BaseActivity
import com.victor.lib.common.interfaces.OnDialogOkCancelClickListener
import com.victor.lib.common.util.SharedPreferencesUtils
import com.victor.module.me.databinding.ActivityPrivacySettingBinding
import com.victor.module.me.view.dialog.RecommendCloseTipDialog
import com.victor.module.me.R

class PrivacySettingActivity : BaseActivity<ActivityPrivacySettingBinding>
    (ActivityPrivacySettingBinding::inflate),View.OnClickListener,CompoundButton.OnCheckedChangeListener {

    companion object {
        fun intentStart (activity: AppCompatActivity) {
            var intent = Intent(activity, PrivacySettingActivity::class.java)
            activity.startActivity(intent)
        }
    }

    var mRecommendCloseTipDialog: RecommendCloseTipDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
    }

    override fun onResume() {
        super.onResume()
        initData()
    }

    fun initialize () {
        subscribeUi()

        binding.mIvBack.setOnClickListener(this)
        binding.mTvRecommendToggle.setOnClickListener(this)

        binding.mToggleRecommend.setOnCheckedChangeListener(this)
    }

    fun initData() {
        binding.mToggleRecommend.isChecked = SharedPreferencesUtils.isRecommendOpen
    }

    fun subscribeUi () {
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.mIvBack -> {
                finish()
            }
            R.id.mTvRecommendToggle -> {
                if (binding.mToggleRecommend.isChecked) {
                    showRecommendCloseTipDialog()
                } else {
                    binding.mToggleRecommend.isChecked = true
                }
            }
        }
    }

    override fun onCheckedChanged(cb: CompoundButton?, isChecked: Boolean) {
        SharedPreferencesUtils.isRecommendOpen = isChecked
    }

    fun showRecommendCloseTipDialog() {
        if (mRecommendCloseTipDialog == null) {
            mRecommendCloseTipDialog = RecommendCloseTipDialog(this)
            mRecommendCloseTipDialog?.mOnDialogOkCancelClickListener = object:
                OnDialogOkCancelClickListener {
                override fun OnDialogOkClick() {
                    binding.mToggleRecommend.isChecked = false
                }

                override fun OnDialogCancelClick() {
                    binding.mToggleRecommend.isChecked = true
                }

            }
        }
        mRecommendCloseTipDialog?.show()
    }

}