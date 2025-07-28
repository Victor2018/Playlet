package com.victor.lib.common.view.widget

import android.content.Context
import android.os.Message
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import androidx.constraintlayout.widget.ConstraintLayout
import com.victor.lib.common.databinding.PlayCellBinding
import com.victor.lib.common.module.Player
import com.victor.lib.common.util.MainHandler
import com.victor.lib.common.util.NetworkUtils
import com.victor.lib.common.util.ViewUtils.hide
import com.victor.lib.common.util.ViewUtils.show

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: RvPlayCellView
 * Author: Victor
 * Date: 2025/7/24 14:29
 * Description: 
 * -----------------------------------------------------------------
 */
class RvPlayCellView: ConstraintLayout,MainHandler.OnMainHandlerImpl,OnClickListener {
    private val TAG = "RvPlayCellView"
    private lateinit var binding: PlayCellBinding
    private var mPlayer: Player? = null

    constructor(context: Context) : this(context,null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs,0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView()
    }

    private fun initView() {
        Log.d(TAG,"initView()......")
        MainHandler.get().register(this)
        binding = PlayCellBinding.inflate(LayoutInflater.from(context), this, true)
        mPlayer = Player(binding.mTvPlay,MainHandler.get())
        setOnClickListener(this)
    }

    fun play(playUrl: String?) {
        mPlayer?.playUrl(playUrl)
    }

    fun pause() {
        if (mPlayer?.isPlaying() == false) return
        mPlayer?.pause()
        binding.mIvPlay.show()
    }

    fun resume() {
        if (mPlayer?.isPlaying() == true) return
        mPlayer?.resume()
        binding.mIvPlay.hide()
    }

    private fun startLoadingAnimation() {
        binding.mLoadingSeekBar.startLoadingAnimation()
    }

    private fun stopLoadingAnimation() {
        binding.mLoadingSeekBar.stopLoadingAnimation()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        Log.d(TAG,"onAttachedToWindow()......")
    }

    override fun onDetachedFromWindow() {
        MainHandler.get().unregister(this)
        super.onDetachedFromWindow()
        Log.d(TAG,"onDetachedFromWindow()......")
        mPlayer?.close()
        mPlayer = null
    }

    override fun handleMainMessage(message: Message?) {
        when (message?.what) {
            Player.PLAYER_PREPARING -> {
                startLoadingAnimation()
//                mIvPoster.hide()
            }
            Player.TRACK_READY -> {
            }
            Player.PLAYER_PREPARED -> {
                stopLoadingAnimation()
//                startPlayTime = 0
//                mIvPlay.setImageResource(com.hok.lib.common.R.mipmap.ic_course_video_pause)
//                mIvFullPlay.setImageResource(com.hok.lib.common.R.mipmap.ic_course_video_pause)
//                hideTopBottomCtrlView()
//                mClPlayError.hide()
//                mDuration = mHokPlayer?.getDuration() ?: 0
//                //当前播放进度剩余时间必须大于10s 才seek
//                if (currentPosition > 0 && currentPosition < mDuration - 10 * 1000) {
//                    mHokPlayer?.seekTo(currentPosition)
//                    if (mClBuyPlay.visibility == View.GONE) {
//                        mTvPlayTip.showPlayTip("已为您定位到上次播放位置")
//                    }
//                }
//                setPlayTime(0)

                if (!NetworkUtils.isWifiConnected(context)) {
//                    if (mClBuyPlay.visibility == View.GONE) {
//                        mTvPlayTip.showPlayTip("正在使用非Wifi网络，请注意流量消耗")
//                    }
                }
            }
            Player.PLAYER_SEEK_COMPLETE -> {
//                mIvPoster.hide()
//                mClPlayError.hide()
            }
            Player.PLAYER_REPLAY -> {
            }
            Player.PLAYER_ERROR -> {
                stopLoadingAnimation()
//                mClPlayError.show()
            }
            Player.PLAYER_BUFFERING_START -> {
                startLoadingAnimation()
//                startPlayTime = System.currentTimeMillis()
//                mClPlayError.hide()
//                checkPlayStatus()
            }
            Player.PLAYER_BUFFERING_END -> {
                stopLoadingAnimation()
//                startPlayTime = 0
//                hideTopBottomCtrlView()
//                mClPlayError.hide()
            }
            Player.PLAYER_PROGRESS_INFO -> {
//                val elapseMsec = mHokPlayer?.getCurrentPosition() ?: 0
//                updateProgress(elapseMsec)
//                setLoadingSpeed()
            }
            Player.PLAYER_COMPLETE -> {
                //这里放在外层处理有可能还有其他试看视频
            }
        }
    }

    override fun onClick(v: View?) {
        if (mPlayer?.isPlaying() == true) {
            pause()
        } else {
            resume()
        }
    }
}