package com.victor.lib.coremodel.data.local.vm.factory

import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.victor.lib.coremodel.data.local.repository.DramaRepository
import com.victor.lib.coremodel.data.local.vm.DramaVM
import org.victor.http.lib.vm.BaseVMFactory

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: DramaVMFactory
 * Author: Victor
 * Date: 2022/3/2 15:15
 * Description: 
 * -----------------------------------------------------------------
 */

class DramaVMFactory(
    private val repository: DramaRepository,
    owner: SavedStateRegistryOwner,
    private val userId: String
) : BaseVMFactory(owner) {
    override fun getVM(): ViewModel {
        return DramaVM(repository, userId)
    }
}