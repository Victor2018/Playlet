package com.victor.lib.coremodel.util

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.savedstate.SavedStateRegistryOwner
import com.victor.lib.coremodel.data.local.AppDatabase
import com.victor.lib.coremodel.data.local.repository.SearchKeywordRepository
import com.victor.lib.coremodel.data.local.vm.factory.SearchKeywordVMFactory
import com.victor.lib.coremodel.data.remote.vm.factory.HomeVMFactory
import com.victor.lib.coremodel.data.remote.vm.factory.TheaterVMFactory
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

    fun provideHomeVM(owner: SavedStateRegistryOwner): HomeVMFactory {
        return HomeVMFactory(owner)
    }

    fun provideTheaterVM(owner: SavedStateRegistryOwner): TheaterVMFactory {
        return TheaterVMFactory(owner)
    }



}