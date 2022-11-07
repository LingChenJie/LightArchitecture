package com.architecture.light.ui.page.nested.scrolling

import androidx.recyclerview.widget.DividerItemDecoration
import com.android.architecture.extension.binding
import com.architecture.light.app.AppActivity
import com.architecture.light.databinding.ActivityNestedCoordinatorDemoBinding

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/11/7
 * Modify date: 2022/11/7
 * Version: 1
 */
class NestedCoordinatorDemoActivity : AppActivity() {

    private val binding: ActivityNestedCoordinatorDemoBinding by binding()

    override fun initView() {
        setContentView(binding.root)
        val adapter = TabFragment.SimpleStringAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        )
        adapter.data = initStrings()
    }

    private fun initStrings(): List<String> {
        val list: MutableList<String> = ArrayList()
        for (i in 0..99) {
            list.add("Behavior的嵌套滑动")
        }
        return list
    }

}