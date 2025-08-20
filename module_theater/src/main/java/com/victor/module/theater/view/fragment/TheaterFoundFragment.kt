package com.victor.module.theater.view.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.victor.lib.common.base.BaseFragment
import com.victor.lib.common.util.ResUtils
import com.victor.lib.common.view.adapter.TabPagerAdapter
import com.victor.module.theater.R
import com.victor.module.theater.databinding.FragmentTheaterFoundBinding

class TheaterFoundFragment : BaseFragment<FragmentTheaterFoundBinding>(FragmentTheaterFoundBinding::inflate) {

    companion object {
        fun newInstance(): TheaterFoundFragment {
            return newInstance(0)
        }
        fun newInstance(id: Int): TheaterFoundFragment {
            val fragment = TheaterFoundFragment()
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
        pagerTitles = ResUtils.getStringArrayRes(R.array.theater_found_tab_titles)
        fragmentList.clear()

        var tabCount = pagerTitles?.size ?: 0
        for (i in 0 until tabCount) {
            fragmentList.add(TheaterFoundContentFragment.newInstance())
        }

        mTabPagerAdapter = TabPagerAdapter(childFragmentManager)
        mTabPagerAdapter?.titles = pagerTitles
        mTabPagerAdapter?.frags = fragmentList
        binding.mVpDetail.adapter = mTabPagerAdapter
        binding.mTabDetail.setupWithViewPager(binding.mVpDetail)
    }

    private fun initData() {
    }
}