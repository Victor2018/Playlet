package com.victor.lib.coremodel.data.remote.api

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: TheaterApi
 * Author: Victor
 * Date: 2022/3/1 12:03
 * Description: 
 * -----------------------------------------------------------------
 */

object TheaterApi {
    const val RANKING = "api/v6/community/tab/follow"
    const val FOUND = "api/v5/index/tab/allRec"
    const val HOT_RECOMMEND = "api/v4/rankList/videos?strategy=weekly"
    const val HOT_PLAY = "api/v4/rankList/videos?strategy=monthly"
    const val HOT_NEW = "api/v5/index/tab/feed"
    const val HOT_SEARCH = "api/v4/rankList/videos?strategy=historical"
}