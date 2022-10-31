package com.architecture.light.ui.page.nested.demo1

import com.android.architecture.extension.binding
import com.architecture.light.app.AppActivity
import com.architecture.light.databinding.ActivityNestedDemo1Binding
import com.architecture.light.ui.page.nested.NestedAdapter

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/10/31
 * Modify date: 2022/10/31
 * Version: 1
 */
class NestedDemo1Activity : AppActivity() {

    private val binding: ActivityNestedDemo1Binding by binding()
    private val adapter by lazy { NestedAdapter() }

    override fun initView() {
        setContentView(binding.root)
        binding.contentLayout.adapter = adapter
        val data = simulatedData()
        adapter.addData(data)
    }

    private fun simulatedData(): List<String> {
        val list = mutableListOf<String>()
        for (i in 0..100) {
            list.add("第${i}条数据")
        }
        return list
    }

}