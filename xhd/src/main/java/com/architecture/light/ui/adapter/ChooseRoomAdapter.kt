package com.architecture.light.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.android.architecture.extension.click
import com.android.architecture.extension.getColor
import com.android.architecture.ui.adapter.BaseAdapter
import com.architecture.light.R
import com.architecture.light.data.remote.bean.SearchRoomResponse
import com.architecture.light.databinding.AdapterProjectListBinding

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/7/31
 * Modify date: 2022/7/31
 * Version: 1
 */
class ChooseRoomAdapter :
    BaseAdapter<SearchRoomResponse.Data, AdapterProjectListBinding>() {

    override fun getViewBinding(viewGroup: ViewGroup): AdapterProjectListBinding {
        return AdapterProjectListBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup,
            false
        )
    }

    override fun bindViewHolder(
        holder: ViewHolder<AdapterProjectListBinding>,
        item: SearchRoomResponse.Data,
        position: Int
    ) {
        val binding = holder.binding
        binding.tvTitle.text = item.roomInfo
        binding.root.setBackgroundColor(
            if (item.isChecked) getColor(R.color.theme_color) else
                getColor(com.android.architecture.R.color.transparent)
        )
        binding.root.click {
            data.forEach { it.isChecked = false }
            data[position].isChecked = true
            notifyDataSetChanged()
            mOnItemClickListener?.onItemClick(it.id, position, item)
        }
    }
}