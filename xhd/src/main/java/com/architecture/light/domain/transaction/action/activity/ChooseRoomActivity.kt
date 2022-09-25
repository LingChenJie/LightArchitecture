package com.architecture.light.domain.transaction.action.activity

import com.android.architecture.constant.ErrorCode
import com.android.architecture.domain.transaction.ActionResult
import com.architecture.light.app.AppActivityForAction
import com.architecture.light.data.model.db.entity.TransData
import com.architecture.light.databinding.ActivityChooseRoomBinding
import com.architecture.light.domain.transaction.action.ActionChooseRoom
import com.architecture.light.domain.transaction.action.UIParams
import com.architecture.light.ui.adapter.ChooseRoomAdapter

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/9/1
 * Modify date: 2022/9/1
 * Version: 1
 */
class ChooseRoomActivity : AppActivityForAction() {

    private val binding: ActivityChooseRoomBinding by lazy {
        ActivityChooseRoomBinding.inflate(layoutInflater)
    }

    private val adapter by lazy { ChooseRoomAdapter() }

    override fun initView() {
        setContentView(binding.root)
        val titleName = intent.getStringExtra(UIParams.TITLE_NAME)
        val transData = intent.getSerializableExtra(UIParams.TRANS_DATA) as TransData
        val data = transData.searchRoomResponse!!.data
        binding.titleView.titleText.text = titleName
        adapter.setData(data)
        adapter.setItemClickListener { _, _, item ->
            val room =
                ActionChooseRoom.Room(
                    item.roomInfo,
                    item.roomGUID,
                    item.cstName,
                    transData.searchRoomResponse!!
                )
            finish(ActionResult(ErrorCode.SUCCESS, room))
        }
        binding.recyclerView.adapter = adapter
    }

}