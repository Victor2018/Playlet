package com.victor.lib.coremodel.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.victor.lib.coremodel.data.local.entity.HistoryDramaEntity


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: HistoryDramaDao
 * Author: Victor
 * Date: 2021/5/7 19:50
 * Description: 
 * -----------------------------------------------------------------
 */
@Dao
interface HistoryDramaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: HistoryDramaEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(datas: List<HistoryDramaEntity>)

    @Delete
    suspend fun delete(entity: HistoryDramaEntity)

    @Query("DELETE FROM tb_history_drama where id=:id AND user_id = :userId")
    suspend fun delete(id: String, userId: String)

    @Query("DELETE FROM tb_history_drama where user_id = :userId")
    suspend fun clearAll(userId: String)

    @Query("SELECT * FROM tb_history_drama where user_id = :userId AND id = :id ORDER BY create_date DESC LIMIT 1")
    suspend fun getById(userId: String,id: Int): HistoryDramaEntity?

    @Query("SELECT * FROM tb_history_drama where user_id = :userId ORDER BY create_date DESC")
    fun getAll(userId: String): LiveData<List<HistoryDramaEntity>>

}