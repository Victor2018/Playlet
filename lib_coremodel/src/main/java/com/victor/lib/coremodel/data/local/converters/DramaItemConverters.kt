package com.victor.lib.coremodel.data.local.converters

import androidx.room.TypeConverter
import com.victor.lib.coremodel.data.remote.entity.bean.DramaItemInfo
import org.victor.http.lib.util.JsonUtils

class DramaItemConverters {

    @TypeConverter
    fun dramaItem2JsonStr(data: DramaItemInfo?): String? {
        val jsonStr = JsonUtils.toJSONString(data)
        return jsonStr
    }

    @TypeConverter
    fun jsonStr2DramaItem(json: String?): DramaItemInfo? {
        val item = JsonUtils.parseObject(json,DramaItemInfo::class.java)
        return item
    }
}