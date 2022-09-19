package com.architecture.light.constant

import com.android.architecture.helper.DateHelper
import com.android.architecture.helper.RandomHelper
import com.architecture.light.data.model.UserInfoModel
import com.architecture.light.data.model.db.entity.TransData
import com.architecture.light.data.model.db.entity.UserInfo
import com.architecture.light.settings.AccountCache
import com.architecture.light.settings.ProjectCache

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/9/1
 * Modify date: 2022/9/1
 * Version: 1
 */
object GlobalParams {

    private var userInfo: UserInfo? = null
    private var transData: TransData? = null

    fun setUserInfo(userInfo: UserInfo) {
        GlobalParams.userInfo = userInfo
    }

    fun getUserInfo(): UserInfo {
        if (userInfo == null) {
            userInfo = UserInfoModel.queryUserInfoByUsername(AccountCache.getUsername())!!
        }
        return userInfo!!
    }

    fun initTransData(): TransData {
        transData = newTransData()
        return transData!!
    }

    fun getTransData(): TransData {
        return transData!!
    }

    fun resetTransData() {
        transData = null
    }

    private fun newTransData(): TransData {
        val transData = TransData()
        transData.account = AccountCache.getAccount()
        transData.zygwGUID = AccountCache.getUserGUID()
        if (ProjectCache.getProject() != null) {
            transData.projGUID = ProjectCache.getProject()!!.projGUID
        }
        transData.orderNumber =
            DateHelper.MMDDYYYY + DateHelper.timeString + RandomHelper.getRandomHexString(3)
        return transData
    }

}