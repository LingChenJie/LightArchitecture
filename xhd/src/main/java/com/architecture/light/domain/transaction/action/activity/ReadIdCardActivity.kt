package com.architecture.light.domain.transaction.action.activity

import com.android.architecture.constant.ErrorCode
import com.android.architecture.domain.transaction.ActionResult
import com.android.architecture.extension.toast
import com.android.architecture.helper.Logger
import com.architecture.light.app.AppActivityForAction
import com.architecture.light.constant.AppErrorCode
import com.architecture.light.databinding.ActivityReadIdCardBinding
import com.architecture.light.domain.transaction.action.ActionReadIdCard
import com.architecture.light.helper.AidlServiceFactory
import com.sunmi.idcardservice.CardCallback
import com.sunmi.idcardservice.IDCardInfo

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

        if (idCardService != null) {
            idCardService.readCardAuto(object : CardCallback.Stub() {
                override fun getCardData(info: IDCardInfo, code: Int) {
                    if (code == 10) {
                        Logger.i(TAG, "getCardData: $info")
                        val idCardInfo = ActionReadIdCard.IdCardInfo(info.idCard)
                        finish(ActionResult(ErrorCode.SUCCESS, idCardInfo))
                    } else if (code == -10) {
                        Logger.i(TAG, "auto read card did not get data")
                        toast("auto read card did not get data")
                        finish(ActionResult(AppErrorCode.BACK_TO_MAIN_PAGE))
                    } else {
                        Logger.i(TAG, "what is wrong")
                        toast("what is wrong")
                        finish(ActionResult(AppErrorCode.BACK_TO_MAIN_PAGE))
                    }
                }
            })
        } else {
            toast("身份证服务打开失败，请重试")
            finish(ActionResult(AppErrorCode.BACK_TO_MAIN_PAGE))
        }
    }

    override fun onStop() {
        super.onStop()
        idCardService?.cancelAutoReading()
    }

}