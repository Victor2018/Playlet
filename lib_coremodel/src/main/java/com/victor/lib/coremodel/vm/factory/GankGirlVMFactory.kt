package com.victor.lib.coremodel.vm.factory

import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.victor.lib.coremodel.datasource.GankGirlDS
import kotlinx.coroutines.Dispatchers
import org.victor.http.lib.vm.BaseVMFactory
import com.victor.lib.coremodel.vm.GankGirlVM


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: GankGirlVMFactory
 * Author: Victor
 * Date: 2021/2/24 17:32
 * Description: 
 * -----------------------------------------------------------------
 */
class GankGirlVMFactory(owner: SavedStateRegistryOwner) : BaseVMFactory(owner) {

    override fun getVM(): ViewModel {
        return GankGirlVM(GankGirlDS(Dispatchers.IO))
    }

}