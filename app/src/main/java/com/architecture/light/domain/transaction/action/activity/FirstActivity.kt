package com.architecture.light.domain.transaction.action.activity

import com.android.architecture.constant.ErrorCode
import com.android.architecture.domain.navigation.NavigationResult
import com.android.architecture.extension.argument
import com.android.architecture.extension.binding
import com.android.architecture.extension.click
import com.android.architecture.helper.Logger
import com.android.architecture.utils.KeyStoreUtils
import com.architecture.light.app.AppActivityForNavigationAction
import com.architecture.light.databinding.Activity1Binding

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/10/13
 * Modify date: 2022/10/13
 * Version: 1
 */
class FirstActivity : AppActivityForNavigationAction() {

    private val binding: Activity1Binding by binding()
    private val name: String by argument()

    override fun initView() {
        setContentView(binding.root)
        Logger.e(TAG, "name:$name")
        binding.btTo2.click {
            finish(NavigationResult(ErrorCode.SUCCESS))
        }
        binding.btTest.click {
            val encryptFromKeyStore = KeyStoreUtils.encryptFromKeyStore("1122334455667788", "xinhua")
            Logger.e("suqi", "encryptFromKeyStore: $encryptFromKeyStore")
        }
    }

}