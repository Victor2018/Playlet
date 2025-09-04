package com.victor.lib.coremodel.data.local.repository

import com.victor.lib.coremodel.data.local.AppDatabase
import com.victor.lib.coremodel.data.local.entity.PurchasedDramaEntity


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: PurchasedDramaRepository
 * Author: Victor
 * Date: 2021/5/8 10:58
 * Description: 
 * -----------------------------------------------------------------
 */
class PurchasedDramaRepository constructor(private val db: AppDatabase) {
    val dramaDao = db.purchasedDramaDao()

    suspend fun insert(entity: PurchasedDramaEntity) {
        dramaDao.insert(entity)
    }

    suspend fun delete(entity: PurchasedDramaEntity) {
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
        @Volatile private var instance: PurchasedDramaRepository? = null

        fun getInstance(db: AppDatabase) =
                instance ?: synchronized(this) {
                    instance ?: PurchasedDramaRepository(db).also { instance = it }
                }
    }
}