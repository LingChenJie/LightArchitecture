package com.architecture.light.domain.transaction.action.activity

import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.android.architecture.constant.ErrorCode
import com.android.architecture.domain.transaction.ActionResult
import com.android.architecture.extension.valid
import com.android.architecture.helper.DelayHelper
import com.android.architecture.utils.Timer
import com.architecture.light.app.AppActivityForAction
import com.architecture.light.constant.AppErrorCode
import com.architecture.light.constant.Constant
import com.architecture.light.databinding.ActivityReadIdCardBinding
import com.architecture.light.domain.transaction.action.ActionReadIdCard
import com.architecture.light.ext.toast
import com.architecture.light.ext.toastWarn
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
    private var timer: Timer? = null

    override fun initView() {
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        if (idCardService != null) {
            readIdCard()
            startTimer()
        } else {
            if (Constant.IS_DEBUG) {
                DelayHelper.sendDelayTask(3000, object : DelayHelper.Task {
                    override fun execute() {
                        val idCardInfo = ActionReadIdCard.IdCardInfo("")
                        finish(ActionResult(ErrorCode.SUCCESS, idCardInfo))
                    }
                })
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

    private fun startTimer() {
        binding.titleView.timerView.visibility = View.VISIBLE
        binding.titleView.timerText.text = "60"
        timer?.cancel()
        timer = Timer(60)
        timer?.setTimerListener(object : Timer.TimerListener {
            override fun onFinish() {
                toastWarn("身份证读取超时")
                finish(ActionResult(AppErrorCode.BACK_TO_PREVIOUS_PAGE))
            }

            override fun onTick(leftTime: Long) {
                binding.titleView.timerText.text = leftTime.toString()
            }
        })
        timer?.start()
    }

    override fun finish(result: ActionResult?) {
        super.finish(result)
        timer?.cancel()
    }

}