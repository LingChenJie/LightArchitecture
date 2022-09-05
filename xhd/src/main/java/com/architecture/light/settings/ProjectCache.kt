package com.architecture.light.settings

import com.android.architecture.extension.valid
import com.android.architecture.helper.CacheHelper
import com.android.architecture.helper.JsonHelper
import com.architecture.light.data.remote.bean.LoginResponse
import com.architecture.light.settings.bean.ProjectBean

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
                return JsonHelper.toBean(jsonString)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ProjectBean()
    }

    private fun saveBean(): Boolean {
        val jsonString = JsonHelper.toJson(bean)
        return CacheHelper.saveString(KEY_PROJECT_BEAN, jsonString)
    }

    fun getProjectList(): List<LoginResponse.DataBean.ProjectListBean>? {
        try {
            if (bean.projectListStr.valid) {
                return JsonHelper.toList(bean.projectListStr)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun saveProjectList(projectList: List<LoginResponse.DataBean.ProjectListBean>): Boolean {
        bean.projectListStr = JsonHelper.toJson(projectList)
        return saveBean()
    }

    fun getProject(): LoginResponse.DataBean.ProjectListBean? {
        try {
            if (bean.projectStr.valid) {
                return JsonHelper.toBean(bean.projectStr)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun saveProject(project: LoginResponse.DataBean.ProjectListBean): Boolean {
        bean.projectStr = JsonHelper.toJson(project)
        return saveBean()
    }
}
