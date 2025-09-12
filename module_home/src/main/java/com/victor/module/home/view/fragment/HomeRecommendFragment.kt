package com.victor.module.home.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isNotEmpty
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.victor.lib.common.base.BaseFragment
import com.victor.lib.common.util.ToastUtils
import com.victor.lib.common.view.widget.layoutmanager.ViewPagerLayoutManager
import com.victor.lib.common.view.widget.layoutmanager.ViewPagerLayoutManager.OnViewPagerListener
import com.victor.lib.coremodel.data.remote.entity.bean.DramaItemInfo
import com.victor.lib.coremodel.data.remote.entity.response.BaseRes
import com.victor.lib.coremodel.data.remote.vm.HomeVM
import com.victor.lib.coremodel.data.remote.vm.factory.HomeVMFactory
import com.victor.lib.coremodel.util.InjectorUtils
import com.victor.module.home.R
import com.victor.module.home.databinding.FragmentHomeRecommendBinding
import com.victor.module.home.view.adapter.PlayingAdapter
import com.victor.module.home.view.holder.PlayingContentViewHolder
import org.victor.http.lib.data.HttpResult
import com.victor.lib.common.app.App
import com.victor.lib.common.interfaces.IHomeMain
import com.victor.lib.common.util.Constant
import com.victor.lib.common.util.Loger
import com.victor.lib.common.util.TextViewBoundsUtil
import com.victor.lib.common.util.ViewUtils.hide
import com.victor.lib.common.util.ViewUtils.show
import com.victor.lib.common.view.widget.LMRecyclerView
import com.victor.lib.common.view.widget.PlayCellView
import com.victor.lib.coremodel.data.local.entity.DramaEntity
import com.victor.lib.coremodel.data.local.vm.DramaVM
import com.victor.lib.video.cache.preload.PreLoadManager
import com.victor.lib.video.cache.preload.VideoPreLoadFuture
import com.victor.module.home.view.activity.PlayActivity
import org.victor.http.lib.util.JsonUtils
import kotlin.getValue

