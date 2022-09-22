package com.architecture.light.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.android.architecture.extension.click
import com.android.architecture.ui.adapter.BaseAdapter
import com.architecture.light.data.remote.bean.SearchReserveResponse
import com.architecture.light.data.remote.bean.SearchRoomResponse
import com.architecture.light.databinding.AdapterReserveListBinding
import com.architecture.light.databinding.AdapterRoomListBinding

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/7/31
 * Modify date: 2022/7/31
 * Version: 1
 */
class ChooseReserveAdapter(val context: Context) :
    BaseAdapter<SearchReserveResponse.Data, AdapterReserveListBinding>() {

    override fun getViewBinding(viewGroup: ViewGroup): AdapterReserveListBinding {
        return AdapterReserveListBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup,
            false
        )
    }

    override fun bindViewHolder(
        holder: ViewHolder<AdapterReserveListBinding>,
        item: SearchReserveResponse.Data,
        position: Int
    ) {
        val binding = holder.binding
        binding.tvCustomName.text = item.cstName
        binding.tvCustomPhone.text = item.tel
        binding.tvCustomCardId.text = item.cardID
        binding.tvProjectNum.text = item.projNum

        binding.layoutItem.isSelected = item.isChecked
        binding.labelCustomName.isSelected = item.isChecked
        binding.tvCustomName.isSelected = item.isChecked
        binding.labelCustomPhone.isSelected = item.isChecked
        binding.tvCustomPhone.isSelected = item.isChecked
        binding.labelCustomCardId.isSelected = item.isChecked
        binding.tvCustomCardId.isSelected = item.isChecked
        binding.labelProjectNum.isSelected = item.isChecked
        binding.tvProjectNum.isSelected = item.isChecked

        binding.root.click {
            data.forEach { it.isChecked = false }
            data[position].isChecked = true
            notifyDataSetChanged()
            mOnItemClickListener?.onItemClick(it.id, position, item)
        }
    }

}