package com.victor.lib.coremodel.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.victor.lib.coremodel.data.remote.entity.bean.DramaItemInfo
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
    @ColumnInfo(name = "drama_item") var dramaItem: DramaItemInfo?,
    @ColumnInfo(name = "play_position") var playPosition: Int,
    @ColumnInfo(name = "is_history") var isHistory: Boolean,
    @ColumnInfo(name = "is_following") var isFollowing: Boolean,
    @ColumnInfo(name = "is_liked") var isLiked: Boolean,
    @ColumnInfo(name = "is_purchased") var isPurchased: Boolean,
    @ColumnInfo(name = "create_date") val create_date: Calendar = Calendar.getInstance()
//    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Long = 0L
)