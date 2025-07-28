package com.victor.module.me.view.fragment

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.material.appbar.AppBarLayout
import com.hok.lib.common.base.ARouterPath
import com.victor.lib.common.base.BaseFragment
import com.victor.lib.common.util.ResUtils
import com.victor.lib.common.view.adapter.TabPagerAdapter
import com.victor.module.me.R
import com.victor.module.me.databinding.FragmentMeBinding

@Route(path = ARouterPath.MeFgt)
class MeFragment : BaseFragment<FragmentMeBinding>(FragmentMeBinding::inflate),
    AppBarLayout.OnOffsetChangedListener,OnItemClickListener {

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
        pagerTitles = ResUtils.getStringArrayRes(R.array.me_tab_titles)
        fragmentList.clear()
        fragmentList.add(HistoryFragment.newInstance())
        fragmentList.add(FollowingFragment.newInstance())
        fragmentList.add(LikesFragment.newInstance())
        fragmentList.add(PurchasedFragment.newInstance())
        fragmentList.add(DowloadsFragment.newInstance())

        mTabPagerAdapter = TabPagerAdapter(childFragmentManager)
        mTabPagerAdapter?.titles = pagerTitles
        mTabPagerAdapter?.frags = fragmentList
        binding.mVpDetail.adapter = mTabPagerAdapter
        binding.mTabDetail.setupWithViewPager(binding.mVpDetail)

        binding.appbar.addOnOffsetChangedListener(this)
    }

    private fun initData() {
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        val totalScrollRange = appBarLayout?.totalScrollRange ?: 0
        if (verticalOffset == 0) {
            //展开状态
            binding.mIvBack.setImageResource(com.victor.lib.common.R.mipmap.ic_black_arrow_left)
            binding.mIvSetting.setImageResource(R.mipmap.ic_me_setting_black)
            binding.mTvTitle.setTextColor(ResUtils.getColorRes(com.victor.lib.common.R.color.color_333333))
        } else if (Math.abs(verticalOffset) >= totalScrollRange) {
            //折叠状态
            binding.mIvBack.setImageResource(com.victor.lib.common.R.mipmap.ic_back_white)
            binding.mIvSetting.setImageResource(R.mipmap.ic_me_setting_white)
            binding.mTvTitle.setTextColor(ResUtils.getColorRes(com.victor.lib.common.R.color.white))
        } else {
            //中间状态
        }
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
    }
}