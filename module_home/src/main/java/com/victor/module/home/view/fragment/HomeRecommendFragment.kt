package com.victor.module.home.view.fragment

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.FrameLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.victor.lib.common.base.BaseFragment
import com.victor.lib.common.util.ToastUtils
import com.victor.lib.common.view.widget.RvPlayCellView
import com.victor.lib.common.view.widget.layoutmanager.ViewPagerLayoutManager
import com.victor.lib.common.view.widget.layoutmanager.ViewPagerLayoutManager.OnViewPagerListener
import com.victor.lib.coremodel.data.remote.entity.bean.HomeItemInfo
import com.victor.lib.coremodel.data.remote.entity.response.BaseRes
import com.victor.lib.coremodel.data.remote.vm.HomeVM
import com.victor.lib.coremodel.data.remote.vm.factory.HomeVMFactory
import com.victor.lib.coremodel.util.InjectorUtils
import com.victor.module.home.R
import com.victor.module.home.databinding.FragmentHomeRecommendBinding
import com.victor.module.home.view.adapter.PlayingAdapter
import com.victor.module.home.view.holder.PlayingContentViewHolder
import org.victor.http.lib.data.HttpResult
import androidx.core.view.isNotEmpty
import com.victor.lib.common.util.Constant
import com.victor.lib.common.util.Loger
import com.victor.lib.common.view.widget.LMRecyclerView
import com.victor.lib.video.cache.preload.PreLoadManager
import com.victor.lib.video.cache.preload.VideoPreLoadFuture

class HomeRecommendFragment : BaseFragment<FragmentHomeRecommendBinding>(FragmentHomeRecommendBinding::inflate),
    OnItemClickListener, OnRefreshListener, OnViewPagerListener,LMRecyclerView.OnLoadMoreListener {

    companion object {
        fun newInstance(): HomeRecommendFragment {
            return newInstance(0)
        }
        fun newInstance(id: Int): HomeRecommendFragment {
            val fragment = HomeRecommendFragment()
            val bundle = Bundle()
            bundle.putInt(ID_KEY, id)
            fragment.setArguments(bundle)
            return fragment
        }
    }

    private lateinit var mHomeVM: HomeVM
    private var mPlayingAdapter: PlayingAdapter? = null
    private var currentPosition = -1

    val ids = listOf(2, 4, 6, 8, 10, 12, 14,18,20,22,24,26,28,30,32,34,36,38)
    var requestId = ids[0]

    private var currentPage = 1

    val mVideoPreLoadFuture by lazy {
        VideoPreLoadFuture(requireContext(), Constant.PRELOAD_BUS_ID)
    }

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

    override fun onPause() {
        super.onPause()
        getCurrentPlayView()?.pause()
    }

    override fun onResume() {
        super.onResume()
        getCurrentPlayView()?.resume()
    }

    private fun initView() {
        mHomeVM = InjectorUtils.provideFragmentVM(this, HomeVMFactory(this), HomeVM::class.java)

        mPlayingAdapter = PlayingAdapter(requireContext(),this)
        binding.mRvPlaying.adapter = mPlayingAdapter
        val layoutManager = ViewPagerLayoutManager(requireContext(), LinearLayoutManager.VERTICAL)
        layoutManager.setOnViewPagerListener(this)
        binding.mRvPlaying.layoutManager = layoutManager

        binding.mRvPlaying.setLoadMoreListener(this)
        binding.mSrlRefresh.setOnRefreshListener(this)

        subscribeUi()
    }

    private fun initData() {
        sendHomePlayingRequest()
    }

    private fun subscribeUi() {
        mHomeVM.homePlayingData.observe(viewLifecycleOwner, Observer {
            binding.mSrlRefresh.isRefreshing = false
            when(it) {
                is HttpResult.Success -> {
                    showHomePlayingData(it.value)
                }
                is HttpResult.Error -> {
                    ToastUtils.show(it.message)
                }
            }
        })

        mHomeVM.homePlayingNextData.observe(viewLifecycleOwner, Observer {
            binding.mSrlRefresh.isRefreshing = false
            when(it) {
                is HttpResult.Success -> {
                    showHomePlayingData(it.value)
                }
                is HttpResult.Error -> {
                    ToastUtils.show(it.message)
                }
            }
        })
    }

    fun sendHomePlayingRequest() {
        mHomeVM.fetchHomePlaying(requestId)
    }

    fun showHomePlayingData(data: BaseRes<HomeItemInfo>) {
        mPlayingAdapter?.showData(data.itemList,binding.mTvNoData,binding.mRvPlaying, currentPage,
            false,true)

        var urls = data.itemList?.map { it.data?.playUrl }
        if (urls == null) {
            urls = ArrayList()
        }
        mVideoPreLoadFuture.addUrls(urls)
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
    }

    override fun onRefresh() {
        requestId = ids.random()
        sendHomePlayingRequest()
    }

    override fun OnLoadMore() {
        currentPage++
        requestId = ids.random()
        sendHomePlayingRequest()
    }

    override fun onPageRelease(isNext: Boolean, position: Int) {
        Log.i(TAG,"onPageRelease()......isNext = $isNext")
        Log.i(TAG,"onPageRelease()......position = $position")

        val viewHolder = binding.mRvPlaying.findViewHolderForLayoutPosition(position)
        if (viewHolder is PlayingContentViewHolder) {
            val mFlPlay = viewHolder.itemView.findViewById<FrameLayout>(R.id.mFlPlay)
            mFlPlay.removeAllViews()
        }
    }

    override fun onPageSelected(position: Int, isBottom: Boolean) {
        currentPosition = position
        //isBottom == true 可以加载下一页数据
        Log.i(TAG,"onPageSelected()......isBottom = $isBottom")
        Log.i(TAG,"onPageSelected()......position = $position")

        val viewHolder = binding.mRvPlaying.findViewHolderForLayoutPosition(position)
        if (viewHolder is PlayingContentViewHolder) {
            val mFlPlay = viewHolder.itemView.findViewById<FrameLayout>(R.id.mFlPlay)
            val playCell = RvPlayCellView(requireContext())

            mFlPlay.removeAllViews()
            mFlPlay.addView(playCell)

            val data = mPlayingAdapter?.getItem(position)
            val playUrl = data?.data?.playUrl
            playCell.play(playUrl)

            if (PreLoadManager.getInstance(requireContext()).hasEnoughCache(playUrl)) {
                Loger.d(TAG, "onPageSelected-playUrl()...has cached");
            }

            // 参数preloadBusId和VideoPreLoadFuture初始化的VideoPreLoadFuture保持一致，url为当前短视频播放地址
            mVideoPreLoadFuture.currentPlayUrl(playUrl)
        }
    }

    private fun getCurrentPlayView(): RvPlayCellView? {
        if (currentPosition == -1) return null
        val viewHolder = binding.mRvPlaying.findViewHolderForLayoutPosition(currentPosition)
            val mFlPlay = viewHolder?.itemView?.findViewById<FrameLayout>(R.id.mFlPlay)
            if (mFlPlay?.isNotEmpty() == true) {
                val childView = mFlPlay.getChildAt(0)
                if (childView is RvPlayCellView) {
                    return childView
                }
            }
        return null
    }

}