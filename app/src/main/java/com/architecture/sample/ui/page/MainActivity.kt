package com.architecture.sample.ui.page

import android.os.Bundle
import androidx.activity.viewModels
import com.android.architecture.helper.Logger
import com.android.architecture.ui.page.BaseActivity
import com.architecture.sample.databinding.ActivityMainBinding
import com.architecture.sample.domain.event.ComplexEvent
import com.architecture.sample.domain.request.ComplexRequester

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/7/29
 * Modify date: 2022/7/29
 * Version: 1
 */
class MainActivity : BaseActivity() {

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val complexRequester by viewModels<ComplexRequester>()

    override fun initView() {
        setContentView(binding.root)
    }

    override fun input() {
//        complexRequester.input(ComplexEvent.ResultTest1())
//        complexRequester.input(ComplexEvent.ResultTest2())
//        complexRequester.input(ComplexEvent.ResultTest2())
//        complexRequester.input(ComplexEvent.ResultTest2())
//        complexRequester.input(ComplexEvent.ResultTest2())
//        complexRequester.input(ComplexEvent.ResultTest3())
//        complexRequester.input(ComplexEvent.ResultTest3())
//        complexRequester.input(ComplexEvent.ResultTest3())
//        complexRequester.input(ComplexEvent.ResultTest3())
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
    }

}