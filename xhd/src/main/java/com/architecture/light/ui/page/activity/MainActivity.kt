package com.architecture.light.ui.page.activity

import android.content.Intent
import android.view.View
import com.android.architecture.domain.transaction.TransactionConstant
import com.android.architecture.extension.click
import com.android.architecture.helper.Logger
import com.architecture.light.app.AppActivity
import com.architecture.light.databinding.ActivityMainBinding
import com.architecture.light.helper.PermissionsHelper
import com.hjq.permissions.Permission

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
//        if (LoginCache.getUsername().empty || ProjectCache.getProject() == null) {
//            LogonTrans().execute()
//        }
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
            permissionCamera()
            //PaymentTrans().execute()
        }
        binding.layoutCommon.click {
            startActivity(Intent(this, CommonActivity::class.java))
        }
    }

    fun permissionCamera() {
        PermissionsHelper.requirePermissions(Permission.CAMERA) {
            Logger.e("tag", "获得了权限")
        }
    }

}