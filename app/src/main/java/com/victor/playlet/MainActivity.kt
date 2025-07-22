package com.victor.playlet

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.alibaba.android.arouter.launcher.ARouter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.hok.lib.common.base.ARouterPath
import com.victor.lib.common.base.BaseActivity
import com.victor.lib.common.util.Loger
import com.victor.lib.common.util.NetworkUtils
import com.victor.lib.common.util.ResUtils
import com.victor.lib.common.util.ViewUtils.hide
import com.victor.lib.common.util.ViewUtils.show
import com.victor.lib.common.view.adapter.ViewPagerAdapter
import com.victor.lib.coremodel.util.InjectorUtils
import com.victor.lib.coremodel.vm.GankGirlVM
import com.victor.playlet.databinding.ActivityMainBinding


class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate),
    OnClickListener, NavigationBarView.OnItemSelectedListener,OnPageChangeListener {


    private val gankGirlVm by viewModels<GankGirlVM> {
        InjectorUtils.provideGankGirlVm(this)
    }

    var mViewPagerAdapter: ViewPagerAdapter? = null
    var fragmentList: MutableList<Fragment> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initData()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    private fun initView() {
        handleNetworkChanges()

        mViewPagerAdapter = ViewPagerAdapter(supportFragmentManager)

        val homeFrag = ARouter.getInstance().build(ARouterPath.HomeFgt).navigation() as Fragment
        val theaterFrag = ARouter.getInstance().build(ARouterPath.TheaterFgt).navigation() as Fragment
        val welfareFrag = ARouter.getInstance().build(ARouterPath.WelfareFgt).navigation() as Fragment
        val meFrag = ARouter.getInstance().build(ARouterPath.MeFgt).navigation() as Fragment

        fragmentList.clear()
        fragmentList.add(homeFrag)
        fragmentList.add(theaterFrag)
        fragmentList.add(welfareFrag)
        fragmentList.add(meFrag)

        mViewPagerAdapter?.fragmetList = fragmentList
        binding.mVpHome.adapter = mViewPagerAdapter

        binding.mVpHome.offscreenPageLimit = fragmentList.size
        binding.mVpHome.canScroll = false

        binding.mBottomNav.itemIconTintList = null//解决图标被颜色覆盖问题
        binding.mBottomNav.setOnItemSelectedListener(this)
        binding.mBottomNav.removeLongTouchToast()

        binding.mVpHome.addOnPageChangeListener(this)
        binding.mTvNetworkStatus.setOnClickListener(this)
    }

    private fun initData() {
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.mTvNetworkStatus -> {
                startActivity(Intent(Settings.ACTION_WIRELESS_SETTINGS))
            }
        }
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        Loger.e(TAG, "onPageSelected......position = $position")
        binding.mBottomNav.selectedItemId = binding.mBottomNav.menu[position].itemId
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        Loger.e(TAG, "onNavigationItemSelected......")
        when (item.itemId) {
            R.id.navigation_home -> {
                binding.mVpHome.setCurrentItem(0, false)
                return true
            }
            R.id.navigation_theater -> {
                binding.mVpHome.setCurrentItem(1,false)
                return true
            }
            R.id.navigation_welfare -> {
                binding.mVpHome.setCurrentItem(2,false)
                return true
            }
            R.id.navigation_me -> {
//                if (notLogin()) return true
                binding.mVpHome.setCurrentItem(3, false)
                return true
            }
        }
        return false
    }

    /**
     * Observe network changes i.e. Internet Connectivity
     */
    private fun handleNetworkChanges() {
        NetworkUtils.getNetworkLiveData(applicationContext).observe(
            this,
            androidx.lifecycle.Observer{ isConnected ->
                if (!isConnected) {
                    binding.mTvNetworkStatus.text = ResUtils.getStringRes(com.victor.lib.common.R.string.network_error)
                    binding.mTvNetworkStatus.apply {
                        show()
                        setBackgroundColor(ResUtils.getColorRes(com.victor.lib.common.R.color.color_D32F2F))
                    }
                } else {
                    binding.mTvNetworkStatus.text = ResUtils.getStringRes(com.victor.lib.common.R.string.connectivity)
                    binding.mTvNetworkStatus.apply {
                        setBackgroundColor(ResUtils.getColorRes(com.victor.lib.common.R.color.color_43A047))

                        animate()
                            .alpha(1f)
                            .setStartDelay(1000)
                            .setDuration(1000)
                            .setListener(object : AnimatorListenerAdapter() {
                                override fun onAnimationEnd(animation: Animator) {
                                    hide()
                                }
                            }
                        )
                    }
                }
            }
        )
    }

    /**
     * 移除长按点击弹出Toast
     */
    fun BottomNavigationView.removeLongTouchToast() {
        val bottomNavigationMenuView = this.getChildAt(0) as ViewGroup
        val size = bottomNavigationMenuView.childCount
        for (index in 0 until size) {
            bottomNavigationMenuView[index].setOnLongClickListener {
                true
            }
        }
    }

}