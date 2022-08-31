package com.light.xhd.domain.task

import com.light.xhd.data.remote.bean.LoginResponse

/**
 * Created by SuQi on 2022/9/1.
 * Describe:
 */
class LoginTask : HttpTask<LoginResponse>() {

    override fun unpack(response: String) {

    }


}