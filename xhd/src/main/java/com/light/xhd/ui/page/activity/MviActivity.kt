package com.light.xhd.ui.page.activity

import androidx.activity.viewModels
import com.android.architecture.helper.Logger
import com.light.xhd.app.AppActivity
import com.light.xhd.domain.event.ComplexEvent
import com.light.xhd.domain.event.Messages
import com.light.xhd.domain.message.PageMessenger
import com.light.xhd.domain.request.ComplexRequester
import com.light.xhd.databinding.ActivityMviBinding

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/7/29
 * Modify date: 2022/7/29
 * Version: 1
 */
class MviActivity : AppActivity() {

    private val binding: ActivityMviBinding by lazy { ActivityMviBinding.inflate(layoutInflater) }
    private val complexRequester by viewModels<ComplexRequester>()
    private val messenger by viewModels<PageMessenger>()

    override fun isStatusBarEnabled(): Boolean {
        return false
    }

    override fun initView() {
        setContentView(binding.root)
    }

    override fun input() {
        complexRequester.input(ComplexEvent.ResultTest1())
        complexRequester.input(ComplexEvent.ResultTest2())
        complexRequester.input(ComplexEvent.ResultTest2())
        complexRequester.input(ComplexEvent.ResultTest2())
        complexRequester.input(ComplexEvent.ResultTest2())
        complexRequester.input(ComplexEvent.ResultTest3())
        complexRequester.input(ComplexEvent.ResultTest3())
        complexRequester.input(ComplexEvent.ResultTest3())
        complexRequester.input(ComplexEvent.ResultTest3())
    }

    override fun output() {
        complexRequester.output(this) {
            when (it) {
                is ComplexEvent.ResultTest1 -> {
                    Logger.d(TAG, "--1")
                }
                is ComplexEvent.ResultTest2 -> {
                    Logger.d(TAG, "--2")
                }
                is ComplexEvent.ResultTest3 -> {
                    Logger.d(TAG, "--3")
                }
                is ComplexEvent.ResultTest4 -> {
                    Logger.d(TAG, "--4 " + it.count)
                }
            }
        }
        messenger.output(this) {
            if (it is Messages.FinishActivity) {
                finish()
            }
        }
    }

}