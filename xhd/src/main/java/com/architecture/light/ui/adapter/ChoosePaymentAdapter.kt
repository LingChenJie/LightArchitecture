package com.architecture.light.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.android.architecture.extension.click
import com.android.architecture.helper.AmountHelper
import com.android.architecture.ui.adapter.BaseAdapter
import com.architecture.light.data.remote.bean.SearchRoomResponse
import com.architecture.light.databinding.AdapterPaymentListBinding
import com.architecture.light.ui.dialog.AmountModifyDialog

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/7/31
 * Modify date: 2022/7/31
 * Version: 1
 */
class ChoosePaymentAdapter(val context: Context) :
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
        binding.tvPaymentTotalName.text = AmountHelper.formatAmount(item.amount)
        binding.tvPaymentNotPaidAmount.text = AmountHelper.formatAmount(item.paymentAmount)
        binding.layoutItem.isSelected = item.isChecked
        binding.layoutPaymentNotPaidAmount.click {
            showModifyAmountDialog(item)
        }
        binding.layoutItem.click {
            data[position].isChecked = !data[position].isChecked
            notifyDataSetChanged()
            itemChangeListener?.change()
        }
    }

    private fun showModifyAmountDialog(item: SearchRoomResponse.Data.Fee) {
        AmountModifyDialog.Builder(context)
            .setAmount(item.paymentAmount, item.yeAmount)
            .setClickConfirmListener { _, amount ->
                item.paymentAmount = amount
                notifyDataSetChanged()
                itemChangeListener?.change()
            }
            .show()
    }

    private var itemChangeListener: ItemChangeListener? = null

    fun setItemChangeListener(listener: ItemChangeListener) {
        itemChangeListener = listener
    }

    interface ItemChangeListener {
        fun change()
    }
}