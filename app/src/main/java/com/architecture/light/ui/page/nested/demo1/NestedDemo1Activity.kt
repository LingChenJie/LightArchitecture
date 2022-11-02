package com.architecture.light.ui.page.nested.demo1

import android.view.LayoutInflater
import android.view.ViewGroup
import com.android.architecture.extension.binding
import com.android.architecture.ui.adapter.BaseAdapter
import com.architecture.light.app.AppActivity
import com.architecture.light.databinding.ActivityNestedDemo1Binding
import com.architecture.light.databinding.ItemSingleTextBinding

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/10/31
 * Modify date: 2022/10/31
 * Version: 1
 */
class NestedDemo1Activity : AppActivity() {

    private val binding: ActivityNestedDemo1Binding by binding()
    private val adapter by lazy { SimpleStringAdapter() }

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

    class SimpleStringAdapter : BaseAdapter<String, ItemSingleTextBinding>() {
        override fun getViewBinding(viewGroup: ViewGroup): ItemSingleTextBinding {
            return ItemSingleTextBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        }

        override fun bindViewHolder(
            holder: ViewHolder<ItemSingleTextBinding>,
            item: String,
            position: Int
        ) {
            holder.binding.tvText.text = item
        }

    }

}