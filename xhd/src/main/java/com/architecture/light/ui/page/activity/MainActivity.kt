package com.architecture.light.ui.page.activity

import android.os.Environment
import android.view.View
import com.android.architecture.constant.ErrorCode
import com.android.architecture.domain.transaction.TransactionConstant
import com.android.architecture.extension.click
import com.android.architecture.extension.getContext
import com.android.architecture.extension.measuredView
import com.android.architecture.helper.Logger
import com.architecture.light.app.AppActivity
import com.architecture.light.constant.GlobalParams
import com.architecture.light.databinding.ActivityMainBinding
import com.architecture.light.domain.task.SearchBillTask
import com.architecture.light.domain.transaction.LogonTrans
import com.architecture.light.domain.transaction.PaymentTrans
import com.architecture.light.helper.PermissionsHelper
import com.architecture.light.print.view.PreviewBillView
import com.architecture.light.settings.AccountCache
import com.architecture.light.utils.ImageUtils
import com.hjq.permissions.Permission
import kotlin.concurrent.thread

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
        if (!AccountCache.getLoginStatus()) {
            LogonTrans().execute()
        }
    }

    override fun initView() {
        setContentView(binding.root)
        binding.titleView.apply {
            backIcon.visibility = View.GONE
        }
        binding.layoutMvi.click {
            PaymentTrans().execute()
        }

        binding.layoutCommon.click {
            //startActivity(Intent(this, CommonActivity::class.java))
            thread {
                val transData = SearchBillTask().execute(GlobalParams.initTransData())
                if (transData.responseCode == ErrorCode.SUCCESS) {
                    val bill = transData.searchBillResponse!!.data
                    val billView = PreviewBillView(getContext())
                    bill[0].printNum = "第一联"
                    billView.fullData(bill[0])

                    val path = Environment.getExternalStorageDirectory().path
                    billView.measuredView(1240, 1754)
                    Logger.d("suqi", "开始。。。")
                    ImageUtils.viewSaveImage(billView, "$path/test.png")
//                    ImageUtils.viewSaveImage(billView, "$path/test2.png")
//                    ImageUtils.viewSaveImage(billView, "$path/test3.png")
                    Logger.d("suqi", "通过。。。")
                }
            }
        }
        PermissionsHelper.requirePermissions(Permission.WRITE_EXTERNAL_STORAGE)
    }

}