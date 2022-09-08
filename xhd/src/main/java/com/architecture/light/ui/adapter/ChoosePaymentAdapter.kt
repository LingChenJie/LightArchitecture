package com.architecture.light.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.android.architecture.extension.click
import com.android.architecture.extension.getColor
import com.android.architecture.ui.adapter.BaseAdapter
import com.architecture.light.R
import com.architecture.light.data.remote.bean.SearchRoomResponse
import com.architecture.light.databinding.AdapterChoosePaymentListBinding

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/7/31
 * Modify date: 2022/7/31
 * Version: 1
 */
class ChoosePaymentAdapter :
    BaseAdapter<SearchRoomResponse.Data.Fee, AdapterChoosePaymentListBinding>() {

    override fun getViewBinding(viewGroup: ViewGroup): AdapterChoosePaymentListBinding {
        return AdapterChoosePaymentListBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup,
            false
        )
    }

    override fun bindViewHolder(
        holder: ViewHolder<AdapterChoosePaymentListBinding>,
        item: SearchRoomResponse.Data.Fee,
        position: Int
    ) {
        val binding = holder.binding
        binding.tvTitle.text = item.itemName
        binding.root.setBackgroundColor(
            if (item.isChecked) getColor(R.color.theme_color) else
                getColor(com.android.architecture.R.color.transparent)
        )
        binding.root.click {
            data[position].isChecked = !data[position].isChecked
            notifyDataSetChanged()
            mOnItemClickListener?.onItemClick(it.id, position, item)
        }
    }
}