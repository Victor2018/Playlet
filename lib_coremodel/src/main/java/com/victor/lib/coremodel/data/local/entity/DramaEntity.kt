package com.victor.lib.coremodel.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: SearchKeywordEntity
 * Author: Victor
 * Date: 2021/5/7 19:45
 * Description: 
 * -----------------------------------------------------------------
 */
@Entity(tableName = "tb_drama")
data class DramaEntity(
    @PrimaryKey @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "user_id") var user_id: String,
    @ColumnInfo(name = "dramaItemJson") var dramaItemJson: String,
    @ColumnInfo(name = "isHistory") var isHistory: Boolean,
    @ColumnInfo(name = "isFollowing") var isFollowing: Boolean,
    @ColumnInfo(name = "isLiked") var isLiked: Boolean,
    @ColumnInfo(name = "isPurchased") var isPurchased: Boolean,
    @ColumnInfo(name = "create_date") val create_date: Calendar = Calendar.getInstance()
//    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Long = 0L
)