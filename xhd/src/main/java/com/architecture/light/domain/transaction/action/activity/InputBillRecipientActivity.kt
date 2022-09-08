package com.architecture.light.domain.transaction.action.activity

import com.android.architecture.constant.ErrorCode
import com.android.architecture.domain.transaction.ActionResult
import com.android.architecture.extension.click
import com.android.architecture.extension.empty
import com.android.architecture.extension.toast
import com.android.architecture.helper.ClickHelper
import com.android.architecture.helper.DelayHelper
import com.architecture.light.R
import com.architecture.light.app.AppActivityForAction
import com.architecture.light.constant.AppErrorCode
import com.architecture.light.databinding.ActivityInputBillRecipientBinding
import com.architecture.light.databinding.ActivityInputLoginInfoBinding
import com.architecture.light.domain.transaction.action.ActionInputBillRecipient
import com.architecture.light.domain.transaction.action.ActionInputLoginInfo
import com.architecture.light.utils.KeyBoardUtils

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/9/1
 * Modify date: 2022/9/1
 * Version: 1
 */
class InputBillRecipientActivity : AppActivityForAction() {

    private val binding: ActivityInputBillRecipientBinding by lazy {
        ActivityInputBillRecipientBinding.inflate(layoutInflater)
    }

    override fun initView() {
        setContentView(binding.root)
        binding.btConfirm.click {
            if (ClickHelper.isDoubleClick()) {
                return@click
            }
            val account = binding.etBillRecipient.text.toString()
            if (account.empty) {
                toast(getString(R.string.login_hint_bill_recipient_empty))
                return@click
            }
            val loginInfo = ActionInputBillRecipient.BillRecipientInfo(account)
            finish(ActionResult(ErrorCode.SUCCESS, loginInfo))
        }
        KeyBoardUtils.addLayoutListener(binding.layoutBottom, binding.btConfirm)
    }

}