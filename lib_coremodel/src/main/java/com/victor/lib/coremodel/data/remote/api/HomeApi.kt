package com.victor.lib.coremodel.data.remote.api

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: HomeApi
 * Author: Victor
 * Date: 2022/3/1 12:03
 * Description: 
 * -----------------------------------------------------------------
 */

object HomeApi {
    const val DRAMA_LIST = "api/v4/tabs/follow"
    const val HOT_RECOMMEND = "api/v4/rankList/videos?strategy=weekly"
    const val HOT_PLAY = "api/v4/rankList/videos?strategy=monthly"
    const val HOT_NEW = "api/v5/index/tab/feed"
    const val HOT_SEARCH = "api/v4/rankList/videos?strategy=historical"
    const val HOME_PLAYING = "api/v4/categories/videoList"
}