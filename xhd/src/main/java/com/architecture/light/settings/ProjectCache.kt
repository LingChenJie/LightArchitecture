package com.architecture.light.settings

import com.android.architecture.extension.valid
import com.android.architecture.helper.CacheHelper
import com.architecture.light.data.remote.bean.LoginResponse
import com.architecture.light.helper.GsonHelper
import com.architecture.light.settings.bean.ProjectBean
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/5/9
 * Modify date: 2022/5/9
 * Version: 1
 */
object ProjectCache {
    private const val KEY_PROJECT_BEAN = "key_project_bean_1"
    private var bean = initBean()

    private fun initBean(): ProjectBean {
        try {
            val jsonString = CacheHelper.getString(KEY_PROJECT_BEAN)
            if (jsonString.valid) {
                return Gson().fromJson(jsonString, ProjectBean::class.java)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ProjectBean()
    }

    private fun saveBean(): Boolean {
        val jsonString = Gson().toJson(bean)
        return CacheHelper.saveString(KEY_PROJECT_BEAN, jsonString)
    }

    fun getProjectList(): List<LoginResponse.DataBean.ProjectListBean> {
        val type = object : TypeToken<List<LoginResponse.DataBean.ProjectListBean>>() {}.type
        return GsonHelper.gson.fromJson(bean.projectListStr, type)
    }

    fun saveProjectList(projectList: List<LoginResponse.DataBean.ProjectListBean>): Boolean {
        bean.projectListStr = GsonHelper.gson.toJson(projectList)
        return saveBean()
    }

    fun getProject(): LoginResponse.DataBean.ProjectListBean? {
        try {
            val type = object : TypeToken<LoginResponse.DataBean.ProjectListBean>() {}.type
            return GsonHelper.gson.fromJson(bean.projectStr, type)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun saveProject(project: LoginResponse.DataBean.ProjectListBean): Boolean {
        bean.projectStr = GsonHelper.gson.toJson(project)
        return saveBean()
    }
}
