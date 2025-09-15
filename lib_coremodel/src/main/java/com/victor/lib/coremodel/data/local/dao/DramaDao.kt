package com.victor.lib.coremodel.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.victor.lib.coremodel.data.local.entity.DramaEntity

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: FollowingDramaDao
 * Author: Victor
 * Date: 2021/5/7 19:50
 * Description: 
 * -----------------------------------------------------------------
 */
@Dao
interface DramaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: DramaEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(datas: List<DramaEntity>)

    @Delete
    suspend fun delete(entity: DramaEntity)

    @Delete
    suspend fun delete(datas: List<DramaEntity>)

    @Query("DELETE FROM tb_drama where id=:id AND user_id = :userId")
    suspend fun delete(id: String, userId: String)

    @Query("DELETE FROM tb_drama where user_id = :userId")
    suspend fun clearAll(userId: String)

    @Query("SELECT * FROM tb_drama where user_id = :userId AND id = :id ORDER BY create_date DESC LIMIT 1")
    suspend fun getById(userId: String,id: Int): DramaEntity?

    @Query("SELECT * FROM tb_drama where user_id = :userId ORDER BY create_date DESC")
    fun getAll(userId: String): LiveData<List<DramaEntity>>

    @Query("SELECT * FROM tb_drama where user_id = :userId AND is_history = true ORDER BY create_date DESC")
    fun getHistory(userId: String): LiveData<List<DramaEntity>>

    @Query("SELECT * FROM tb_drama where user_id = :userId AND is_following = true ORDER BY create_date DESC")
    fun getFollowing(userId: String): LiveData<List<DramaEntity>>

    @Query("SELECT * FROM tb_drama where user_id = :userId AND is_liked = true ORDER BY create_date DESC")
    fun getLiked(userId: String): LiveData<List<DramaEntity>>

    @Query("SELECT * FROM tb_drama where user_id = :userId AND is_purchased = true ORDER BY create_date DESC")
    fun getPurchased(userId: String): LiveData<List<DramaEntity>>

}