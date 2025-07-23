package com.victor.lib.coremodel.data.local.converters

import androidx.room.TypeConverter
import java.util.*

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2020-2080, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: DateConverters
 * Author: Victor
 * Date: 2022/3/2 15:26
 * Description: 
 * -----------------------------------------------------------------
 */

class DateConverters {
    @TypeConverter
    fun calendarToDatestamp(calendar: Calendar): Long = calendar.timeInMillis

    @TypeConverter
    fun datestampToCalendar(value: Long): Calendar =
        Calendar.getInstance().apply { timeInMillis = value }
}