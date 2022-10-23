package com.architecture.light.data.remote.bean

import com.google.gson.annotations.SerializedName

/**
 * Created by SuQi on 2022/10/23.
 * Describe:
 */
class RepoResponse(
    @SerializedName("items") val items: List<Repo> = emptyList()
)