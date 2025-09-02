package com.victor.lib.coremodel.data.remote.entity.bean

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: LatestVersionData
 * Author: Victor
 * Date: 2022/3/1 18:28
 * Description: 
 * -----------------------------------------------------------------
 */

class LatestVersionData {
    var content: String? = null//更新内容
    var downloadUrl: String? = null//下载链接
    var title: String? = null//更新标题
    var updateSetting: Int = 0//更新场景 1：强制更新 2：可选更新 3：不更新
    var version: String? = null//版本号
}