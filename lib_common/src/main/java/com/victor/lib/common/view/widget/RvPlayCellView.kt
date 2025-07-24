package com.victor.lib.common.view.widget

import android.content.Context
import android.util.AttributeSet
import android.view.TextureView
import com.victor.lib.common.module.Player
import com.victor.lib.common.util.MainHandler

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
class RvPlayCellView: TextureView {
    private var mPlayer: Player? = null

    constructor(context: Context) : this(context,null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs,0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView()
    }

    private fun initView() {
        mPlayer = Player(this,MainHandler.get())
    }

    fun play(playUrl: String?) {
        mPlayer?.playUrl(playUrl)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mPlayer?.stop()
        mPlayer = null
    }
}