class HomeRecommendFragment : BaseFragment<FragmentHomeRecommendBinding>(FragmentHomeRecommendBinding::inflate),
    OnItemClickListener, OnRefreshListener, OnViewPagerListener,LMRecyclerView.OnLoadMoreListener,OnClickListener {

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

    //共享使用MainActivity 的 viewmodel
    private val mDramaVM: DramaVM by activityViewModels()

    private var mPlayingAdapter: PlayingAdapter? = null
    private var currentPosition = -1

    val ids = listOf(2, 4, 6, 8, 10, 12, 14,18,20,22,24,26,28,30,32,34,36,38)
    var requestId = 0

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
        pausePlay()
    }

    override fun onResume() {
        super.onResume()
        resumePlay()
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

        binding.mIvBack.setOnClickListener(this)

        subscribeUi()
    }

    private fun initData() {
        requestId = ids.random()
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

    fun showHomePlayingData(data: BaseRes<DramaItemInfo>) {
        mPlayingAdapter?.showData(data.itemList,null,binding.mRvPlaying, currentPage,
            false,true)

        var urls = data.itemList?.map { it.data?.playUrl }
        if (urls == null) {
            urls = ArrayList()
        }
        mVideoPreLoadFuture.addUrls(urls)
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val data = mPlayingAdapter?.getItem(position)
        when (view?.id) {
            R.id.mTvFavCount -> {
                getDramaById(data?.data?.id ?: 0){
                    val isFollowing = it?.isFollowing ?: false
                    setFollowingStyle(position,!isFollowing)
                    val userId = App.get().getUserInfo()?.uid ?: ""
                    val entity = DramaEntity(data?.data?.id ?: 0,userId,
                        JsonUtils.toJSONString(data),it?.isHistory ?: false,
                        !isFollowing, it?.isLiked ?: false,it?.isPurchased ?: false)
                    mDramaVM.insert(entity)
                }
            }
            R.id.mTvCollectCount -> {
                getDramaById(data?.data?.id ?: 0){
                    val isLiked = it?.isLiked ?: false
                    setLikedStyle(position,!isLiked)
                    val userId = App.get().getUserInfo()?.uid ?: ""
                    val entity = DramaEntity(data?.data?.id ?: 0,userId,
                        JsonUtils.toJSONString(data),it?.isHistory ?: false,
                        it?.isFollowing ?: false, !isLiked,it?.isPurchased ?: false)
                    mDramaVM.insert(entity)
                }
            }
            R.id.mTvShareCount -> {
                getDramaById(data?.data?.id ?: 0){
                    val isPurchased = it?.isPurchased ?: false
                    val userId = App.get().getUserInfo()?.uid ?: ""
                    val entity = DramaEntity(data?.data?.id ?: 0,userId,
                        JsonUtils.toJSONString(data),it?.isHistory ?: false,
                        it?.isFollowing ?: false,it?.isLiked ?: false,!isPurchased)
                    mDramaVM.insert(entity)
                }
            }
            R.id.mTvDramaCount -> {
                App.get().mPlayInfos = mPlayingAdapter?.getDatas()
                val playPosition = getCurrentPlayView()?.getCurrentPositon() ?: 0

                App.get().removePlayViewFormParent()

                PlayActivity.intentStart(activity as AppCompatActivity,position,playPosition)
//                setPlayStyle(true)
            }
        }
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

        App.get().removePlayViewFormParent()

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
            val data = mPlayingAdapter?.getItem(position)

            val mFlPlay = viewHolder.itemView.findViewById<FrameLayout>(R.id.mFlPlay)

            val playCell = PlayCellView(requireContext())

            mFlPlay.removeAllViews()
            mFlPlay.addView(playCell)

            val playUrl = data?.data?.playUrl
            playCell.play(playUrl)

            if (PreLoadManager.getInstance(requireContext()).hasEnoughCache(playUrl)) {
                Loger.d(TAG, "onPageSelected-playUrl()...has cached");
            }

            // 参数preloadBusId和VideoPreLoadFuture初始化的VideoPreLoadFuture保持一致，url为当前短视频播放地址
            mVideoPreLoadFuture.currentPlayUrl(playUrl)

            getDramaById(data?.data?.id ?: 0){
                setFollowingStyle(position,it?.isFollowing ?: false)
                setLikedStyle(position,it?.isLiked ?: false)

                val userId = App.get().getUserInfo()?.uid ?: ""
                val entity = DramaEntity(data?.data?.id ?: 0,userId,
                    JsonUtils.toJSONString(data),true, it?.isFollowing ?: false,
                    it?.isLiked ?: false,it?.isPurchased ?: false)
                mDramaVM.insert(entity)
            }
        }
    }

    private fun setFollowingStyle(position: Int,isFollowing: Boolean) {
        val viewHolder = binding.mRvPlaying.findViewHolderForLayoutPosition(position)
        if (viewHolder is PlayingContentViewHolder) {
            val mTvFavCount = viewHolder.itemView.findViewById<TextView>(R.id.mTvFavCount)
            if (isFollowing) {
                TextViewBoundsUtil.setTvDrawableTop(context,mTvFavCount,com.victor.lib.common.R.mipmap.ic_fav_focus)
            } else {
                TextViewBoundsUtil.setTvDrawableTop(context,mTvFavCount,com.victor.lib.common.R.mipmap.ic_fav_normal)
            }
        }
    }

    private fun setPlayStyle(playing: Boolean) {
        if (playing) {
            binding.appbar.show()
            binding.mTvBottomSlogan.show()
            if (parentFragment is HomeFragment) {
                val parentFrag = parentFragment as HomeFragment
                parentFrag.binding.appbar.hide()
            }
            if (activity is IHomeMain) {
                val homeMain = activity as IHomeMain
                homeMain.getBottomNavBar().hide()
            }
        } else {
            binding.appbar.hide()
            binding.mTvBottomSlogan.hide()
            if (parentFragment is HomeFragment) {
                val parentFrag = parentFragment as HomeFragment
                parentFrag.binding.appbar.show()
            }
            if (activity is IHomeMain) {
                val homeMain = activity as IHomeMain
                homeMain.getBottomNavBar().show()
            }
        }
    }

    private fun setLikedStyle(position: Int,isLiked: Boolean) {
        val viewHolder = binding.mRvPlaying.findViewHolderForLayoutPosition(position)
        if (viewHolder is PlayingContentViewHolder) {
            val mTvCollectCount = viewHolder.itemView.findViewById<TextView>(R.id.mTvCollectCount)
            if (isLiked) {
                TextViewBoundsUtil.setTvDrawableTop(context,mTvCollectCount,com.victor.lib.common.R.mipmap.ic_collect_focus)
            } else {
                TextViewBoundsUtil.setTvDrawableTop(context,mTvCollectCount,com.victor.lib.common.R.mipmap.ic_collect_normal)
            }
        }
    }

    private fun resumePlay() {
        /*if (currentPosition == -1) return
        App.get().removePlayViewFormParent()
        val viewHolder = binding.mRvPlaying.findViewHolderForLayoutPosition(currentPosition)
        val mFlPlay = viewHolder?.itemView?.findViewById<FrameLayout>(R.id.mFlPlay)
        mFlPlay?.addView(App.get().mRvPlayCellView)*/

        getCurrentPlayView()?.resume()
    }

    private fun pausePlay() {
//        if (currentPosition == -1) return
//        App.get().removePlayViewFormParent()

        getCurrentPlayView()?.pause()
    }

    private fun getCurrentPlayView(): PlayCellView? {
        if (currentPosition == -1) return null
        val viewHolder = binding.mRvPlaying.findViewHolderForLayoutPosition(currentPosition)
        val mFlPlay = viewHolder?.itemView?.findViewById<FrameLayout>(R.id.mFlPlay)
        if (mFlPlay?.isNotEmpty() == true) {
            val childView = mFlPlay.getChildAt(0)
            if (childView is PlayCellView) {
                return childView
            }
        }
        return null
    }

    private fun getDramaById(id: Int?,callback: (DramaEntity?) -> Unit) {
        mDramaVM.getById(id ?: 0,callback)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.mIvBack -> {
               setPlayStyle(false)
            }
        }
    }

}