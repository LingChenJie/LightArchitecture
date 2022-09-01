package com.architecture.light.domain.task

import com.architecture.light.data.remote.bean.LoginResponse

/**
 * Created by SuQi on 2022/9/1.
 * Describe:
 */
class LoginTask : HttpTask<LoginResponse>() {

    override fun unpack(response: String) {

    }


}