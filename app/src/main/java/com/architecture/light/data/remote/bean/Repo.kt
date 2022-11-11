package com.architecture.light.data.remote.bean

import com.architecture.light.data.bean.BaseBean
import com.google.gson.annotations.SerializedName

/**
 * Created by SuQi on 2022/10/23.
 * Describe:
 */
data class Repo(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("html_url") val url: String,
    @SerializedName("description") val description: String?,
    @SerializedName("stargazers_count") val starCount: Int
) : BaseBean()