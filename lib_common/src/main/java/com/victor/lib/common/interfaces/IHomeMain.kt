package com.victor.lib.common.interfaces

import com.google.android.material.bottomnavigation.BottomNavigationView
import com.victor.lib.coremodel.data.local.vm.DramaVM

interface IHomeMain {
    fun getDramaVM(): DramaVM
    fun getBottomNavBar(): BottomNavigationView
}