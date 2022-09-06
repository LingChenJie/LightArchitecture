package com.architecture.light.ui.page.activity

import android.content.Intent
import android.view.View
import com.android.architecture.domain.transaction.TransactionConstant
import com.android.architecture.extension.click
import com.android.architecture.helper.Logger
import com.architecture.light.helper.PermissionsHelper
import com.architecture.light.app.AppActivity
import com.architecture.light.databinding.ActivityMainBinding
<<<<<<< HEAD
import com.hjq.permissions.Permission
=======
import com.architecture.light.domain.task.SearchRoomTask
import com.architecture.light.domain.transaction.LogonTrans
import com.architecture.light.domain.transaction.PaymentTrans
import com.architecture.light.settings.LoginCache
import com.architecture.light.settings.ProjectCache
import kotlin.concurrent.thread
>>>>>>> 4b55c9d66878ca35e024c4ef88cb36173d97927c

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/8/11
 * Modify date: 2022/8/11
 * Version: 1
 */
class MainActivity : AppActivity() {

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onResume() {
        super.onResume()
        TransactionConstant.getInstance().currentActivity = this
<<<<<<< HEAD
//        if (LoginCache.getUsername().empty) {
//            LogonTrans().execute()
//        }
=======
        if (LoginCache.getUsername().empty || ProjectCache.getProject() == null) {
            LogonTrans().execute()
        }
>>>>>>> 4b55c9d66878ca35e024c4ef88cb36173d97927c
    }

    override fun initView() {
        setContentView(binding.root)
        binding.titleView.apply {
            backIcon.visibility = View.GONE
        }
        binding.layoutMvi.click {
            //startActivity(Intent(this, MviActivity::class.java))
//            thread {
//                SearchRoomTask().execute(GlobalParams.newTransData())
//            }
            PermissionsHelper.requirePermissions(Permission.CAMERA) {
                Logger.e("tag", "获得了权限")
            }
            PaymentTrans().execute()
        }
        binding.layoutCommon.click {
            startActivity(Intent(this, CommonActivity::class.java))
        }
    }

}