package com.architecture.light.settings

import com.android.architecture.extension.valid
import com.android.architecture.helper.CacheHelper
import com.android.architecture.helper.JsonHelper
import com.architecture.light.settings.bean.PayBean

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/5/9
 * Modify date: 2022/5/9
 * Version: 1
 */
object PayCache {
    private const val KEY_PAY_BEAN = "key_pay_bean_1"
    private var bean = initBean()

    private fun initBean(): PayBean {
        try {
            val jsonString = CacheHelper.getString(KEY_PAY_BEAN)
            if (jsonString.valid) {
                return JsonHelper.toBean(jsonString)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return PayBean()
    }

    private fun saveBean(): Boolean {
        val jsonString = JsonHelper.toJson(bean)
        return CacheHelper.saveString(KEY_PAY_BEAN, jsonString)
    }

    fun savePayBean(bean: PayBean): Boolean {
        this.bean = bean
        return saveBean()
    }

    fun getPayBean(): PayBean {
        return bean
    }

    fun getPosConnMode() = bean.posConnMode

    fun savePosConnMode(posConnMode: Int): Boolean {
        bean.posConnMode = posConnMode
        return saveBean()
    }

    fun getIp() = bean.ip

    fun saveIp(ip: String): Boolean {
        bean.ip = ip
        return saveBean()
    }

    fun getPort() = bean.port

    fun savePort(port: String): Boolean {
        bean.port = port
        return saveBean()
    }

    fun getComNo() = bean.comNo

    fun saveComNo(comNo: String): Boolean {
        bean.comNo = comNo
        return saveBean()
    }

    fun getBoundNo() = bean.boundNo

    fun saveBoundNo(boundNo: Int): Boolean {
        bean.boundNo = boundNo
        return saveBean()
    }

}