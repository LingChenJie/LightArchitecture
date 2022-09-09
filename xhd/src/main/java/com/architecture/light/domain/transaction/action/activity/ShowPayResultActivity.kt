package com.architecture.light.domain.transaction.action.activity

import com.architecture.light.app.AppActivityForAction
import com.architecture.light.databinding.ActivityShowPayResultBinding

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/9/1
 * Modify date: 2022/9/1
 * Version: 1
 */
class ShowPayResultActivity : AppActivityForAction() {

    private val binding: ActivityShowPayResultBinding by lazy {
        ActivityShowPayResultBinding.inflate(layoutInflater)
    }


    override fun initView() {
        setContentView(binding.root)
    }

}