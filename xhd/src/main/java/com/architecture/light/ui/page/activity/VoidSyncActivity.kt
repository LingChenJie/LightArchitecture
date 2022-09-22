package com.architecture.light.ui.page.activity

import com.android.architecture.extension.click
import com.architecture.light.app.AppActivity
import com.architecture.light.databinding.ActivityVoidSyncBinding


/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/8/11
 * Modify date: 2022/8/11
 * Version: 1
 */
class VoidSyncActivity : AppActivity() {

    private val binding: ActivityVoidSyncBinding by lazy {
        ActivityVoidSyncBinding.inflate(
            layoutInflater
        )
    }

    override fun initView() {
        setContentView(binding.root)
        binding.titleView.backView.click { finish() }
    }

}