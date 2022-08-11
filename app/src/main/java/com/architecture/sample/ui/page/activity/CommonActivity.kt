package com.architecture.sample.ui.page.activity

import com.android.architecture.ui.page.BaseActivity
import com.architecture.sample.databinding.ActivityCommonBinding

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/7/29
 * Modify date: 2022/7/29
 * Version: 1
 */
class CommonActivity : BaseActivity() {

    private val binding: ActivityCommonBinding by lazy {
        ActivityCommonBinding.inflate(layoutInflater)
    }

    override fun initView() {
        setContentView(binding.root)
    }

}