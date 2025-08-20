package com.victor.module.theater.view.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.victor.lib.common.base.BaseFragment
import com.victor.lib.common.util.ResUtils
import com.victor.lib.common.view.adapter.TabPagerAdapter
import com.victor.module.theater.R
import com.victor.module.theater.data.HotType
import com.victor.module.theater.databinding.FragmentTheaterHotBinding

class TheaterHotFragment : BaseFragment<FragmentTheaterHotBinding>(FragmentTheaterHotBinding::inflate) {

    companion object {
        fun newInstance(): TheaterHotFragment {
            return newInstance(0)
        }
        fun newInstance(id: Int): TheaterHotFragment {
            val fragment = TheaterHotFragment()
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
        pagerTitles = ResUtils.getStringArrayRes(R.array.theater_hot_tab_titles)
        fragmentList.clear()
        fragmentList.add(TheaterHotContentFragment.newInstance(HotType.RECOMMEND))
        fragmentList.add(TheaterHotContentFragment.newInstance(HotType.PLAY))
        fragmentList.add(TheaterHotContentFragment.newInstance(HotType.NEW))
        fragmentList.add(TheaterHotContentFragment.newInstance(HotType.SEARCH))

        mTabPagerAdapter = TabPagerAdapter(childFragmentManager)
        mTabPagerAdapter?.titles = pagerTitles
        mTabPagerAdapter?.frags = fragmentList
        binding.mVpDetail.adapter = mTabPagerAdapter
        binding.mTabDetail.setupWithViewPager(binding.mVpDetail)
    }

    private fun initData() {
    }
}