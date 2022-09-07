package com.architecture.light.ui.page.activity

import android.content.Intent
import android.view.View
import com.android.architecture.domain.transaction.TransactionConstant
import com.android.architecture.extension.click
import com.architecture.light.app.AppActivity
import com.architecture.light.databinding.ActivityMainBinding
import com.architecture.light.domain.transaction.LogonTrans
import com.architecture.light.domain.transaction.PaymentTrans
import com.architecture.light.settings.AccountCache

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/8/11
 * Modify date: 2022/8/11
 * Version: 1
 */
class MainActivity : AppActivity() {

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onResume() {
        super.onResume()
        TransactionConstant.getInstance().currentActivity = this
        if (!AccountCache.getLoginStatus()) {
            LogonTrans().execute()
        }
    }

    override fun initView() {
        setContentView(binding.root)
        binding.titleView.apply {
            backIcon.visibility = View.GONE
        }
        binding.layoutMvi.click {
//            thread {
//                SearchRoomTask().execute(GlobalParams.newTransData())
//            }
            PaymentTrans().execute()
        }
        binding.layoutCommon.click {
            startActivity(Intent(this, CommonActivity::class.java))
        }
    }

}