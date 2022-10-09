package com.architecture.light.data.model.db.entity.convert

import androidx.room.TypeConverter
import com.android.architecture.extension.valid
import com.android.architecture.helper.JsonHelper
import com.architecture.light.data.remote.bean.SearchReserveResponse

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/9/23
 * Modify date: 2022/9/23
 * Version: 1
 */
class SearchReserveResponseConvert {

    @TypeConverter
    fun objectToString(response: SearchReserveResponse?): String {
        if (response != null) {
            return JsonHelper.toJson(response)
        }
        return ""
    }

    @TypeConverter
    fun stringToObject(json: String): SearchReserveResponse? {
        if (json.valid) {
            return JsonHelper.toBean(json)
        }
        return null
    }

}