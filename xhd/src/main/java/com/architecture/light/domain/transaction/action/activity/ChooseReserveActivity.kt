package com.architecture.light.domain.transaction.action.activity

import com.android.architecture.constant.ErrorCode
import com.android.architecture.domain.transaction.ActionResult
import com.architecture.light.app.AppActivityForAction
import com.architecture.light.data.model.db.entity.TransData
import com.architecture.light.databinding.ActivityChooseReserveBinding
import com.architecture.light.domain.transaction.action.ActionChooseReserve
import com.architecture.light.domain.transaction.action.UIParams
import com.architecture.light.ui.adapter.ChooseReserveAdapter

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/9/1
 * Modify date: 2022/9/1
 * Version: 1
 */
class ChooseReserveActivity : AppActivityForAction() {

    private val binding: ActivityChooseReserveBinding by lazy {
        ActivityChooseReserveBinding.inflate(layoutInflater)
    }

    private val adapter by lazy { ChooseReserveAdapter(this) }

    override fun initView() {
        setContentView(binding.root)
        val transData = intent.getSerializableExtra(UIParams.TRANS_DATA) as TransData
        val data = transData.searchReserveResponse!!.data
        adapter.data = data
        adapter.setItemClickListener { _, _, item ->
            val room =
                ActionChooseReserve.Info(
                    item.projNum,
                    item.bookingGUID,
                    item.cstName,
                    transData.searchReserveResponse!!
                )
            finish(ActionResult(ErrorCode.SUCCESS, room))
        }
        binding.recyclerView.adapter = adapter
    }

}