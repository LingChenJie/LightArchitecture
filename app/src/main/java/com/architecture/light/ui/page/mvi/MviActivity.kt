package com.architecture.light.ui.page.mvi

import androidx.activity.viewModels
import com.android.architecture.extension.binding
import com.android.architecture.helper.Logger
import com.architecture.light.app.AppActivity
import com.architecture.light.databinding.ActivityMviBinding
import com.architecture.light.domain.event.ComplexEvent
import com.architecture.light.domain.event.MviMessages
import com.architecture.light.domain.message.ComplexRequester
import com.architecture.light.domain.message.PageMessenger

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/7/29
 * Modify date: 2022/7/29
 * Version: 1
 */
class MviActivity : AppActivity() {

    private val binding: ActivityMviBinding by binding()
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
            if (it is MviMessages.FinishActivity) {
                finish()
            }
        }
    }

}