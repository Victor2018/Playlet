package com.victor.lib.coremodel.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: LikedDramaEntity
 * Author: Victor
 * Date: 2021/5/7 19:45
 * Description: 
 * -----------------------------------------------------------------
 */
@Entity(tableName = "tb_liked_drama")
data class LikedDramaEntity(
    @PrimaryKey @ColumnInfo(name = "id") var id: Int,
    @ColumnInfo(name = "dataType") var dataType: String?,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "category") var category: String,
    @ColumnInfo(name = "description") var description: String,
    @ColumnInfo(name = "playUrl") var playUrl: String,
    @ColumnInfo(name = "cover") var cover: String,
    @ColumnInfo(name = "duration") var duration: Int,
    @ColumnInfo(name = "user_id") var user_id: String,
    @ColumnInfo(name = "create_date") val create_date: Calendar = Calendar.getInstance()
)