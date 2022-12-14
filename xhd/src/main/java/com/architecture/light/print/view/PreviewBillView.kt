package com.architecture.light.print.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.android.architecture.extension.valid
import com.android.architecture.helper.AmountHelper
import com.android.architecture.helper.DateHelper
import com.architecture.light.data.remote.bean.SearchBillResponse
import com.architecture.light.databinding.ViewPreviewBillBinding

/**
 * File describe:
 * Author: SuQi
 * Create date: 9/9/22
 * Modify date: 9/9/22
 * Version: 1
 */
class PreviewBillView(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : RelativeLayout(context, attrs, defStyleAttr) {

    private val binding: ViewPreviewBillBinding by lazy {
        val layoutInflater = LayoutInflater.from(context)
        ViewPreviewBillBinding.inflate(layoutInflater, this, true)
    }

    fun fullData(bean: SearchBillResponse.Data) {
        binding.tvNo.text = bean.payNo
        binding.tvPrintUnion.text = bean.printNum
        binding.tvProjectName.text = bean.projStagesName
        binding.tvRoomNum.text = bean.roomName
        binding.tvBillDate.text = bean.kpDate
        binding.tvPayingUnit.text = bean.jkr
        binding.tvRmbCapital.text = bean.amountString
        binding.tvRmbLower.text = AmountHelper.formatAmount(AmountHelper.convertAmount(bean.amount))
        if (bean.payMode.valid) {
            val amount = "￥" + AmountHelper.formatAmount(AmountHelper.convertAmount(bean.amount))
            val payWay = bean.payMode + " " + amount
            binding.tvPayingWay.text = payWay
        }
        binding.tvPayingReason.text = bean.payRemark
        //binding.tvSummaryNotes.text
        binding.tvIssuer.text = bean.kpr
        binding.tvRemark.text = bean.customRemark
        binding.tvPrintTime.text = DateHelper.getDateFormatString()
    }

}