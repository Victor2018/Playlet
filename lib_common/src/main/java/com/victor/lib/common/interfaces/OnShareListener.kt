package com.victor.lib.common.interfaces

import android.graphics.Bitmap

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: OnShareListener
 * Author: Victor
 * Date: 2022/3/1 18:28
 * Description: 
 * -----------------------------------------------------------------
 */

interface OnShareListener {
    fun OnShareFriend ()
    fun OnShareFriendCircle ()
    fun OnShareCopeLink ()
    fun OnShareSave (bitmap: Bitmap?)
//    fun OnShareQQ ()
//    fun OnShareQZone ()
}