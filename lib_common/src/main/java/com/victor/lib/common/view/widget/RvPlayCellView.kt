package com.victor.lib.common.view.widget

import android.content.Context
import android.os.Message
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.constraintlayout.widget.ConstraintLayout
import com.victor.lib.common.databinding.PlayCellBinding
import com.victor.lib.common.module.Player
import com.victor.lib.common.util.ImageUtils
import com.victor.lib.common.util.Loger
import com.victor.lib.common.util.MainHandler
import com.victor.lib.common.util.NetworkUtils
import com.victor.lib.common.util.TimeUtils
import com.victor.lib.common.util.ViewUtils.hide
import com.victor.lib.common.util.ViewUtils.show
import com.victor.lib.coremodel.data.remote.entity.bean.HomeItemInfo

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
class RvPlayCellView: ConstraintLayout,MainHandler.OnMainHandlerImpl,OnClickListener,OnSeekBarChangeListener {
    private val TAG = "RvPlayCellView"
    private lateinit var binding: PlayCellBinding
    private var mPlayer: Player? = null
    private var isSeeking = false //是否正在拖动进度
    private var currentPosition: Int = 0
    private var mDuration: Int = 0 //影片时长

    constructor(context: Context) : this(context,null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs,0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView()
    }

    private fun initView() {
        MainHandler.get().register(this)
        binding = PlayCellBinding.inflate(LayoutInflater.from(context), this, true)
        mPlayer = Player(binding.mTvPlay,MainHandler.get())

        setOnClickListener(this)
        binding.mLoadingSeekBar.setOnSeekBarChangeListener(this)
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
        binding.mLoadingSeekBar.showLight(true)
    }

    private fun stopLoadingAnimation() {
        binding.mLoadingSeekBar.showLight(false)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        Loger.d(TAG,"onAttachedToWindow()......")
    }

    override fun onDetachedFromWindow() {
        MainHandler.get().unregister(this)
        super.onDetachedFromWindow()
        Loger.d(TAG,"onDetachedFromWindow()......")
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
                mDuration = mPlayer?.getDuration() ?: 0
                //当前播放进度剩余时间必须大于10s 才seek
                if (currentPosition > 0 && currentPosition < mDuration - 10 * 1000) {
                    mPlayer?.seekTo(currentPosition)
                }
                setPlayTime(0)

                if (!NetworkUtils.isWifiConnected(context)) {
//                    if (mClBuyPlay.visibility == View.GONE) {
//                        mTvPlayTip.showPlayTip("正在使用非Wifi网络，请注意流量消耗")
//                    }
                }
            }
            Player.PLAYER_SEEK_COMPLETE -> {
//                mIvPoster.hide()
            }
            Player.PLAYER_REPLAY -> {
            }
            Player.PLAYER_ERROR -> {
                stopLoadingAnimation()
            }
            Player.PLAYER_BUFFERING_START -> {
                startLoadingAnimation()
            }
            Player.PLAYER_BUFFERING_END -> {
                stopLoadingAnimation()
            }
            Player.PLAYER_PROGRESS_INFO -> {
                val elapseMsec = mPlayer?.getCurrentPosition() ?: 0
                updateProgress(elapseMsec)
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

    fun setPlayTime (elapseMsec: Int) {
        val mTimePass = TimeUtils.getMs(elapseMsec / 1000)
        val mTimeLong = TimeUtils.getMs(mDuration / 1000)

        val mFullTimePass = TimeUtils.getHMS(elapseMsec / 1000)
        val mFullTimeLong = TimeUtils.getHMS(mDuration / 1000)

        /*mTvPlayTime.text = "${mTimePass}/${mTimeLong}"

        mTvFullPassTime.text = mFullTimePass
        mTvFullLongTime.text = mFullTimeLong

        mTvSeekTime.text = "${mFullTimePass}/${mFullTimeLong}"*/
    }

    private fun updateProgress(elapseMsec: Int) {
        if (mDuration > 0 && !isSeeking) {
            currentPosition = elapseMsec
            setPlayTime(elapseMsec)
            val progress = elapseMsec * 100 / mDuration

            val secondaryMsec = mPlayer?.getBufferPercentage() ?: 0
            val secondaryProgress = secondaryMsec * 100 / mDuration

            binding.mLoadingSeekBar.secondaryProgress = secondaryProgress
            binding.mLoadingSeekBar.progress = progress
        }
    }

    override fun onProgressChanged(sb: SeekBar?, progress: Int, fromUser: Boolean) {
    }

    override fun onStartTrackingTouch(sb: SeekBar?) {
        isSeeking = true
    }

    override fun onStopTrackingTouch(sb: SeekBar?) {
        isSeeking = false
        var progress = sb?.progress ?: 0
        var duration = mPlayer?.getDuration() ?: 0
        if (duration > 0) {
            val msec = progress * duration / 100
            mPlayer?.seekTo(msec)
        }
    }
}