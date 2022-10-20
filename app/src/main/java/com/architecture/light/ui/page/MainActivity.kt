package com.architecture.light.ui.page

import android.content.Intent
import android.view.View
import com.android.architecture.extension.binding
import com.android.architecture.extension.click
import com.android.architecture.helper.AppExecutors
import com.android.architecture.helper.Logger
import com.android.architecture.utils.LogSaveUtils
import com.android.architecture.utils.ZipUtils
import com.architecture.light.app.AppActivity
import com.architecture.light.databinding.ActivityMainBinding
import com.architecture.light.domain.navigation.NavigationDemo
import com.architecture.light.ui.page.common.CommonActivity
import com.architecture.light.ui.page.mvi.MviActivity

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/8/11
 * Modify date: 2022/8/11
 * Version: 1
 */
class MainActivity : AppActivity() {

    private val binding: ActivityMainBinding by binding()

    override fun initView() {
        setContentView(binding.root)
        binding.titleView.apply {
            backIcon.visibility = View.GONE
        }
        binding.layoutMvi.click {
            startActivity(Intent(this, MviActivity::class.java))
        }
        binding.layoutCommon.click {
            startActivity(Intent(this, CommonActivity::class.java))
        }
        binding.layoutNavigation.click {
            NavigationDemo().execute()
        }
        binding.layoutTransaction.click {
//            TransactionDemo().execute()
            AppExecutors.getInstance().io().execute {
                val path = LogSaveUtils.getLogSavePath() + LogSaveUtils.getCurrentLogFileName()
                val re = ZipUtils.zip(path, LogSaveUtils.getLogSavePath() + "log.zip")
                Logger.e("aa", "re:$re")
            }
        }
    }

}