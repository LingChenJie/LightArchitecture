package com.architecture.light.settings

import com.android.architecture.extension.valid
import com.android.architecture.helper.CacheHelper
import com.architecture.light.settings.bean.LoginBean
import com.google.gson.Gson

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/5/9
 * Modify date: 2022/5/9
 * Version: 1
 */
object LoginCache {
    private const val KEY_LOGIN_BEAN = "key_login_bean_1"
    private var bean = initBean()

    private fun initBean(): LoginBean {
        try {
            val jsonString = CacheHelper.getString(KEY_LOGIN_BEAN)
            if (jsonString.valid) {
                return Gson().fromJson(jsonString, LoginBean::class.java)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return LoginBean()
    }

    private fun saveBean(): Boolean {
        val jsonString = Gson().toJson(bean)
        return CacheHelper.saveString(KEY_LOGIN_BEAN, jsonString)
    }

    fun saveLoginBean(bean: LoginBean): Boolean {
        this.bean = bean
        return saveBean()
    }

    fun getLoginBean(): LoginBean {
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

    fun getProjGUID() = bean.projGUID

    fun saveProjGUID(projGUID: String): Boolean {
        bean.projGUID = projGUID
        return saveBean()
    }

    fun getUserGUID() = bean.userGUID

    fun getLastLoginTime() = bean.lastLoginTime

    fun saveLastLoginTime(lastLoginTime: String): Boolean {
        bean.lastLoginTime = lastLoginTime
        return saveBean()
    }

}