package com.victor.lib.common.interfaces

import com.victor.lib.coremodel.data.local.vm.FollowingDramaVM
import com.victor.lib.coremodel.data.local.vm.HistoryDramaVM
import com.victor.lib.coremodel.data.local.vm.LikedDramaVM
import com.victor.lib.coremodel.data.local.vm.PurchasedDramaVM

interface IDramaVM {
    fun getHistoryDramaVM(): HistoryDramaVM
    fun getFollowingDramaVM(): FollowingDramaVM
    fun getLikedDramaVM(): LikedDramaVM
    fun getPurchasedDramaVM(): PurchasedDramaVM
}