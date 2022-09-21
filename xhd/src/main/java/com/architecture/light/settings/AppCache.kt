package com.architecture.light.settings

import com.android.architecture.extension.valid
import com.android.architecture.helper.CacheHelper
import com.android.architecture.helper.JsonHelper
import com.architecture.light.settings.bean.AppBean
import com.architecture.light.settings.bean.PayBean

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/5/9
 * Modify date: 2022/5/9
 * Version: 1
 */
object AppCache {
    private const val KEY_APP_BEAN = "key_app_bean_1"
    private var bean = initBean()

    private fun initBean(): AppBean {
        try {
            val jsonString = CacheHelper.getString(KEY_APP_BEAN)
            if (jsonString.valid) {
                return JsonHelper.toBean(jsonString)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return AppBean()
    }

    private fun saveBean(): Boolean {
        val jsonString = JsonHelper.toJson(bean)
        return CacheHelper.saveString(KEY_APP_BEAN, jsonString)
    }

    fun savePayBean(bean: AppBean): Boolean {
        this.bean = bean
        return saveBean()
    }

    fun getPayBean(): AppBean {
        return bean
    }

    fun getManagePwd() = bean.managePwd

    fun saveManagePwd(managePwd: String): Boolean {
        bean.managePwd = managePwd
        return saveBean()
    }

}