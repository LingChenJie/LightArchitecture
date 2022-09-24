package com.architecture.light.domain.transaction.action.activity

import com.android.architecture.constant.ErrorCode
import com.android.architecture.domain.transaction.ActionResult
import com.architecture.light.app.AppActivityForAction
import com.architecture.light.data.model.db.entity.TransData
import com.architecture.light.databinding.ActivityChoosePrintBinding
import com.architecture.light.domain.transaction.action.ActionChooseBill
import com.architecture.light.domain.transaction.action.UIParams
import com.architecture.light.ui.adapter.ChoosePrintAdapter

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/9/1
 * Modify date: 2022/9/1
 * Version: 1
 */
class ChooseBillActivity : AppActivityForAction() {

    private val binding: ActivityChoosePrintBinding by lazy {
        ActivityChoosePrintBinding.inflate(layoutInflater)
    }

    private val adapter by lazy { ChoosePrintAdapter() }

    override fun initView() {
        setContentView(binding.root)
        val titleName = intent.getStringExtra(UIParams.TITLE_NAME)
        val transData = intent.getSerializableExtra(UIParams.TRANS_DATA) as TransData
        val data = transData.searchBillResponse!!.data
        binding.titleView.titleText.text = titleName
        adapter.data = data
        adapter.setItemClickListener { _, _, _ ->
            val info = ActionChooseBill.Info(transData.searchBillResponse!!)
            finish(ActionResult(ErrorCode.SUCCESS, info))
        }
        binding.recyclerView.adapter = adapter
    }

}