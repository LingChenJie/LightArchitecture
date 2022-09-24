package com.architecture.light.data.model.db.entity.convert

import androidx.room.TypeConverter
import com.android.architecture.extension.valid
import com.android.architecture.helper.JsonHelper
import com.architecture.light.data.remote.bean.SearchRoomResponse

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/9/23
 * Modify date: 2022/9/23
 * Version: 1
 */
class SearchRoomResponseConvert {

    @TypeConverter
    fun objectToString(response: SearchRoomResponse?): String {
        if (response != null) {
            val res = SearchRoomResponse()
            for (bean in response.data) {
                if (bean.isChecked) {
                    res.data.add(bean)
                    break
                }
            }
            return JsonHelper.toJson(res)
        }
        return ""
    }

    @TypeConverter
    fun stringToObject(json: String): SearchRoomResponse? {
        if (json.valid) {
            return JsonHelper.toBean(json)
        }
        return null
    }

}