package com.architecture.light.ui.page.test

import com.android.architecture.extension.binding
import com.architecture.light.app.AppActivity
import com.architecture.light.databinding.ActivityTestBinding

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/10/28
 * Modify date: 2022/10/28
 * Version: 1
 */
class TestActivity : AppActivity() {

    private val binding: ActivityTestBinding by binding()

    override fun initView() {
        setContentView(binding.root)
        val list = mutableListOf<ByteArray>()
        for (i in 0..Int.MAX_VALUE) {
            val array = ByteArray(1024)
            list.add(array)
        }
    }

}