package com.victor.lib.common.interfaces

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: IPlayer
 * Author: Victor
 * Date: 2022/7/19 16:46
 * Description: 
 * -----------------------------------------------------------------
 */

interface IPlayer {
    fun isPlaying(): Boolean
    fun getCurrentPosition(): Long
    fun getBufferPercentage (): Long
    fun getCurrentDownloadSpeed (): Long
    fun getDuration(): Long
    fun getLastPlayUrl(): String?
    fun replayByUser ()
    fun pause()
    fun resume()
    fun stop ()
    fun seekTo(position: Long)
    fun selectTrack(index: Int)
}