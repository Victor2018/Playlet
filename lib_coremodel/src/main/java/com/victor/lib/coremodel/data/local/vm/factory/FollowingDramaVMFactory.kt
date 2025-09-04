package com.victor.lib.coremodel.data.local.vm.factory

import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.victor.lib.coremodel.data.local.repository.FollowingDramaRepository
import com.victor.lib.coremodel.data.local.vm.FollowingDramaVM
import org.victor.http.lib.vm.BaseVMFactory

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: FollowingDramaVMFactory
 * Author: Victor
 * Date: 2022/3/2 15:15
 * Description: 
 * -----------------------------------------------------------------
 */

class FollowingDramaVMFactory(
    private val repository: FollowingDramaRepository,
    owner: SavedStateRegistryOwner,
    private val userId: String
) : BaseVMFactory(owner) {
    override fun getVM(): ViewModel {
        return FollowingDramaVM(repository, userId)
    }
}