package com.victor.lib.coremodel.data.remote.entity.bean

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: ListData
 * Author: Victor
 * Date: 2022/3/1 18:28
 * Description: 
 * -----------------------------------------------------------------
 */

open class ListData<T> {
    var total: Int = 0//总记录数
    var pageNum: Int = 0//当前页号
    var pageSize: Int = 0//每页的数量
    var size: Int = 0//当前页的记录数量
    var pages: Int = 0//总页数
    var current: Int = 0
    var hitCount: Boolean = false
    var searchCount: Boolean = false
    var records: List<T>? = null
    var items: List<T>? = null
}
