package com.architecture.light.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.android.architecture.extension.click
import com.android.architecture.helper.AmountHelper
import com.android.architecture.ui.adapter.BaseAdapter
import com.architecture.light.data.remote.bean.SearchBillResponse
import com.architecture.light.databinding.AdapterBillListBinding

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/7/31
 * Modify date: 2022/7/31
 * Version: 1
 */
class ChooseBillAdapter :
    BaseAdapter<SearchBillResponse.Data, AdapterBillListBinding>() {

    override fun getViewBinding(viewGroup: ViewGroup): AdapterBillListBinding {
        return AdapterBillListBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup,
            false
        )
    }

    override fun bindViewHolder(
        holder: ViewHolder<AdapterBillListBinding>,
        item: SearchBillResponse.Data,
        position: Int
    ) {
        val binding = holder.binding
        binding.tvProjectName.text = item.projStagesName
        binding.tvRoomName.text = item.roomName
        binding.tvCustomName.text = item.cstName
        binding.tvPaymentDate.text = item.kpDate
        binding.tvPaymentAmount.text =
            AmountHelper.formatAmount(AmountHelper.convertAmount(item.amount))
        binding.tvConfirm.click {
            data.forEach { it.isChecked = false }
            data[position].isChecked = true
            notifyDataSetChanged()
            mOnItemClickListener?.onItemClick(it.id, position, item)
        }
    }
}