package com.victor.lib.coremodel.util

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.savedstate.SavedStateRegistryOwner
import com.victor.lib.coremodel.data.local.AppDatabase
import com.victor.lib.coremodel.data.local.repository.FollowingDramaRepository
import com.victor.lib.coremodel.data.local.repository.HistoryDramaRepository
import com.victor.lib.coremodel.data.local.repository.LikedDramaRepository
import com.victor.lib.coremodel.data.local.repository.PurchasedDramaRepository
import com.victor.lib.coremodel.data.local.repository.SearchKeywordRepository
import com.victor.lib.coremodel.data.local.vm.factory.FollowingDramaVMFactory
import com.victor.lib.coremodel.data.local.vm.factory.HistoryDramaVMFactory
import com.victor.lib.coremodel.data.local.vm.factory.LikedDramaVMFactory
import com.victor.lib.coremodel.data.local.vm.factory.PurchasedDramaVMFactory
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
        return SearchKeywordRepository.getInstance(AppDatabase.getInstance(context.applicationContext))
    }
    fun provideSearchKeywordVMFactory(activity: AppCompatActivity, userId: String): SearchKeywordVMFactory {
        return SearchKeywordVMFactory(getSearchKeywordRepository(activity),activity,userId)
    }

    fun getHistoryDramaRepository(context: Context): HistoryDramaRepository {
        return HistoryDramaRepository.getInstance(AppDatabase.getInstance(context.applicationContext))
    }
    fun provideHistoryDramaVMFactory(activity: AppCompatActivity, userId: String): HistoryDramaVMFactory {
        return HistoryDramaVMFactory(getHistoryDramaRepository(activity),activity,userId)
    }

    fun getFollowingDramaRepository(context: Context): FollowingDramaRepository {
        return FollowingDramaRepository.getInstance(AppDatabase.getInstance(context.applicationContext))
    }
    fun provideFollowingDramaVMFactory(activity: AppCompatActivity, userId: String): FollowingDramaVMFactory {
        return FollowingDramaVMFactory(getFollowingDramaRepository(activity), activity, userId)
    }

    fun getLikedDramaRepository(context: Context): LikedDramaRepository {
        return LikedDramaRepository.getInstance(AppDatabase.getInstance(context.applicationContext))
    }
    fun provideLikedDramaVMFactory(activity: AppCompatActivity, userId: String): LikedDramaVMFactory {
        return LikedDramaVMFactory(getLikedDramaRepository(activity),activity,userId)
    }

    fun getPurchasedDramaRepository(context: Context): PurchasedDramaRepository {
        return PurchasedDramaRepository.getInstance(AppDatabase.getInstance(context.applicationContext))
    }
    fun providePurchasedDramaVMFactory(activity: AppCompatActivity, userId: String): PurchasedDramaVMFactory {
        return PurchasedDramaVMFactory(getPurchasedDramaRepository(activity),activity,userId)
    }

    fun provideHomeVMFactory(owner: SavedStateRegistryOwner): HomeVMFactory {
        return HomeVMFactory(owner)
    }

    fun provideTheaterVMFactory(owner: SavedStateRegistryOwner): TheaterVMFactory {
        return TheaterVMFactory(owner)
    }



}