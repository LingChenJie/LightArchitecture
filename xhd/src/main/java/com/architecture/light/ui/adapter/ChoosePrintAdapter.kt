package com.architecture.light.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.android.architecture.extension.click
import com.android.architecture.ui.adapter.BaseAdapter
import com.architecture.light.data.remote.bean.SearchBillResponse
import com.architecture.light.databinding.AdapterPrintListBinding
import com.architecture.light.helper.AmountHelper

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/7/31
 * Modify date: 2022/7/31
 * Version: 1
 */
class ChoosePrintAdapter :
    BaseAdapter<SearchBillResponse.Data, AdapterPrintListBinding>() {

    override fun getViewBinding(viewGroup: ViewGroup): AdapterPrintListBinding {
        return AdapterPrintListBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup,
            false
        )
    }

    override fun bindViewHolder(
        holder: ViewHolder<AdapterPrintListBinding>,
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
        binding.root.click {
            data.forEach { it.isChecked = false }
            data[position].isChecked = true
            notifyDataSetChanged()
            mOnItemClickListener?.onItemClick(it.id, position, item)
        }
    }
}