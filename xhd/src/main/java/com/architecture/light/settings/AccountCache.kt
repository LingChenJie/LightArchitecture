package com.architecture.light.settings

import com.android.architecture.extension.valid
import com.android.architecture.helper.CacheHelper
import com.android.architecture.helper.JsonHelper
import com.architecture.light.settings.bean.AccountBean

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/5/9
 * Modify date: 2022/5/9
 * Version: 1
 */
object AccountCache {
    private const val KEY_LOGIN_BEAN = "key_login_bean_1"
    private var bean = initBean()

    private fun initBean(): AccountBean {
        try {
            val jsonString = CacheHelper.getString(KEY_LOGIN_BEAN)
            if (jsonString.valid) {
                return JsonHelper.toBean(jsonString)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return AccountBean()
    }

    private fun saveBean(): Boolean {
        val jsonString = JsonHelper.toJson(bean)
        return CacheHelper.saveString(KEY_LOGIN_BEAN, jsonString)
    }

    fun saveLoginBean(bean: AccountBean): Boolean {
        this.bean = bean
        return saveBean()
    }

    fun getLoginBean(): AccountBean {
        return bean
    }

    fun getAccount() = bean.account

    fun saveAccount(account: String): Boolean {
        bean.account = account
        return saveBean()
    }

    fun getPassword() = bean.password

    fun savePassword(password: String): Boolean {
        bean.password = password
        return saveBean()
    }

    fun getUsername() = bean.username

    fun saveUsername(userGUID: String): Boolean {
        bean.userGUID = userGUID
        return saveBean()
    }

    fun saveUserGUID(userGUID: String): Boolean {
        bean.userGUID = userGUID
        return saveBean()
    }

    fun getUserGUID() = bean.userGUID

    fun saveBillRecipient(billRecipient: String): Boolean {
        bean.billRecipient = billRecipient
        return saveBean()
    }

    fun getBillRecipient() = bean.billRecipient

    fun getLoginStatus() = bean.loginStatus

    fun saveLoginStatus(loginStatus: Boolean): Boolean {
        bean.loginStatus = loginStatus
        return saveBean()
    }

    fun getLastLoginTime() = bean.lastLoginTime

    fun saveLastLoginTime(lastLoginTime: String): Boolean {
        bean.lastLoginTime = lastLoginTime
        return saveBean()
    }

}