package com.victor.lib.coremodel.data.local.repository

import com.victor.lib.coremodel.data.local.AppDatabase
import com.victor.lib.coremodel.data.local.entity.DramaEntity


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
class DramaRepository constructor(private val db: AppDatabase) {
    val dramaDao = db.dramaDao()

    suspend fun insert(entity: DramaEntity) {
        dramaDao.insert(entity)
    }

    suspend fun delete(entity: DramaEntity) {
        dramaDao.delete(entity)
    }

    suspend fun delete(id: String, userId: String) {
        dramaDao.delete(id,userId)
    }

    suspend fun clearAll(userId: String) {
        dramaDao.clearAll(userId)
    }

    suspend fun getById(userId: String, id: Int) = dramaDao.getById(userId, id)

    fun getAll(userId: String) = dramaDao.getAll(userId)
    fun getHistory(userId: String) = dramaDao.getHistory(userId)
    fun getFollowing(userId: String) = dramaDao.getFollowing(userId)
    fun getLiked(userId: String) = dramaDao.getLiked(userId)
    fun getPurchased(userId: String) = dramaDao.getPurchased(userId)

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: DramaRepository? = null

        fun getInstance(db: AppDatabase) =
                instance ?: synchronized(this) {
                    instance ?: DramaRepository(db).also { instance = it }
                }
    }
}