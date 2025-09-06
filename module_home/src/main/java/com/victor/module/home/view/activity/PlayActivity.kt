package com.victor.module.home.view.activity

import android.app.ComponentCaller
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.victor.lib.common.app.App
import com.victor.lib.common.base.BaseActivity
import com.victor.lib.common.base.BaseFragment.Companion.TAG
import com.victor.lib.common.util.Constant
import com.victor.lib.common.util.Loger
import com.victor.lib.common.util.TextViewBoundsUtil
import com.victor.lib.common.view.widget.layoutmanager.ViewPagerLayoutManager
import com.victor.lib.common.view.widget.layoutmanager.ViewPagerLayoutManager.OnViewPagerListener
import com.victor.lib.coremodel.data.local.entity.DramaEntity
import com.victor.lib.video.cache.preload.PreLoadManager
import com.victor.lib.video.cache.preload.VideoPreLoadFuture
import com.victor.module.home.R
import com.victor.module.home.databinding.ActivityPlayBinding
import com.victor.module.home.view.adapter.PlayingAdapter
import com.victor.module.home.view.holder.PlayingContentViewHolder
import org.victor.http.lib.util.JsonUtils

class PlayActivity: BaseActivity<ActivityPlayBinding>(ActivityPlayBinding::inflate),
    OnItemClickListener, OnViewPagerListener {


    companion object {
        fun intentStart (activity: AppCompatActivity,position: Int) {
            val intent = Intent(activity, PlayActivity::class.java)
            intent.putExtra(Constant.POSITION_KEY,position)
            activity.startActivity(intent)
        }
    }

    val mVideoPreLoadFuture by lazy {
        VideoPreLoadFuture(this, Constant.PRELOAD_BUS_ID)
    }

    private var mPlayingAdapter: PlayingAdapter? = null
    private var currentPosition = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        statusBarTextColorBlack = false
        super.onCreate(savedInstanceState)
        initView()
        initData(intent)
    }

    private fun initView() {
        mPlayingAdapter = PlayingAdapter(this,this)

        binding.mRvPlaying.adapter = mPlayingAdapter
        val layoutManager = ViewPagerLayoutManager(this, LinearLayoutManager.VERTICAL)
        layoutManager.setOnViewPagerListener(this)
        binding.mRvPlaying.layoutManager = layoutManager
    }

    private fun initData(intent: Intent?) {
        currentPosition = intent?.getIntExtra(Constant.POSITION_KEY,0) ?: 0
        mPlayingAdapter?.showData(App.get().mPlayInfos)
        binding.mRvPlaying.scrollToPosition(currentPosition)
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
//                    getDramaVM()?.insert(entity)
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
//                    getDramaVM()?.insert(entity)
                }
            }
            R.id.mTvShareCount -> {
                getDramaById(data?.data?.id ?: 0){
                    val isPurchased = it?.isPurchased ?: false
                    val userId = App.get().getUserInfo()?.uid ?: ""
                    val entity = DramaEntity(data?.data?.id ?: 0,userId,
                        JsonUtils.toJSONString(data),it?.isHistory ?: false,
                        it?.isFollowing ?: false,it?.isLiked ?: false,!isPurchased)
//                    getDramaVM()?.insert(entity)
                }
            }
        }
    }
    override fun onPageRelease(isNext: Boolean, position: Int) {
        Log.i(TAG,"onPageRelease()......isNext = $isNext")
        Log.i(TAG,"onPageRelease()......position = $position")

        App.get().removePlayViewFormParent()
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

            val playCell = App.get().mRvPlayCellView

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
//                getDramaVM()?.insert(entity)
            }
        }
    }

    private fun setFollowingStyle(position: Int,isFollowing: Boolean) {
        val viewHolder = binding.mRvPlaying.findViewHolderForLayoutPosition(position)
        if (viewHolder is PlayingContentViewHolder) {
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
        if (viewHolder is PlayingContentViewHolder) {
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

    private fun getDramaById(id: Int?,callback: (DramaEntity?) -> Unit) {
//        getDramaVM()?.getById(id ?: 0,callback)
    }

    override fun onNewIntent(intent: Intent, caller: ComponentCaller) {
        super.onNewIntent(intent, caller)
        initData(intent)
    }
}