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
import com.architecture.light.databinding.ActivityMain2Binding
import com.architecture.light.domain.task.SearchBillTask
import com.architecture.light.domain.transaction.LogonTrans
import com.architecture.light.helper.BillHelper
import com.architecture.light.settings.AccountCache
import com.architecture.light.ui.dialog.AmountModifyDialog
import kotlin.concurrent.thread


/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/8/11
 * Modify date: 2022/8/11
 * Version: 1
 */
class MainActivity : AppActivity() {

    private val binding: ActivityMain2Binding by lazy { ActivityMain2Binding.inflate(layoutInflater) }

    override fun onResume() {
        super.onResume()
        TransactionConstant.getInstance().currentActivity = this
        if (!AccountCache.getLoginStatus()) {
            LogonTrans().execute()
        }
    }

    override fun initView() {
        setContentView(binding.root)
        binding.layoutMvi.click {
//            PaymentTrans().execute()
            AmountModifyDialog.Builder(this)
                .setAmount(-8888.88, -9.9)
                .setCancelable(false)
                .create()
                .show()
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

        val bannerImages =
            arrayOf(
                R.drawable.icon_banner_1,
                R.drawable.icon_banner_2,
                R.drawable.icon_banner_3
            )
        val bannerViews = mutableListOf<View>()
        for (bannerImage in bannerImages) {
            val bannerView = View.inflate(this, R.layout.main_banner_view, null)
            bannerView.findViewById<ImageView>(R.id.iv_image).setImageResource(bannerImage)
            bannerViews.add(bannerView)
        }
//        binding.banner.setData(bannerViews)
    }

}