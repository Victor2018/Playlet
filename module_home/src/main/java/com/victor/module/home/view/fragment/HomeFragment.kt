package com.victor.module.home.view.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.hok.lib.common.base.ARouterPath
import com.victor.lib.common.base.BaseFragment
import com.victor.lib.common.util.ResUtils
import com.victor.lib.common.view.adapter.TabPagerAdapter
import com.victor.module.home.R
import com.victor.module.home.databinding.FragmentHomeBinding

@Route(path = ARouterPath.HomeFgt)
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    companion object {
        fun newInstance(): HomeFragment {
            return newInstance(0)
        }
        fun newInstance(id: Int): HomeFragment {
            val fragment = HomeFragment()
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
        pagerTitles = ResUtils.getStringArrayRes(R.array.home_tab_titles)
        fragmentList.clear()
        fragmentList.add(HotFragment.newInstance())
        fragmentList.add(PlayListFragment.newInstance())
        fragmentList.add(RecommendFragment.newInstance())

        mTabPagerAdapter = TabPagerAdapter(childFragmentManager)
        mTabPagerAdapter?.titles = pagerTitles
        mTabPagerAdapter?.frags = fragmentList
        binding.mVpDetail.adapter = mTabPagerAdapter
        binding.mTabDetail.setupWithViewPager(binding.mVpDetail)
//        binding.mVpDetail.currentItem = 2
    }

    private fun initData() {
    }
}