package com.architecture.light.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.android.architecture.extension.click
import com.android.architecture.ui.adapter.BaseAdapter
import com.architecture.light.data.remote.bean.SearchReserveResponse
import com.architecture.light.databinding.AdapterReserveListBinding

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
        binding.tvNo.text = item.projNum
        binding.tvPaymentName.text = item.projGUID
        binding.tvReserveNumber.text = item.bookingGUID
        binding.layoutItem.isSelected = item.isChecked
        binding.layoutItem.click {
            data[position].isChecked = !data[position].isChecked
            notifyDataSetChanged()
            mOnItemClickListener?.onItemClick(it.id, position, item)
        }
    }

}