package com.architecture.light.ui.page.nested.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.android.architecture.ui.adapter.BaseAdapter
import com.architecture.light.databinding.ItemSingleTextBinding

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/11/8
 * Modify date: 2022/11/8
 * Version: 1
 */
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