package com.victor.module.home.view.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.victor.lib.common.base.BaseFragment
import com.victor.lib.common.util.ResUtils
import com.victor.lib.common.view.adapter.TabPagerAdapter
import com.victor.module.home.R
import com.victor.module.home.databinding.FragmentHomeHotBinding
import com.victor.module.home.view.data.HotType

class HomeHotFragment : BaseFragment<FragmentHomeHotBinding>(FragmentHomeHotBinding::inflate) {

    companion object {
        fun newInstance(): HomeHotFragment {
            return newInstance(0)
        }
        fun newInstance(id: Int): HomeHotFragment {
            val fragment = HomeHotFragment()
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
        pagerTitles = ResUtils.getStringArrayRes(R.array.home_hot_tab_titles)
        fragmentList.clear()
        fragmentList.add(HomeHotContentFragment.newInstance(HotType.RECOMMEND))
        fragmentList.add(HomeHotContentFragment.newInstance(HotType.PLAY))
        fragmentList.add(HomeHotContentFragment.newInstance(HotType.NEW))
        fragmentList.add(HomeHotContentFragment.newInstance(HotType.SEARCH))

        mTabPagerAdapter = TabPagerAdapter(childFragmentManager)
        mTabPagerAdapter?.titles = pagerTitles
        mTabPagerAdapter?.frags = fragmentList
        binding.mVpDetail.adapter = mTabPagerAdapter
        binding.mTabDetail.setupWithViewPager(binding.mVpDetail)
    }

    private fun initData() {
    }
}