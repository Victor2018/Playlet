package com.victor.module.home.view.activity

import android.app.ComponentCaller
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
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
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.hok.lib.common.base.ARouterPath
import com.victor.lib.common.app.App
import com.victor.lib.common.base.BaseActivity
import com.victor.lib.common.interfaces.OnPlaySpeedSelectListener
import com.victor.lib.common.util.Constant
import com.victor.lib.common.util.Loger
import com.victor.lib.common.util.TextViewBoundsUtil
import com.victor.lib.common.util.ViewUtils.hide
import com.victor.lib.common.util.ViewUtils.show
import com.victor.lib.common.view.dialog.PlaySettingsDialog
import com.victor.lib.common.view.dialog.SpeedSelectDialog
import com.victor.lib.common.view.widget.ExpandableTextView
import com.victor.lib.common.view.widget.PlayCellView
import com.victor.lib.common.view.widget.layoutmanager.ViewPagerLayoutManager
import com.victor.lib.common.view.widget.layoutmanager.ViewPagerLayoutManager.OnViewPagerListener
import com.victor.lib.coremodel.data.local.entity.DramaEntity
import com.victor.lib.coremodel.data.local.vm.DramaVM
import com.victor.lib.coremodel.data.remote.vm.HomeVM
import com.victor.lib.coremodel.util.InjectorUtils
import com.victor.lib.video.cache.HttpProxyCacheServer
import com.victor.lib.video.cache.preload.PreLoadManager
import com.victor.lib.video.cache.preload.VideoPreLoadFuture
import com.victor.module.home.R
import com.victor.module.home.databinding.ActivityPlayBinding
import com.victor.module.home.view.adapter.DetailPlayingAdapter
import com.victor.module.home.view.dialog.EpisodesSelectDialog
import com.victor.module.home.view.holder.DetailPlayingContentViewHolder
import org.victor.http.lib.util.JsonUtils
import kotlin.getValue

