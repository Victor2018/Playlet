package com.victor.module.home.view.dialog

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.tabs.TabLayoutMediator
import com.victor.lib.common.util.ImageUtils
import com.victor.lib.common.util.ResUtils
import com.victor.lib.common.util.ScreenUtils
import com.victor.lib.common.view.adapter.ViewPager2Adapter
import com.victor.lib.common.view.dialog.AbsBottomSheetDialog
import com.victor.lib.coremodel.data.remote.entity.bean.DramaItemInfo
import com.victor.module.home.R
import com.victor.module.home.databinding.DlgEpisodesSelectBinding
import com.victor.module.home.view.fragment.EpisodesProfileFragment
import com.victor.module.home.view.fragment.EpisodesSelectFragment
import kotlin.random.Random

class EpisodesSelectDialog(context: Context, val fm: FragmentManager) : AbsBottomSheetDialog<DlgEpisodesSelectBinding>
    (context,DlgEpisodesSelectBinding::inflate),View.OnClickListener,
    AdapterView.OnItemClickListener {

    var mViewPager2Adapter: ViewPager2Adapter? = null
    private var pagerTitles: Array<String>? = null
    private var fragmentList: ArrayList<Fragment> = ArrayList()

    var mDramaItemInfo: DramaItemInfo? = null

    override fun handleLayoutParams(wlp: WindowManager.LayoutParams?) {
        wlp?.width = ScreenUtils.getWidth(context)
//        wlp?.height = ScreenUtils.getHeight(context) * 8 / 10
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initData()
    }

    private fun initView() {
        pagerTitles = ResUtils.getStringArrayRes(R.array.home_episodes_tab_titles)
        fragmentList.clear()
        fragmentList.add(EpisodesProfileFragment.newInstance(mDramaItemInfo))
        fragmentList.add(EpisodesSelectFragment.newInstance())

        mViewPager2Adapter = ViewPager2Adapter(fm, lifecycle)
        mViewPager2Adapter?.fragmetList = fragmentList

        binding.mVpEpisodes.adapter = mViewPager2Adapter
        binding.mVpEpisodes.currentItem = 1

        TabLayoutMediator(binding.mTabEpisodes, binding.mVpEpisodes) { tab, position ->
            tab.text = pagerTitles?.get(position)
        }.attach()
    }

    private fun initData() {
        binding.mTvTitle.text = mDramaItemInfo?.data?.title ?: ""
        val url = mDramaItemInfo?.data?.cover?.feed ?: ""
        ImageUtils.instance.loadImage(context,binding.mIvPoster, url)

        val dramaCount = Random.nextInt(20, 101)
        binding.mTvType.text = "${mDramaItemInfo?.data?.category ?: ""} · 全${dramaCount}集"
    }

    override fun onClick(v: View?) {
    }

    override fun onItemClick(p0: AdapterView<*>?, v: View?, position: Int, id: Long) {
    }


}