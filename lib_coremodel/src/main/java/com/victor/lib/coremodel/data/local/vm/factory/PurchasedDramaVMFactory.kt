package com.victor.lib.coremodel.data.local.vm.factory

import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.victor.lib.coremodel.data.local.repository.PurchasedDramaRepository
import com.victor.lib.coremodel.data.local.vm.PurchasedDramaVM
import org.victor.http.lib.vm.BaseVMFactory

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: PurchasedDramaVMFactory
 * Author: Victor
 * Date: 2022/3/2 15:15
 * Description: 
 * -----------------------------------------------------------------
 */

class PurchasedDramaVMFactory(
    private val repository: PurchasedDramaRepository,
    owner: SavedStateRegistryOwner,
    private val userId: String
) : BaseVMFactory(owner) {
    override fun getVM(): ViewModel {
        return PurchasedDramaVM(repository, userId)
    }
}