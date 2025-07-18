package com.victor.playlet

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.activity.viewModels
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.victor.lib.common.base.BaseActivity
import com.victor.lib.coremodel.util.InjectorUtils
import com.victor.lib.coremodel.vm.GankGirlVM
import com.victor.playlet.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {


    private val gankGirlVm by viewModels<GankGirlVM> {
        InjectorUtils.provideGankGirlVm(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navView.setupWithNavController(navController)
    }

}