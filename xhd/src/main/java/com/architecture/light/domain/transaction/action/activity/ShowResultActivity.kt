package com.architecture.light.domain.transaction.action.activity

import com.architecture.light.app.AppActivityForAction
import com.architecture.light.databinding.ActivityShowResultBinding

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/9/1
 * Modify date: 2022/9/1
 * Version: 1
 */
class ShowResultActivity : AppActivityForAction() {

    private val binding: ActivityShowResultBinding by lazy {
        ActivityShowResultBinding.inflate(layoutInflater)
    }


    override fun initView() {
        setContentView(binding.root)
    }

}