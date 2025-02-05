package com.architecture.light.ui.page.activity

import android.view.View
import androidx.core.view.isVisible
import com.android.architecture.data.manage.InputTextManager
import com.android.architecture.extension.click
import com.android.architecture.extension.hideSoftInput
import com.android.architecture.extension.openActivity
import com.android.architecture.extension.valid
import com.architecture.light.app.AppActivity
import com.architecture.light.databinding.ActivityAccountManageBinding
import com.architecture.light.ext.toastSucc
import com.architecture.light.settings.AccountCache
import com.architecture.light.settings.ProjectCache
import com.architecture.light.ui.dialog.MessageDialog


/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/8/11
 * Modify date: 2022/8/11
 * Version: 1
 */
class AccountManageActivity : AppActivity() {

    private val binding: ActivityAccountManageBinding by lazy {
        ActivityAccountManageBinding.inflate(
            layoutInflater
        )
    }

    override fun onResume() {
        super.onResume()
        if (ProjectCache.getProject() != null) {
            binding.tvProjectName.text = ProjectCache.getProject()!!.projName
        }
    }

    override fun initView() {
        setContentView(binding.root)
        binding.titleView.backView.click { finish() }
        binding.tvUsername.text = AccountCache.getUsername()
        binding.tvUserguid.text = AccountCache.getUserGUID()
        binding.tvBillRecipient.text = AccountCache.getBillRecipient()
        val version = "Version:" + getVersionName()
        binding.tvVersion.text = version
        binding.layoutProject.click {
            openActivity<ChooseProjectActivity>()
        }
        binding.layoutBillRecipient.click {
            if (binding.layoutBillRecipientModify.isVisible) {
                binding.layoutBillRecipientModify.visibility = View.GONE
            } else {
                binding.layoutBillRecipientModify.visibility = View.VISIBLE
            }
        }
        binding.btConfirmBillRecipient.click {
            val billRecipient = binding.etBillRecipient.text.toString()
            if (billRecipient.valid) {
                AccountCache.saveBillRecipient(billRecipient)
                binding.tvBillRecipient.text = billRecipient
                hideSoftInput()
                toastSucc("保存成功")
            }
        }
        binding.layoutConnectSet.click {
            openActivity<ConnectSetActivity>()
        }
        InputTextManager.with(this)
            .addView(binding.etBillRecipient)
            .setMain(binding.btConfirmBillRecipient)
            .build()
        binding.btExitAccount.click {
            MessageDialog.Builder(this)
                .setMessage("确定退出当前账户?")
                .setConfirmListener {
                    AccountCache.saveLoginStatus(false)
                    finish()
                }
                .show()
        }
    }

    private fun getVersionName(): String {
        val packInfo = packageManager.getPackageInfo(packageName, 0)
        return packInfo.versionName
    }

}