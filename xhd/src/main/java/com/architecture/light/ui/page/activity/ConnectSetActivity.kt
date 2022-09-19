package com.architecture.light.ui.page.activity

import androidx.lifecycle.lifecycleScope
import com.android.architecture.data.manage.InputTextManager
import com.android.architecture.extension.click
import com.android.architecture.extension.hideKeyboard
import com.android.architecture.extension.valid
import com.architecture.light.app.AppActivity
import com.architecture.light.data.model.db.entity.TransData
import com.architecture.light.databinding.ActivityConnectSetBinding
import com.architecture.light.domain.task.PosSignInTask
import com.architecture.light.settings.PayCache
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/8/11
 * Modify date: 2022/8/11
 * Version: 1
 */
class ConnectSetActivity : AppActivity() {

    private val binding: ActivityConnectSetBinding by lazy {
        ActivityConnectSetBinding.inflate(
            layoutInflater
        )
    }

    override fun initView() {
        setContentView(binding.root)
        binding.titleView.backView.click { finish() }
        binding.etIp.setText(PayCache.getIp())
        binding.etPort.setText(PayCache.getPort())
        binding.btConfirmWifi.click {
            val ip = binding.etIp.text.toString()
            val port = binding.etPort.text.toString()
            if (ip.valid && port.valid) {
                PayCache.saveIp(ip)
                PayCache.savePort(port)
                signIn()
            }
        }
        InputTextManager.with(this)
            .addView(binding.etIp)
            .addView(binding.etPort)
            .setMain(binding.btConfirmWifi)
            .build()
        binding.etIp.hideKeyboard()
    }

    private fun signIn() {
        lifecycleScope.launchWhenResumed {
            withContext(Dispatchers.IO) {
                PosSignInTask().execute(TransData())
            }
        }
    }

}