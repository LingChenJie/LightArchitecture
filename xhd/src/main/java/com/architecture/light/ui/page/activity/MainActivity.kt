package com.architecture.light.ui.page.activity

import android.content.Intent
import android.view.View
import com.android.architecture.extension.click
import com.architecture.light.app.AppActivity
import com.architecture.light.data.remote.bean.LoginRequest
import com.architecture.light.databinding.ActivityMainBinding
import com.architecture.light.domain.task.LoginTask
import kotlin.concurrent.thread

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/8/11
 * Modify date: 2022/8/11
 * Version: 1
 */
class MainActivity : AppActivity() {

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun initView() {
        setContentView(binding.root)
        binding.titleView.apply {
            backIcon.visibility = View.GONE
        }
        binding.layoutMvi.click {
            //startActivity(Intent(this, MviActivity::class.java))
            thread {
                val request = LoginRequest()
                request.userCode = "shengxy"
                request.password = "2"
                LoginTask().execute(request)
            }
        }
        binding.layoutCommon.click {
            startActivity(Intent(this, CommonActivity::class.java))
        }
    }

}