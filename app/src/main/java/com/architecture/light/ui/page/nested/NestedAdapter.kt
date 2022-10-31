package com.architecture.light.ui.page.nested

import android.view.LayoutInflater
import android.view.ViewGroup
import com.android.architecture.ui.adapter.BaseAdapter
import com.architecture.light.databinding.ItemNestedListBinding

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/7/31
 * Modify date: 2022/7/31
 * Version: 1
 */
class NestedAdapter : BaseAdapter<String, ItemNestedListBinding>() {

    override fun getViewBinding(viewGroup: ViewGroup): ItemNestedListBinding {
        return ItemNestedListBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup,
            false
        )
    }

    override fun bindViewHolder(
        holder: ViewHolder<ItemNestedListBinding>,
        item: String,
        position: Int
    ) {
        val binding = holder.binding
        binding.tvContent.text = item
    }
}