package com.victor.module.home.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.Gravity
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
import com.victor.lib.common.util.DramaShowUtil
import kotlin.random.Random


class HomeRecommendFragment : BaseFragment<FragmentHomeRecommendBinding>(FragmentHomeRecommendBinding::inflate),
    OnItemClickListener, OnRefreshListener, OnViewPagerListener {

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
    }

    fun sendHomePlayingRequest() {
        val ids = listOf(2, 4, 6, 8, 10, 12, 14,18,20,22,24,26,28,30,32,34,36,38)
        val id = ids.random()
        mHomeVM.fetchHomePlaying(id)
    }

    fun showHomePlayingData(data: BaseRes<HomeItemInfo>) {
        mPlayingAdapter?.showData(data.itemList)
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
    }

    override fun onRefresh() {
        sendHomePlayingRequest()
    }

    override fun onPageRelease(isNext: Boolean, position: Int) {
        Log.i(TAG,"onPageRelease()......isNext = $isNext")
        Log.i(TAG,"onPageRelease()......position = $position")

        val viewHolder = binding.mRvPlaying.findViewHolderForLayoutPosition(position) as PlayingContentViewHolder
        val mFlPlay = viewHolder.itemView.findViewById<FrameLayout>(R.id.mFlPlay)
        mFlPlay.removeAllViews()
    }

    override fun onPageSelected(position: Int, isBottom: Boolean) {
        currentPosition = position
        //isBottom == true 可以加载下一页数据
        Log.i(TAG,"onPageSelected()......isBottom = $isBottom")
        Log.i(TAG,"onPageSelected()......position = $position")

        val viewHolder = binding.mRvPlaying.findViewHolderForLayoutPosition(position) as PlayingContentViewHolder
        val mFlPlay = viewHolder.itemView.findViewById<FrameLayout>(R.id.mFlPlay)
        val playCell = RvPlayCellView(requireContext())

        mFlPlay.removeAllViews()
        mFlPlay.addView(playCell)

        val data = mPlayingAdapter?.getItem(position)
        val playUrl = data?.data?.playUrl
        Log.i(TAG,"onPageSelected()......playUrl = $playUrl")
        playCell.play(playUrl)
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