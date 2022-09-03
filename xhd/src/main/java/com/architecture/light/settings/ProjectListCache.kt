package com.architecture.light.settings

import com.android.architecture.extension.valid
import com.android.architecture.helper.CacheHelper
import com.architecture.light.data.remote.bean.LoginResponse
import com.architecture.light.settings.bean.ProjectListBean
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/5/9
 * Modify date: 2022/5/9
 * Version: 1
 */
object ProjectListCache {
    private const val KEY_PROJECT_LIST_BEAN = "key_project_list_bean_1"
    private var bean = initBean()

    private fun initBean(): ProjectListBean {
        try {
            val jsonString = CacheHelper.getString(KEY_PROJECT_LIST_BEAN)
            if (jsonString.valid) {
                return Gson().fromJson(jsonString, ProjectListBean::class.java)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ProjectListBean()
    }

    private fun saveBean(): Boolean {
        val jsonString = Gson().toJson(bean)
        return CacheHelper.saveString(KEY_PROJECT_LIST_BEAN, jsonString)
    }

    fun getProjectList(): List<LoginResponse.DataBean.ProjectListBean> {
        val type = object : TypeToken<List<LoginResponse.DataBean.ProjectListBean>>() {}.type
        return Gson().fromJson(bean.projectListStr, type)
    }

    fun saveProjectList(projectListStr: List<LoginResponse.DataBean.ProjectListBean>): Boolean {
        bean.projectListStr = Gson().toJson(projectListStr)
        return saveBean()
    }
}
