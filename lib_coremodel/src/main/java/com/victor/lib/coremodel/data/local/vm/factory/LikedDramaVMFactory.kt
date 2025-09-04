package com.victor.lib.coremodel.data.local.vm.factory

import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.victor.lib.coremodel.data.local.repository.LikedDramaRepository
import com.victor.lib.coremodel.data.local.vm.LikedDramaVM
import org.victor.http.lib.vm.BaseVMFactory

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: LikedDramaVMFactory
 * Author: Victor
 * Date: 2022/3/2 15:15
 * Description: 
 * -----------------------------------------------------------------
 */

class LikedDramaVMFactory(
    private val repository: LikedDramaRepository,
    owner: SavedStateRegistryOwner,
    private val userId: String
) : BaseVMFactory(owner) {
    override fun getVM(): ViewModel {
        return LikedDramaVM(repository, userId)
    }
}