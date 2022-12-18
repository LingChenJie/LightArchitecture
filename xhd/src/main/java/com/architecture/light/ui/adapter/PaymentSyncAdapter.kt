package com.architecture.light.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.architecture.extension.click
import com.android.architecture.helper.AmountHelper
import com.android.architecture.helper.DateHelper
import com.android.architecture.ui.adapter.BaseAdapter
import com.architecture.light.constant.TransactionName
import com.architecture.light.data.model.db.entity.TransData
import com.architecture.light.databinding.AdapterPaymentSyncListBinding
import com.architecture.light.helper.TransHelper

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/7/31
 * Modify date: 2022/7/31
 * Version: 1
 */
class PaymentSyncAdapter :
    BaseAdapter<TransData, AdapterPaymentSyncListBinding>() {

    override fun getViewBinding(viewGroup: ViewGroup): AdapterPaymentSyncListBinding {
        return AdapterPaymentSyncListBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup,
            false
        )
    }

    override fun bindViewHolder(
        holder: ViewHolder<AdapterPaymentSyncListBinding>,
        item: TransData,
        position: Int
    ) {
        val binding = holder.binding
        binding.tvTransactionName.text = TransHelper.getTransactionName(item)
        binding.tvCustomName.text = item.cstName
        binding.tvPaymentDate.text =
            DateHelper.getDateFormatString(millis = item.transactionTimeMillis)
        binding.tvPaymentAmount.text =
            AmountHelper.formatAmount(item.amount)
        binding.tvTransactionStatus.text = TransHelper.getPaymentStatus(item)
        when (item.transactionName) {
            TransactionName.Payment.name -> {
                binding.layoutRoom.visibility = View.VISIBLE
                binding.layoutProject.visibility = View.GONE
            }
            TransactionName.Reserve.name -> {
                binding.layoutRoom.visibility = View.GONE
                binding.layoutProject.visibility = View.VISIBLE
            }
            TransactionName.AdvancesReceived.name -> {
                binding.layoutRoom.visibility = View.GONE
                binding.layoutProject.visibility = View.GONE
            }
        }
        binding.tvRoomName.text = item.roomInfo
        binding.tvProjectNum.text = item.projNum

        binding.tvStatusSync.click {
            mOnItemClickListener?.onItemClick(it.id, position, item)
        }
    }
}