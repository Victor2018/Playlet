package com.victor.playlet

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.alibaba.android.arouter.launcher.ARouter
import com.hok.lib.common.base.ARouterPath
import com.victor.lib.common.base.BaseActivity
import com.victor.lib.common.util.NetworkUtils
import com.victor.lib.common.util.ResUtils
import com.victor.lib.common.util.ViewUtils.hide
import com.victor.lib.common.util.ViewUtils.show
import com.victor.lib.common.view.adapter.ViewPagerAdapter
import com.victor.lib.common.view.widget.readablebottombar.ReadableBottomBar.ItemSelectListener
import com.victor.lib.coremodel.util.InjectorUtils
import com.victor.lib.coremodel.vm.GankGirlVM
import com.victor.playlet.databinding.ActivityMainBinding


class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate),
    OnClickListener,ItemSelectListener,OnPageChangeListener {


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
        binding.particleView.resume()
    }

    override fun onPause() {
        super.onPause()
        binding.particleView.pause()
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

        binding.mNavBar.setOnItemSelectListener(this)
        binding.mVpHome.addOnPageChangeListener(this)
        binding.mTvNetworkStatus.setOnClickListener(this)
    }

    private fun initData() {
    }

    override fun onClick(v: View?) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        binding.mNavBar.selectItem(position)
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onItemSelected(index: Int) {
        Log.i(TAG,"onItemSelected()......index = $index")
        binding.mVpHome.setCurrentItem(index,false)
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

}