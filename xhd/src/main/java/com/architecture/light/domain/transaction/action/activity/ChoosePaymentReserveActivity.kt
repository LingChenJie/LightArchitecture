package com.architecture.light.domain.transaction.action.activity

import com.android.architecture.constant.ErrorCode
import com.android.architecture.domain.transaction.ActionResult
import com.architecture.light.app.AppActivityForAction
import com.architecture.light.data.model.db.entity.TransData
import com.architecture.light.data.remote.bean.SearchPaymentResponse
import com.architecture.light.databinding.ActivityChoosePaymentReserveBinding
import com.architecture.light.domain.transaction.action.ActionChoosePaymentReserve
import com.architecture.light.domain.transaction.action.UIParams
import com.architecture.light.ui.adapter.ChoosePaymentReserveAdapter

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/9/1
 * Modify date: 2022/9/1
 * Version: 1
 */
class ChoosePaymentReserveActivity : AppActivityForAction() {

    private val binding: ActivityChoosePaymentReserveBinding by lazy {
        ActivityChoosePaymentReserveBinding.inflate(layoutInflater)
    }

    private val adapter by lazy { ChoosePaymentReserveAdapter(this) }
    private lateinit var paymentData: List<SearchPaymentResponse.Data>

    override fun initView() {
        setContentView(binding.root)
        val titleName = intent.getStringExtra(UIParams.TITLE_NAME)
        val transData = intent.getSerializableExtra(UIParams.TRANS_DATA) as TransData
        paymentData = transData.searchPaymentResponse!!.data
        adapter.data = getParentPayment()
        binding.titleView.titleText.text = titleName
        binding.recyclerView.adapter = adapter
        adapter.setItemClickListener { _, _, item ->
            if (item.isSubLevel) {
                val info = ActionChoosePaymentReserve.Info(transData.searchPaymentResponse!!)
                finish(ActionResult(ErrorCode.SUCCESS, info))
            } else {
                adapter.data = getSubPayment(item.feeItemGUID)
                adapter.notifyDataSetChanged()
            }
        }
    }

    override fun clickBack() {
        val data = adapter.data
        if (data != null && data.size > 0) {
            for (bean in data) {
                bean.isChecked = false
            }
            if (!data[0].isParentLevel) {
                val payment = getPayment(data[0].parentGUID)
                adapter.data = getSubPayment(payment!!.parentGUID)
                adapter.notifyDataSetChanged()
            } else {
                super.clickBack()
            }
        } else {
            super.clickBack()
        }
    }

    private fun getParentPayment(): List<SearchPaymentResponse.Data> {
        val result = mutableListOf<SearchPaymentResponse.Data>()
        for (bean in paymentData) {
            if (bean.isParentLevel) {
                result.add(bean)
            }
        }
        return result
    }

    private fun getSubPayment(feeItemGUID: String): List<SearchPaymentResponse.Data> {
        val result = mutableListOf<SearchPaymentResponse.Data>()
        for (bean in paymentData) {
            if (feeItemGUID == bean.parentGUID) {
                result.add(bean)
            }
        }
        return result
    }

    private fun getPayment(feeItemGUID: String): SearchPaymentResponse.Data? {
        for (bean in paymentData) {
            if (feeItemGUID == bean.feeItemGUID) {
                return bean
            }
        }
        return null
    }

}