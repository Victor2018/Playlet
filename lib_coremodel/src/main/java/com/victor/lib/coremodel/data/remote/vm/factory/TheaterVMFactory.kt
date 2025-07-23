package com.victor.lib.coremodel.data.remote.vm.factory

import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.victor.lib.coremodel.data.remote.datasource.TheaterDS
import com.victor.lib.coremodel.data.remote.vm.TheaterVM
import kotlinx.coroutines.Dispatchers
import org.victor.http.lib.vm.BaseVMFactory

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: TheaterVMFactory
 * Author: Victor
 * Date: 2021/2/24 17:32
 * Description: 
 * -----------------------------------------------------------------
 */
class TheaterVMFactory(owner: SavedStateRegistryOwner) : BaseVMFactory(owner) {

    override fun getVM(): ViewModel {
        return TheaterVM(TheaterDS(Dispatchers.IO))
    }

}