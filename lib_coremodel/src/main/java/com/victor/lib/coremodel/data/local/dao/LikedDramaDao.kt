package com.victor.lib.coremodel.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.victor.lib.coremodel.data.local.entity.LikedDramaEntity


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: LikedDramaDao
 * Author: Victor
 * Date: 2021/5/7 19:50
 * Description: 
 * -----------------------------------------------------------------
 */
@Dao
interface LikedDramaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: LikedDramaEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(datas: List<LikedDramaEntity>)

    @Delete
    suspend fun delete(entity: LikedDramaEntity)

    @Query("DELETE FROM tb_liked_drama where id=:id AND user_id = :userId")
    suspend fun delete(id: String, userId: String)

    @Query("DELETE FROM tb_liked_drama where user_id = :userId")
    suspend fun clearAll(userId: String)

    @Query("SELECT * FROM tb_liked_drama where user_id = :userId AND id = :id ORDER BY create_date DESC LIMIT 1")
    suspend fun getById(userId: String,id: Int): LikedDramaEntity?

    @Query("SELECT * FROM tb_liked_drama where user_id = :userId ORDER BY create_date DESC")
    fun getAll(userId: String): LiveData<List<LikedDramaEntity>>

}