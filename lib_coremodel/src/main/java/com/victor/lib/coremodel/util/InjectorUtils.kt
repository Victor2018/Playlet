package com.victor.lib.coremodel.util

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.savedstate.SavedStateRegistryOwner
import com.victor.lib.coremodel.db.AppDatabase
import com.victor.lib.coremodel.db.repository.SearchKeywordRepository
import com.victor.lib.coremodel.vm.factory.*
import org.victor.http.lib.vm.BaseVMFactory

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: InjectorUtils
 * Author: Victor
 * Date: 2022/3/1 12:03
 * Description: 
 * -----------------------------------------------------------------
 */

object InjectorUtils {
    fun <T : ViewModel>provideFragmentVM(owner: ViewModelStoreOwner,
                                         factory: BaseVMFactory,
                                         clazz: Class<T>): T {
        return ViewModelProvider(owner, factory)[clazz]
    }

    fun getSearchKeywordRepository(context: Context): SearchKeywordRepository {
        return SearchKeywordRepository.getInstance(
            AppDatabase.getInstance(context.applicationContext))
    }
    fun provideSearchKeywordVMFactory(activity: AppCompatActivity, userId: String): SearchKeywordVMFactory {
        return SearchKeywordVMFactory(getSearchKeywordRepository(activity),activity,userId)
    }

    fun provideGankGirlVm(owner: SavedStateRegistryOwner): GankGirlVMFactory {
        return GankGirlVMFactory(owner)
    }



}