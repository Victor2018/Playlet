package com.victor.module.me.view.fragment

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.material.appbar.AppBarLayout
import com.hok.lib.common.base.ARouterPath
import com.victor.lib.common.base.BaseActivity.Companion.intentStart
import com.victor.lib.common.base.BaseFragment
import com.victor.lib.common.util.ResUtils
import com.victor.lib.common.view.adapter.TabPagerAdapter
import com.victor.module.me.R
import com.victor.module.me.databinding.FragmentMeBinding
import com.victor.module.me.databinding.FragmentMeHeaderBinding
import com.victor.module.me.view.activity.SettingsActivity
import com.victor.module.me.view.activity.WithdrawActivity

@Route(path = ARouterPath.MeFgt)
class MeFragment : BaseFragment<FragmentMeBinding>(FragmentMeBinding::inflate),
    AppBarLayout.OnOffsetChangedListener,OnItemClickListener,OnClickListener {

    companion object {
        fun newInstance(): MeFragment {
            return newInstance(0)
        }
        fun newInstance(id: Int): MeFragment {
            val fragment = MeFragment()
            val bundle = Bundle()
            bundle.putInt(ID_KEY, id)
            fragment.setArguments(bundle)
            return fragment
        }
    }

    var mTabPagerAdapter: TabPagerAdapter? = null
    private var pagerTitles: Array<String>? = null
    private var fragmentList: ArrayList<Fragment> = ArrayList()

    private lateinit var headerBinding: FragmentMeHeaderBinding

    override fun handleBackEvent(): Boolean {
        return false
    }

    override fun freshFragData() {
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
    }

    private fun initView() {
        headerBinding = binding.mClFragMeHeader
        pagerTitles = ResUtils.getStringArrayRes(R.array.me_tab_titles)
        fragmentList.clear()
        fragmentList.add(HistoryFragment.newInstance())
        fragmentList.add(FollowingFragment.newInstance())
        fragmentList.add(LikesFragment.newInstance())
        fragmentList.add(PurchasedFragment.newInstance())

        mTabPagerAdapter = TabPagerAdapter(childFragmentManager)
        mTabPagerAdapter?.titles = pagerTitles
        mTabPagerAdapter?.frags = fragmentList
        binding.mVpDetail.adapter = mTabPagerAdapter
        binding.mTabDetail.setupWithViewPager(binding.mVpDetail)

        binding.appbar.addOnOffsetChangedListener(this)
        binding.mIvSetting.setOnClickListener(this)
        headerBinding.mTvWithdrawal.setOnClickListener(this)
    }

    private fun initData() {
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        val totalScrollRange = appBarLayout?.totalScrollRange ?: 0
        if (verticalOffset == 0) {
            //展开状态
            binding.mIvSetting.setImageResource(R.mipmap.ic_me_setting_black)
            binding.mTvTitle.setTextColor(ResUtils.getColorRes(com.victor.lib.common.R.color.color_333333))
        } else if (Math.abs(verticalOffset) >= totalScrollRange) {
            //折叠状态
            binding.mIvSetting.setImageResource(R.mipmap.ic_me_setting_white)
            binding.mTvTitle.setTextColor(ResUtils.getColorRes(com.victor.lib.common.R.color.white))
        } else {
            //中间状态
        }
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.mIvSetting -> {
                context?.intentStart(SettingsActivity::class.java)
            }
            R.id.mTvWithdrawal -> {
                context?.intentStart(WithdrawActivity::class.java)
            }
        }
    }
}