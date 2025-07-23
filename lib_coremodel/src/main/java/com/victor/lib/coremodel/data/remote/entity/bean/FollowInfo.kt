package com.victor.lib.coremodel.data.remote.entity.bean

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: FollowInfo.java
 * Author: Victor
 * Date: 2019/11/1 9:58
 * Description:
 * -----------------------------------------------------------------
 */
class FollowInfo {
    var count: Int = 0
    var dataType: String? = null
    var adTrack: String? = null
    var footer: String? = null
    var header: com.victor.lib.coremodel.data.remote.entity.bean.FollowHeader? = null
    var itemList: List<com.victor.lib.coremodel.data.remote.entity.bean.HomeItemInfo>? = null
}