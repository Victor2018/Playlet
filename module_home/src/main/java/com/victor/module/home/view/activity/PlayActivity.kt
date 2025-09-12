package com.victor.module.home.view.activity

import android.app.ComponentCaller
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.FrameLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isNotEmpty
import androidx.recyclerview.widget.LinearLayoutManager
import com.victor.lib.common.app.App
import com.victor.lib.common.base.BaseActivity
import com.victor.lib.common.util.Constant
import com.victor.lib.common.util.Loger
import com.victor.lib.common.util.TextViewBoundsUtil
import com.victor.lib.common.view.widget.PlayCellView
import com.victor.lib.common.view.widget.layoutmanager.ViewPagerLayoutManager
import com.victor.lib.common.view.widget.layoutmanager.ViewPagerLayoutManager.OnViewPagerListener
import com.victor.lib.coremodel.data.local.entity.DramaEntity
import com.victor.lib.coremodel.data.local.vm.DramaVM
import com.victor.lib.coremodel.util.InjectorUtils
import com.victor.lib.video.cache.preload.PreLoadManager
import com.victor.lib.video.cache.preload.VideoPreLoadFuture
import com.victor.module.home.R
import com.victor.module.home.databinding.ActivityPlayBinding
import com.victor.module.home.view.adapter.DetailPlayingAdapter
import com.victor.module.home.view.dialog.EpisodesSelectDialog
import com.victor.module.home.view.holder.DetailPlayingContentViewHolder
import org.victor.http.lib.util.JsonUtils
import kotlin.getValue

class PlayActivity: BaseActivity<ActivityPlayBinding>(ActivityPlayBinding::inflate),
    OnItemClickListener, OnViewPagerListener,OnClickListener {

    companion object {
        fun intentStart (activity: AppCompatActivity,position: Int,playPosition: Int) {
            val intent = Intent(activity, PlayActivity::class.java)
            intent.putExtra(Constant.POSITION_KEY,position)
            intent.putExtra(Constant.PLAY_POSITION_KEY,playPosition)
            activity.startActivity(intent)
        }
    }

    private val mDramaVM: DramaVM by viewModels {
        val userId = App.get().getUserInfo()?.uid ?: ""
        InjectorUtils.provideDramaVMFactory(this, userId)
    }

    val mVideoPreLoadFuture by lazy {
        VideoPreLoadFuture(this, Constant.PRELOAD_BUS_ID)
    }

    private var mDetailPlayingAdapter: DetailPlayingAdapter? = null
    private var currentPosition = -1
    private var playPosition = 0

    private var mEpisodesSelectDialog: EpisodesSelectDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        statusBarTextColorBlack = false
        super.onCreate(savedInstanceState)
        initView()
        initData(intent)
    }

    private fun initView() {
        mEpisodesSelectDialog = EpisodesSelectDialog(this,supportFragmentManager)
        mDetailPlayingAdapter = DetailPlayingAdapter(this,this)

        binding.mRvPlaying.adapter = mDetailPlayingAdapter
        val layoutManager = ViewPagerLayoutManager(this, LinearLayoutManager.VERTICAL)
        layoutManager.setOnViewPagerListener(this)
        binding.mRvPlaying.layoutManager = layoutManager

        binding.mIvBack.setOnClickListener(this)
    }

    private fun initData(intent: Intent?) {
        currentPosition = intent?.getIntExtra(Constant.POSITION_KEY,0) ?: 0
        playPosition = intent?.getIntExtra(Constant.PLAY_POSITION_KEY,0) ?: 0

        mDetailPlayingAdapter?.showData(App.get().mPlayInfos)

        binding.mRvPlaying.scrollToPosition(currentPosition)
        if (binding.mRvPlaying.layoutManager is ViewPagerLayoutManager) {
            val vlm = binding.mRvPlaying.layoutManager as ViewPagerLayoutManager
            vlm.mLastSelectPosition = currentPosition
        }
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.mIvBack -> {
                finish()
            }
        }
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val data = mDetailPlayingAdapter?.getItem(position)
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
            R.id.mTvDramaEpisodes -> {
                mEpisodesSelectDialog?.mDramaItemInfo = mDetailPlayingAdapter?.getItem(position)
                mEpisodesSelectDialog?.show()
            }
        }
    }
    override fun onPageRelease(isNext: Boolean, position: Int) {
        Log.i(TAG,"onPageRelease()......isNext = $isNext")
        Log.i(TAG,"onPageRelease()......position = $position")

        App.get().removePlayViewFormParent()

        val viewHolder = binding.mRvPlaying.findViewHolderForLayoutPosition(position)
        if (viewHolder is DetailPlayingContentViewHolder) {
            val mFlPlay = viewHolder.itemView.findViewById<FrameLayout>(R.id.mFlPlay)
            mFlPlay.removeAllViews()
        }
    }

    override fun onPageSelected(position: Int, isBottom: Boolean) {
       play(position)
    }

    private fun play(position: Int) {
        binding.mTvTitle.text = "第${position +1}集"
        if (currentPosition != position) {
            playPosition = 0
        }
        currentPosition = position
        Log.i(TAG,"onPageSelected()......position = $position")

        val viewHolder = binding.mRvPlaying.findViewHolderForLayoutPosition(position)
        if (viewHolder is DetailPlayingContentViewHolder) {
            val data = mDetailPlayingAdapter?.getItem(position)

            val mFlPlay = viewHolder.itemView.findViewById<FrameLayout>(R.id.mFlPlay)

            val playCell = PlayCellView(this)
            playCell.setCurrentPositon(playPosition)

            mFlPlay.removeAllViews()
            mFlPlay.addView(playCell)

            val playUrl = data?.data?.playUrl
            playCell.play(playUrl)

            if (PreLoadManager.getInstance(this).hasEnoughCache(playUrl)) {
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
        if (viewHolder is DetailPlayingContentViewHolder) {
            val mTvFavCount = viewHolder.itemView.findViewById<TextView>(R.id.mTvFavCount)
            if (isFollowing) {
                TextViewBoundsUtil.setTvDrawableTop(this,mTvFavCount,com.victor.lib.common.R.mipmap.ic_fav_focus)
            } else {
                TextViewBoundsUtil.setTvDrawableTop(this,mTvFavCount,com.victor.lib.common.R.mipmap.ic_fav_normal)
            }
        }
    }

    private fun setLikedStyle(position: Int,isLiked: Boolean) {
        val viewHolder = binding.mRvPlaying.findViewHolderForLayoutPosition(position)
        if (viewHolder is DetailPlayingContentViewHolder) {
            val mTvCollectCount = viewHolder.itemView.findViewById<TextView>(R.id.mTvCollectCount)
            if (isLiked) {
                TextViewBoundsUtil.setTvDrawableTop(
                    this,
                    mTvCollectCount,
                    com.victor.lib.common.R.mipmap.ic_collect_focus
                )
            } else {
                TextViewBoundsUtil.setTvDrawableTop(
                    this,
                    mTvCollectCount,
                    com.victor.lib.common.R.mipmap.ic_collect_normal
                )
            }
        }
    }

    override fun onPause() {
        super.onPause()
        pausePlay()
    }

    override fun onResume() {
        super.onResume()
        resumePlay()
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

    override fun onNewIntent(intent: Intent, caller: ComponentCaller) {
        super.onNewIntent(intent, caller)
        initData(intent)
    }
}