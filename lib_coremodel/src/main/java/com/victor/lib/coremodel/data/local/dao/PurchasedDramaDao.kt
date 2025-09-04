package com.victor.lib.coremodel.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.victor.lib.coremodel.data.local.entity.PurchasedDramaEntity


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: PurchasedDramaDao
 * Author: Victor
 * Date: 2021/5/7 19:50
 * Description: 
 * -----------------------------------------------------------------
 */
@Dao
interface PurchasedDramaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: PurchasedDramaEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(datas: List<PurchasedDramaEntity>)

    @Delete
    suspend fun delete(entity: PurchasedDramaEntity)

    @Query("DELETE FROM tb_purchased_drama where id=:id AND user_id = :userId")
    suspend fun delete(id: String, userId: String)

    @Query("DELETE FROM tb_purchased_drama where user_id = :userId")
    suspend fun clearAll(userId: String)

    @Query("SELECT * FROM tb_purchased_drama where user_id = :userId AND id = :id ORDER BY create_date DESC LIMIT 1")
    suspend fun getById(userId: String,id: Int): PurchasedDramaEntity?

    @Query("SELECT * FROM tb_purchased_drama where user_id = :userId ORDER BY create_date DESC")
    fun getAll(userId: String): LiveData<List<PurchasedDramaEntity>>

}