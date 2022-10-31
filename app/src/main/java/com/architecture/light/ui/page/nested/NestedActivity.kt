package com.architecture.light.ui.page.nested

import com.android.architecture.extension.binding
import com.android.architecture.extension.click
import com.android.architecture.extension.openActivity
import com.architecture.light.app.AppActivity
import com.architecture.light.databinding.ActivityNestedBinding
import com.architecture.light.ui.page.nested.demo1.NestedDemo1Activity

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/10/31
 * Modify date: 2022/10/31
 * Version: 1
 */
class NestedActivity : AppActivity() {

    private val binding: ActivityNestedBinding by binding()

    override fun initView() {
        setContentView(binding.root)
        binding.btNestedDemo1.click { openActivity<NestedDemo1Activity>() }
    }

}