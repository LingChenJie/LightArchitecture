package com.architecture.light.domain.transaction.action.activity

import com.android.architecture.constant.ErrorCode
import com.android.architecture.domain.navigation.NavigationResult
import com.android.architecture.extension.binding
import com.android.architecture.extension.click
import com.architecture.light.app.AppActivityForNavigationAction
import com.architecture.light.databinding.Activity2Binding

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/10/13
 * Modify date: 2022/10/13
 * Version: 1
 */
class SecondActivity : AppActivityForNavigationAction() {

    private val binding: Activity2Binding by binding()

    override fun initView() {
        setContentView(binding.root)
        binding.btTo3.click {
            finish(NavigationResult(ErrorCode.SUCCESS))
        }
    }

}