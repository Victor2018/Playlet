package com.victor.lib.coremodel.data.local.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.victor.lib.coremodel.data.local.entity.SearchKeywordEntity
import com.victor.lib.coremodel.data.local.repository.SearchKeywordRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: SearchKeywordVM
 * Author: Victor
 * Date: 2022/3/2 15:14
 * Description: 
 * -----------------------------------------------------------------
 */

class SearchKeywordVM(
    private val repository: SearchKeywordRepository,
    private val userId: String
) : ViewModel() {

    val searchKeywordData = repository.getSearchKeyword(userId)

    val allSearchKeywordData = repository.getAllSearchKeyword(userId)

    fun insertSearchKeyword(data: SearchKeywordEntity) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.insertSearchKeyword(data)
            }
        }
    }

    fun deleteSearchKeyword(data: SearchKeywordEntity) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.deleteSearchKeyword(data)
            }
        }
    }

    fun deleteSearchKeyword(keyword: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.deleteSearchKeyword(keyword, userId)
            }
        }
    }

    fun clearAllSearchKeyword() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.clearAll(userId)
            }
        }
    }

}