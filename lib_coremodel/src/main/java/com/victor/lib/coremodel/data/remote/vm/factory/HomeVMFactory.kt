package com.victor.lib.coremodel.data.remote.vm.factory

import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.victor.lib.coremodel.data.remote.datasource.HomeDS
import com.victor.lib.coremodel.data.remote.vm.HomeVM
import kotlinx.coroutines.Dispatchers
import org.victor.http.lib.vm.BaseVMFactory

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: HomeVMFactory
 * Author: Victor
 * Date: 2021/2/24 17:32
 * Description: 
 * -----------------------------------------------------------------
 */
class HomeVMFactory(owner: SavedStateRegistryOwner) : BaseVMFactory(owner) {

    override fun getVM(): ViewModel {
        return HomeVM(HomeDS(Dispatchers.IO))
    }

}