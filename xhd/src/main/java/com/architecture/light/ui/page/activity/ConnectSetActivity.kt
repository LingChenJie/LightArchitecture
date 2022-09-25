package com.architecture.light.ui.page.activity

import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.android.architecture.constant.ErrorCode
import com.android.architecture.data.manage.InputTextManager
import com.android.architecture.extension.click
import com.android.architecture.extension.hideKeyboard
import com.android.architecture.extension.valid
import com.android.architecture.helper.Logger
import com.architecture.light.app.AppActivity
import com.architecture.light.data.model.db.entity.TransData
import com.architecture.light.databinding.ActivityConnectSetBinding
import com.architecture.light.domain.task.PosSignInTask
import com.architecture.light.ext.toastSucc
import com.architecture.light.ext.toastWarn
import com.architecture.light.helper.PayRequest
import com.architecture.light.settings.PayCache
import com.chinaums.mis.bean.RequestPojo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
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
        binding.layoutWifiSet.click {
            if (binding.layoutBillRecipientModify.isVisible) {
                binding.layoutBillRecipientModify.visibility = View.GONE
            } else {
                binding.layoutBillRecipientModify.visibility = View.VISIBLE
            }
        }
        binding.btConfirmWifi.click {
            val ip = binding.etIp.text.toString()
            val port = binding.etPort.text.toString()
            if (ip.valid && port.valid) {
                PayCache.saveIp(ip)
                PayCache.savePort(port)
                toastSucc("保存成功")
            }
        }
        binding.layoutConnectTest.click {
            val ip = PayCache.getIp()
            val port = PayCache.getPort()
            if (ip.valid && port.valid) {
                signIn()
            } else {
                toastWarn("无效的IP和端口")
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
            showLoading("POS签到中...")
            delay(500)
            val response = withContext(Dispatchers.IO) {
                PosSignInTask().execute(TransData())
            }
            hideLoading()
            val code = response.responseCode
            val message = response.responseMessage
            if (code == ErrorCode.SUCCESS) {
                toastSucc("签到成功[$code]")
            } else {
                toastWarn("$message[$code]")
            }
        }
    }

}