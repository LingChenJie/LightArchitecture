package com.architecture.light.domain.transaction.action.activity

import com.android.architecture.constant.ErrorCode
import com.android.architecture.domain.transaction.ActionResult
import com.android.architecture.extension.click
import com.architecture.light.app.AppActivityForAction
import com.architecture.light.data.model.db.entity.TransData
import com.architecture.light.data.remote.bean.SearchReserveResponse
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
        val reserve = data[0]
        binding.tvCustomName.text = reserve.cstName
        binding.tvCustomPhone.text = reserve.tel
        binding.tvCustomCardId.text = reserve.cardID
        adapter.setData(data)
        adapter.setItemClickListener { _, _, _ ->
            binding.btConfirm.isEnabled = false
            for (bean in data) {
                if (bean.isChecked) {
                    binding.btConfirm.isEnabled = true
                    break
                }
            }
            refreshUI(data)
        }
        binding.recyclerView.adapter = adapter
        binding.btConfirm.click {
            val info = ActionChooseReserve.Info(transData.searchReserveResponse!!)
            finish(ActionResult(ErrorCode.SUCCESS, info))
        }
        binding.btConfirm.isEnabled = false
    }

    private fun refreshUI(data: MutableList<SearchReserveResponse.Data>) {
        for (bean in data) {
            if (bean.isChecked) {
                binding.btConfirm.isEnabled = true
                break
            }
        }
    }

}