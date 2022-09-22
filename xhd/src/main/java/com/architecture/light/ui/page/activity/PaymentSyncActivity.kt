package com.architecture.light.ui.page.activity

import com.android.architecture.extension.click
import com.architecture.light.app.AppActivity
import com.architecture.light.databinding.ActivityPaymentSyncBinding


/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/8/11
 * Modify date: 2022/8/11
 * Version: 1
 */
class PaymentSyncActivity : AppActivity() {

    private val binding: ActivityPaymentSyncBinding by lazy {
        ActivityPaymentSyncBinding.inflate(
            layoutInflater
        )
    }

    override fun initView() {
        setContentView(binding.root)
        binding.titleView.backView.click { finish() }
    }

}