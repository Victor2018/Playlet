package com.victor.lib.coremodel.data.local.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.victor.lib.coremodel.data.local.entity.DramaEntity
import com.victor.lib.coremodel.data.local.repository.DramaRepository
import com.victor.lib.coremodel.data.remote.entity.bean.DramaType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: DramaVM
 * Author: Victor
 * Date: 2022/3/2 15:14
 * Description: 
 * -----------------------------------------------------------------
 */

class DramaVM(
    private val repository: DramaRepository,
    private val userId: String
) : ViewModel() {

    val dramaData = repository.getAll(userId)

    val historyDramaData = repository.getByType(userId,DramaType.HISTORY.value)

    val followingDramaData = repository.getByType(userId,DramaType.FOLLOWING.value)

    val likesDramaData = repository.getByType(userId,DramaType.LIKE.value)

    fun insert(data: DramaEntity) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.insert(data)
            }
        }
    }

    fun delete(data: DramaEntity) {
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