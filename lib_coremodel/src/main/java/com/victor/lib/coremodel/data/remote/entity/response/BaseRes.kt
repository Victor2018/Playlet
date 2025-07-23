package com.victor.lib.coremodel.data.remote.entity.response

import android.text.TextUtils
import com.victor.lib.coremodel.data.remote.entity.bean.FollowItem

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: BaseRes
 * Author: Victor
 * Date: 2025/7/17 12:06
 * Description: 
 * -----------------------------------------------------------------
 */

open class BaseRes<T> {
    var count: Int = 0
    var total: Int = 0
    var refreshCount: Int = 0
    var lastStartId: Int = 0
    var nextPageUrl: String? = null
    var adExist: Boolean? = false
    var updateTime: String? = null
    var headerImage: String? = null

    var itemList:List<T>? = null
}