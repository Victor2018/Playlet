package com.victor.lib.common.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: ViewPager2Adapter
 * Author: Victor
 * Date: 2022/6/15 11:00
 * Description: 
 * -----------------------------------------------------------------
 */

class ViewPager2Adapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    var fragmetList: List<Fragment>? = null

    override fun getItemCount() = fragmetList?.size ?: 0

    override fun createFragment(position: Int): Fragment {
        return fragmetList?.get(position) ?: Fragment()
    }

}