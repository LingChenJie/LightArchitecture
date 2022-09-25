package com.architecture.light.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.architecture.extension.click
import com.android.architecture.helper.DateHelper
import com.android.architecture.ui.adapter.BaseAdapter
import com.architecture.light.constant.TransactionName
import com.architecture.light.data.model.db.entity.TransData
import com.architecture.light.databinding.AdapterPaymentSyncListBinding
import com.architecture.light.databinding.AdapterVoidSyncListBinding
import com.architecture.light.helper.AmountHelper
import com.architecture.light.helper.TransHelper

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/7/31
 * Modify date: 2022/7/31
 * Version: 1
 */
class VoidSyncAdapter :
    BaseAdapter<TransData, AdapterVoidSyncListBinding>() {

    override fun getViewBinding(viewGroup: ViewGroup): AdapterVoidSyncListBinding {
        return AdapterVoidSyncListBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup,
            false
        )
    }

    override fun bindViewHolder(
        holder: ViewHolder<AdapterVoidSyncListBinding>,
        item: TransData,
        position: Int
    ) {
        val binding = holder.binding
        binding.tvCustomName.text = item.cstName
        binding.tvPaymentDate.text =
            DateHelper.getDateFormatString(millis = item.transactionTimeMillis)
        binding.tvAmount.text =
            AmountHelper.formatAmount(item.amount)
        binding.tvTransactionStatus.text = TransHelper.getVoidStatus(item)
        binding.tvVoucherName.text = item.originalVoucherNumber

        binding.tvStatusQuery.click {
            mOnItemClickListener?.onItemClick(it.id, position, item)
        }
    }
}