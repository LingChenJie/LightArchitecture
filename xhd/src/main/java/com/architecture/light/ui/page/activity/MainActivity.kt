package com.architecture.light.ui.page.activity

import android.view.View
import android.widget.ImageView
import com.android.architecture.constant.ErrorCode
import com.android.architecture.domain.transaction.TransactionConstant
import com.android.architecture.extension.click
import com.android.architecture.helper.Logger
import com.architecture.light.R
import com.architecture.light.app.AppActivity
import com.architecture.light.constant.GlobalParams
import com.architecture.light.databinding.ActivityMainBinding
import com.architecture.light.domain.task.SearchBillTask
import com.architecture.light.domain.transaction.PaymentTrans
import com.architecture.light.helper.BillHelper
import com.architecture.light.helper.PermissionsHelper
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
//        if (!AccountCache.getLoginStatus()) {
//            LogonTrans().execute()
//        }
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
                    BillHelper.saveBill(bill[0], false)
                    BillHelper.printBill(object : BillHelper.PrintResult {
                        override fun success() {
                            Logger.e("suqi", "success")
                        }

                        override fun fail(code: Int, msg: String) {
                            Logger.e("suqi", "fail code:$code;msg:$msg")
                        }

                    })
                }
//                BillHelper.printBill(object : BillHelper.PrintResult {
//                    override fun success() {
//                        Logger.e("suqi", "success")
//                    }
//
//                    override fun fail(code: Int, msg: String) {
//                        Logger.e("suqi", "fail code:$code;msg:$msg")
//                    }
//
//                })
            }
        }
        PermissionsHelper.requirePermissions(Permission.WRITE_EXTERNAL_STORAGE)
        binding.banner.setData(
            null, ImageView.ScaleType.CENTER_CROP,
            R.drawable.icon_banner_1,
            R.drawable.icon_banner_2,
            R.drawable.icon_banner_3
        )
    }

}