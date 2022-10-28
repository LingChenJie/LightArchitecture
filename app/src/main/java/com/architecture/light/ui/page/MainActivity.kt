package com.architecture.light.ui.page

import android.view.View
import com.android.architecture.extension.binding
import com.android.architecture.extension.click
import com.android.architecture.extension.openActivity
import com.architecture.light.app.AppActivity
import com.architecture.light.databinding.ActivityMainBinding
import com.architecture.light.domain.navigation.NavigationDemo
import com.architecture.light.domain.transaction.TransactionDemo
import com.architecture.light.ui.page.common.CommonActivity
import com.architecture.light.ui.page.mvi.MviActivity
import com.architecture.light.ui.page.paging.RepoActivity
import com.architecture.light.ui.page.test.TestActivity

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
            openActivity<MviActivity>()
        }
        binding.layoutCommon.click {
            openActivity<CommonActivity>()
        }
        binding.layoutNavigation.click {
            NavigationDemo().execute()
        }
        binding.layoutTransaction.click {
            TransactionDemo().execute()
        }
        binding.layoutPaging.click {
            openActivity<RepoActivity>()
        }
        binding.layoutTest.click {
            openActivity<TestActivity>()
        }
    }

}