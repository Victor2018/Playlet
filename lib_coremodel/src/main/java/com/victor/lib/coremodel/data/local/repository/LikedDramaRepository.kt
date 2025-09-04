package com.victor.lib.coremodel.data.local.repository

import com.victor.lib.coremodel.data.local.AppDatabase
import com.victor.lib.coremodel.data.local.entity.LikedDramaEntity


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
class LikedDramaRepository constructor(private val db: AppDatabase) {
    val dramaDao = db.likedDramaDao()

    suspend fun insert(entity: LikedDramaEntity) {
        dramaDao.insert(entity)
    }

    suspend fun delete(entity: LikedDramaEntity) {
        dramaDao.delete(entity)
    }

    suspend fun delete(id: String, userId: String) {
        dramaDao.delete(id,userId)
    }

    suspend fun clearAll(userId: String) {
        dramaDao.clearAll(userId)
    }

    fun getAll(userId: String) = dramaDao.getAll(userId)

    suspend fun getById(userId: String, id: Int) = dramaDao.getById(userId, id)

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: LikedDramaRepository? = null

        fun getInstance(db: AppDatabase) =
                instance ?: synchronized(this) {
                    instance ?: LikedDramaRepository(db).also { instance = it }
                }
    }
}