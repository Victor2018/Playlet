package com.victor.lib.common.interfaces

import com.victor.lib.common.data.ProgressInfo

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: OnDownloadProgressListener
 * Author: Victor
 * Date: 2022/3/1 18:28
 * Description: 
 * -----------------------------------------------------------------
 */

interface OnDownloadProgressListener {
    fun OnDownloadProgress(data: ProgressInfo?)
    fun OnDownloadCompleted(data: ProgressInfo?)
    fun OnError(error: String)
}