package com.victor.lib.coremodel.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.victor.lib.coremodel.data.local.entity.SearchKeywordEntity


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: SearchKeywordDao
 * Author: Victor
 * Date: 2021/5/7 19:50
 * Description: 
 * -----------------------------------------------------------------
 */
@Dao
interface SearchKeywordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearchKeyword(searchKeywordEntity: SearchKeywordEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(datas: List<SearchKeywordEntity>)

    @Delete
    suspend fun deleteSearchKeyword(searchKeywordEntity: SearchKeywordEntity)

    @Query("DELETE FROM tb_search_keyword where keyword=:keyword AND user_id = :userId")
    suspend fun deleteSearchKeyword(keyword: String, userId: String)

    @Query("DELETE FROM tb_search_keyword where user_id = :userId")
    suspend fun clearAll(userId: String)

    @Query("SELECT * FROM tb_search_keyword where user_id = :userId ORDER BY create_date DESC LIMIT 6")
    fun getSearchKeyword(userId: String): LiveData<List<SearchKeywordEntity>>

    @Query("SELECT * FROM tb_search_keyword where user_id = :userId")
    fun getAllSearchKeyword(userId: String): LiveData<List<SearchKeywordEntity>>
}