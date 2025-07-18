package com.victor.lib.common.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.victor.lib.common.R
import com.victor.lib.common.util.ImageUtils

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: SplashViewPagerAdapter
 * Author: Victor
 * Date: 2022/4/18 18:15
 * Description: 
 * -----------------------------------------------------------------
 */

class SplashViewPagerAdapter(var context: Context) : PagerAdapter() {
    var datas: ArrayList<Int>? = null
    private var layoutInflater: LayoutInflater? = null
    var mOnClickListener: View.OnClickListener? = null

    init {
        layoutInflater = LayoutInflater.from(context)
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view == obj
    }

    override fun getCount(): Int {
        return datas?.size ?: 0
    }

    override fun instantiateItem(container: ViewGroup, position: Int): View {
        val imagePath = datas?.get(position)
        val view = layoutInflater?.inflate(R.layout.splash_image_cell, container, false)!!
        var mIvImage = view?.findViewById<ImageView>(R.id.mIvImage)
        mIvImage?.setOnClickListener(mOnClickListener)
        ImageUtils.instance.loadImage(context, mIvImage, imagePath)
        container.addView(mIvImage)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(obj as View)
    }
}