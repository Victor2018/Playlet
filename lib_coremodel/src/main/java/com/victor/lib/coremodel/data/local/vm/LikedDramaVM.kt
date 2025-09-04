package com.victor.lib.coremodel.data.local.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.victor.lib.coremodel.data.local.entity.LikedDramaEntity
import com.victor.lib.coremodel.data.local.repository.LikedDramaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.victor.http.lib.util.JsonUtils

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: LikedDramaVM
 * Author: Victor
 * Date: 2022/3/2 15:14
 * Description: 
 * -----------------------------------------------------------------
 */

class LikedDramaVM(
    private val repository: LikedDramaRepository,
    private val userId: String
) : ViewModel() {

    val dramaData = repository.getAll(userId)

    fun getById(id: Int,callback: (LikedDramaEntity?) -> Unit) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val entity = repository.getById(userId,id)
                Log.e(javaClass.simpleName,"getExportWorker-videoInfo = ${JsonUtils.toJSONString(entity)}")
                withContext(Dispatchers.Main) {
                    callback.invoke(entity)
                }
            }
        }
    }

    fun insert(data: LikedDramaEntity) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.insert(data)
            }
        }
    }

    fun delete(data: LikedDramaEntity) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.delete(data)
            }
        }
    }

    fun delete(id: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.delete(id, userId)
            }
        }
    }

    fun clearAll() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.clearAll(userId)
            }
        }
    }

}