package com.architecture.light.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.android.architecture.extension.click
import com.android.architecture.extension.getColor
import com.android.architecture.ui.adapter.BaseAdapter
import com.architecture.light.R
import com.architecture.light.data.remote.bean.SearchRoomResponse
import com.architecture.light.databinding.AdapterPaymentListBinding

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/7/31
 * Modify date: 2022/7/31
 * Version: 1
 */
class ChoosePaymentAdapter :
    BaseAdapter<SearchRoomResponse.Data.Fee, AdapterPaymentListBinding>() {

    override fun getViewBinding(viewGroup: ViewGroup): AdapterPaymentListBinding {
        return AdapterPaymentListBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup,
            false
        )
    }

    override fun bindViewHolder(
        holder: ViewHolder<AdapterPaymentListBinding>,
        item: SearchRoomResponse.Data.Fee,
        position: Int
    ) {
        val binding = holder.binding
        binding.tvNo.text = (position + 1).toString()
        binding.tvPaymentType.text = item.itemType
        binding.tvPaymentName.text = item.itemName
        binding.tvPaymentTotalName.text = item.amount.toString()
        binding.tvPaymentNotPaidAmount.text = item.yeAmount.toString()
        binding.root.setBackgroundColor(
            if (item.isChecked) getColor(R.color.theme_color) else
                getColor(com.android.architecture.R.color.transparent)
        )
        binding.tvPaymentModify.click {

        }
        binding.root.click {
            data[position].isChecked = !data[position].isChecked
            notifyDataSetChanged()
            mOnItemClickListener?.onItemClick(it.id, position, item)
        }
    }
}