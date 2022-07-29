package com.sunmi.lightarchitecture

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.android.architecture.ui.page.BaseActivity
import com.kunminx.architecture.domain.dispatch.MviDispatcher
import com.sunmi.lightarchitecture.databinding.ActivityMainBinding

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/7/29
 * Modify date: 2022/7/29
 * Version: 1
 */
class MainActivity : BaseActivity() {

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

    }

}