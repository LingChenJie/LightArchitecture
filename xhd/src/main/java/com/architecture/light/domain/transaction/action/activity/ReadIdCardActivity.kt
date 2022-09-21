package com.architecture.light.domain.transaction.action.activity

import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.android.architecture.constant.ErrorCode
import com.android.architecture.domain.transaction.ActionResult
import com.android.architecture.extension.valid
import com.architecture.light.app.AppActivityForAction
import com.architecture.light.constant.AppErrorCode
import com.architecture.light.constant.Constant
import com.architecture.light.databinding.ActivityReadIdCardBinding
import com.architecture.light.domain.transaction.action.ActionReadIdCard
import com.architecture.light.ext.toast
import com.architecture.light.helper.AidlServiceFactory
import com.sunmi.idcardservice.IDCardInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/9/1
 * Modify date: 2022/9/1
 * Version: 1
 */
class ReadIdCardActivity : AppActivityForAction() {

    private val binding: ActivityReadIdCardBinding by lazy {
        ActivityReadIdCardBinding.inflate(layoutInflater)
    }

    val idCardService = AidlServiceFactory.instance.getIDCardService()

    override fun initView() {
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        if (idCardService != null) {
            readIdCard()
        } else {
            if (Constant.IS_DEBUG) {
                val idCardInfo = ActionReadIdCard.IdCardInfo("")
                finish(ActionResult(ErrorCode.SUCCESS, idCardInfo))
            } else {
                toast("身份证服务打开失败，请重试")
                finish(ActionResult(AppErrorCode.BACK_TO_PREVIOUS_PAGE))
            }
        }
    }

    private fun readIdCard() {
        lifecycleScope.launchWhenResumed {
            withContext(Dispatchers.IO) {
                var cardInfo: IDCardInfo?
                while (true) {
                    cardInfo = idCardService?.readCard()
                    Log.i(TAG, "cardInfo:$cardInfo")
                    if (cardInfo != null && cardInfo.idCard.valid) {
                        val idCardInfo = ActionReadIdCard.IdCardInfo(cardInfo.idCard)
                        finish(ActionResult(ErrorCode.SUCCESS, idCardInfo))
                        break;
                    }
                    delay(200)
                }
            }
        }
    }

}