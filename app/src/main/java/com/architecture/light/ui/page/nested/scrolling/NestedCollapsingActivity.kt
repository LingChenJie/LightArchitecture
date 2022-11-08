package com.architecture.light.ui.page.nested.scrolling

import androidx.recyclerview.widget.DividerItemDecoration
import com.android.architecture.extension.binding
import com.architecture.light.app.AppActivity
import com.architecture.light.databinding.ActivityNestedCollapsingBinding
import com.architecture.light.ui.page.nested.adapter.SimpleStringAdapter

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/11/8
 * Modify date: 2022/11/8
 * Version: 1
 */
class NestedCollapsingActivity : AppActivity() {

    private val binding: ActivityNestedCollapsingBinding by binding()

    override fun isStatusBarEnabled(): Boolean {
        return false
    }

    override fun initView() {
        setContentView(binding.root)
        val adapter = SimpleStringAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        )
        adapter.data = initStrings()
    }

    private fun initStrings(): List<String> {
        val list: MutableList<String> = ArrayList()
        for (i in 0..99) {
            list.add("简单文本$i")
        }
        return list
    }

}