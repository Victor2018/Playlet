package com.victor.lib.coremodel.db.repository

import com.victor.lib.coremodel.db.AppDatabase
import com.victor.lib.coremodel.db.entity.SearchKeywordEntity


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: SearchKeywordRepository
 * Author: Victor
 * Date: 2021/5/8 10:58
 * Description: 
 * -----------------------------------------------------------------
 */
class SearchKeywordRepository constructor(private val db: AppDatabase) {
    val searchKeywordDao = db.searchKeywordDao()

    suspend fun insertSearchKeyword(searchKeywordEntity: SearchKeywordEntity) {
        searchKeywordDao.insertSearchKeyword(searchKeywordEntity)
    }

    suspend fun deleteSearchKeyword(searchKeywordEntity: SearchKeywordEntity) {
        searchKeywordDao.deleteSearchKeyword(searchKeywordEntity)
    }

    suspend fun deleteSearchKeyword(keyword: String, userId: String) {
        searchKeywordDao.deleteSearchKeyword(keyword,userId)
    }

    suspend fun clearAll(userId: String) {
        searchKeywordDao.clearAll(userId)
    }

    fun getSearchKeyword(userId: String) = searchKeywordDao.getSearchKeyword(userId)

    fun getAllSearchKeyword(userId: String) = searchKeywordDao.getAllSearchKeyword(userId)

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: SearchKeywordRepository? = null

        fun getInstance(db: AppDatabase) =
                instance ?: synchronized(this) {
                    instance ?: SearchKeywordRepository(db).also { instance = it }
                }
    }
}