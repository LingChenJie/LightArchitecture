package com.architecture.sample.ui.page

import com.android.architecture.extension.click
import com.android.architecture.ui.page.BaseActivity
import com.architecture.sample.databinding.ActivityHomeBinding
import com.architecture.sample.ui.dialog.MessageDialog

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/8/11
 * Modify date: 2022/8/11
 * Version: 1
 */
class HomeActivity : BaseActivity() {

    private val binding: ActivityHomeBinding by lazy { ActivityHomeBinding.inflate(layoutInflater) }

    override fun initView() {
        setContentView(binding.root)
        binding.btn.click {
            MessageDialog.Builder(this)
                .title("标题")
                .message("我是一个消息")
                .create()
                .show()
        }
    }

}