@Route(path = ARouterPath.PlayAct)
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

    private val mHomeVM: HomeVM by viewModels {
        InjectorUtils.provideHomeVMFactory(this)
    }

    val mVideoPreLoadFuture by lazy {
        VideoPreLoadFuture(this, Constant.PRELOAD_BUS_ID)
    }

    private var mDetailPlayingAdapter: DetailPlayingAdapter? = null
    private var currentPosition = -1
    private var playPosition = 0

    private var mEpisodesSelectDialog: EpisodesSelectDialog? = null
    private var mSpeedSelectDialog: SpeedSelectDialog? = null
    private var mPlaySettingsDialog: PlaySettingsDialog? = null

    private var fullScreen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        statusBarTextColorBlack = false
        super.onCreate(savedInstanceState)
        initView()
        initData(intent)
    }

    private fun initView() {
        subscribeUi()

        mEpisodesSelectDialog = EpisodesSelectDialog(this,supportFragmentManager)

        mDetailPlayingAdapter = DetailPlayingAdapter(this,this)

        binding.mRvPlaying.adapter = mDetailPlayingAdapter
        val layoutManager = ViewPagerLayoutManager(this, LinearLayoutManager.VERTICAL)
        layoutManager.setOnViewPagerListener(this)
        binding.mRvPlaying.layoutManager = layoutManager

        binding.mIvBack.setOnClickListener(this)
        binding.mIvPlayFullScreen.setOnClickListener(this)
        binding.mTvPlaySpeed.setOnClickListener(this)
        binding.mIvSettingMore.setOnClickListener(this)
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

    private fun subscribeUi() {
        mHomeVM.updatePlayPositionData.observe(this, Observer {
            if (getCurrentPlayView()?.isPlaying() == true) {
                updatePlayHistory()
            }
        })
    }

    private fun updatePlayHistory() {
        val playPosition = getCurrentPlayView()?.getCurrentPositon() ?: 0
        val data = mDetailPlayingAdapter?.getItem(currentPosition)
        getDramaById(data?.data?.id ?: 0){
            val userId = App.get().getUserInfo()?.uid ?: ""
            val entity = DramaEntity(data?.data?.id ?: 0,userId,
                data,playPosition,
                it?.isHistory ?: false,
                it?.isFollowing ?: false,
                it?.isLiked ?: false,
                it?.isPurchased ?: false)
            mDramaVM.insert(entity)
        }
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.mIvBack -> {
                finish()
            }
            R.id.mIvPlayFullScreen -> {
                fullScreen = !fullScreen
                setFullPlayStyle()
            }
            R.id.mTvPlaySpeed -> {
                showSpeedSelectDlg()
            }
            R.id.mIvSettingMore -> {
                showPlaySettingsDlg()
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
                        data,it?.playPosition ?: 0,it?.isHistory ?: false,
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
                        data,it?.playPosition ?: 0,it?.isHistory ?: false,
                        it?.isFollowing ?: false, !isLiked,it?.isPurchased ?: false)
                    mDramaVM.insert(entity)
                }
            }
            R.id.mTvShareCount -> {
                getDramaById(data?.data?.id ?: 0){
                    val isPurchased = it?.isPurchased ?: false
                    val userId = App.get().getUserInfo()?.uid ?: ""
                    val entity = DramaEntity(data?.data?.id ?: 0,userId,
                        data,it?.playPosition ?: 0,it?.isHistory ?: false,
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

        /*val viewHolder = binding.mRvPlaying.findViewHolderForLayoutPosition(position)
        if (viewHolder is DetailPlayingContentViewHolder) {
            val mFlPlay = viewHolder.itemView.findViewById<FrameLayout>(R.id.mFlPlay)
            mFlPlay.removeAllViews()
        }*/

        App.get().removePlayCellViewFormParent()
    }

    override fun onPageSelected(position: Int, isBottom: Boolean) {
       play(position)
    }

    private fun play(position: Int) {
        currentPosition = position

        setFullPlayStyle()

        binding.mTvTitle.text = "第${position +1}集"
        if (currentPosition != position) {
            playPosition = 0
        }
        Log.i(TAG,"onPageSelected()......position = $position")

        val viewHolder = binding.mRvPlaying.findViewHolderForLayoutPosition(position)
        if (viewHolder is DetailPlayingContentViewHolder) {
            val data = mDetailPlayingAdapter?.getItem(position)

            val mFlPlay = viewHolder.itemView.findViewById<FrameLayout>(R.id.mFlPlay)

            App.get().removePlayCellViewFormParent()
//            val playCell = PlayCellView(this)
            val playCell = App.get().mPlayCellView
            playCell.setCurrentPositon(playPosition)

            mFlPlay.removeAllViews()
            mFlPlay.addView(playCell)

            val playUrl = data?.data?.playUrl
            Loger.d(TAG, "onPageSelected-playUrl = $playUrl")
            Loger.d(TAG, "onPageSelected-getLastPlayUrl = ${playCell.getLastPlayUrl()}")

            val proxy: HttpProxyCacheServer = App.get().mHttpProxyCacheServer
            val proxyUrl = proxy.getProxyUrl(playUrl)
            if (TextUtils.equals(proxyUrl,playCell.getLastPlayUrl())) {
                playCell.resume()
            } else {
                playCell.play(playUrl)
            }

            if (PreLoadManager.getInstance(this).hasEnoughCache(playUrl)) {
                Loger.d(TAG, "onPageSelected-playUrl()...has cached")
            }

            // 参数preloadBusId和VideoPreLoadFuture初始化的VideoPreLoadFuture保持一致，url为当前短视频播放地址
            mVideoPreLoadFuture.currentPlayUrl(playUrl)

            getDramaById(data?.data?.id ?: 0){
                setFollowingStyle(position,it?.isFollowing ?: false)
                setLikedStyle(position,it?.isLiked ?: false)

                val userId = App.get().getUserInfo()?.uid ?: ""
                val entity = DramaEntity(data?.data?.id ?: 0,userId,
                    data,it?.playPosition ?: 0,true, it?.isFollowing ?: false,
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
        if (currentPosition == -1) return
        App.get().removePlayCellViewFormParent()
        val viewHolder = binding.mRvPlaying.findViewHolderForLayoutPosition(currentPosition)
        val mFlPlay = viewHolder?.itemView?.findViewById<FrameLayout>(R.id.mFlPlay)
        mFlPlay?.addView(App.get().mPlayCellView)

//        getCurrentPlayView()?.resume()
    }

    private fun pausePlay() {
        if (currentPosition == -1) return
        App.get().removePlayCellViewFormParent()

//        getCurrentPlayView()?.pause()
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

    private fun getCurrentPlayCellView(): View? {
        if (currentPosition == -1) return null
        val viewHolder = binding.mRvPlaying.findViewHolderForLayoutPosition(currentPosition)
        return viewHolder?.itemView
    }

    private fun getDramaById(id: Int?,callback: (DramaEntity?) -> Unit) {
        mDramaVM.getById(id ?: 0,callback)
    }

    private fun showSpeedSelectDlg() {
        if (mSpeedSelectDialog == null) {
            mSpeedSelectDialog = SpeedSelectDialog(this)
            mSpeedSelectDialog?.mOnPlaySpeedSelectListener = object : OnPlaySpeedSelectListener {
                override fun OnPlaySpeedSelect(speed: Float) {
                    binding.mTvPlaySpeed.text = "${speed}X"
                    getCurrentPlayView()?.setSpeed(speed)
                }
            }
        }
        mSpeedSelectDialog?.show()
    }
    private fun showPlaySettingsDlg() {
        if (mPlaySettingsDialog == null) {
            mPlaySettingsDialog = PlaySettingsDialog(this)
//            mPlaySettingsDialog?.mOnPlaySpeedSelectListener = object : OnPlaySpeedSelectListener {
//                override fun OnPlaySpeedSelect(speed: Float) {
//                    getCurrentPlayView()?.setSpeed(speed)
//                }
//            }
        }
        mPlaySettingsDialog?.show()
    }

    fun setFullPlayStyle() {
        val mTvTitle = getCurrentPlayCellView()?.findViewById<TextView>(R.id.mTvTitle)
        val mTvDescribe = getCurrentPlayCellView()?.findViewById<ExpandableTextView>(R.id.mTvDescribe)
        val mTvFavCount = getCurrentPlayCellView()?.findViewById<TextView>(R.id.mTvFavCount)
        val mTvCollectCount = getCurrentPlayCellView()?.findViewById<TextView>(R.id.mTvCollectCount)
        val mTvShareCount = getCurrentPlayCellView()?.findViewById<TextView>(R.id.mTvShareCount)
        val mTvDramaEpisodes = getCurrentPlayCellView()?.findViewById<TextView>(R.id.mTvDramaEpisodes)

        if (fullScreen) {
            binding.mIvPlayFullScreen.setImageResource(R.mipmap.ic_play_normal_screen)
            mTvTitle?.hide()
            mTvDescribe?.hide()
            mTvFavCount?.hide()
            mTvCollectCount?.hide()
            mTvShareCount?.hide()
            mTvDramaEpisodes?.hide()
        } else {
            binding.mIvPlayFullScreen.setImageResource(R.mipmap.ic_play_full_screen)
            mTvTitle?.show()
            mTvDescribe?.show()
            mTvFavCount?.show()
            mTvCollectCount?.show()
            mTvShareCount?.show()
            mTvDramaEpisodes?.show()
        }
    }


    override fun onNewIntent(intent: Intent, caller: ComponentCaller) {
        super.onNewIntent(intent, caller)
        initData(intent)
    }
}