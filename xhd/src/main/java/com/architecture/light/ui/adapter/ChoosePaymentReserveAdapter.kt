package com.architecture.light.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.android.architecture.extension.click
import com.android.architecture.ui.adapter.BaseAdapter
import com.architecture.light.data.remote.bean.SearchPaymentResponse
import com.architecture.light.databinding.AdapterPaymentReserveListBinding

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/7/31
 * Modify date: 2022/7/31
 * Version: 1
 */
class ChoosePaymentReserveAdapter(val context: Context) :
    BaseAdapter<SearchPaymentResponse.Data, AdapterPaymentReserveListBinding>() {

    override fun getViewBinding(viewGroup: ViewGroup): AdapterPaymentReserveListBinding {
        return AdapterPaymentReserveListBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup,
            false
        )
    }

    override fun bindViewHolder(
        holder: ViewHolder<AdapterPaymentReserveListBinding>,
        item: SearchPaymentResponse.Data,
        position: Int
    ) {
        val binding = holder.binding
        binding.layoutItem.isSelected = item.isChecked
        binding.tvPaymentName.text = item.feeItemName
        binding.layoutItem.click {
            data.forEach { it.isChecked = false }
            data[position].isChecked = true
            notifyDataSetChanged()
            mOnItemClickListener?.onItemClick(it.id, position, item)
        }
    }

}