package com.architecture.light.ui.page.activity

import android.view.View
import android.widget.ImageView
import com.android.architecture.domain.transaction.TransactionConstant
import com.android.architecture.extension.click
import com.android.architecture.extension.openActivity
import com.android.architecture.helper.AppExecutors
import com.android.architecture.helper.Logger
import com.android.architecture.utils.FileUtils
import com.architecture.light.R
import com.architecture.light.app.AppActivity
import com.architecture.light.data.model.TransDataModel
import com.architecture.light.databinding.ActivityMainBinding
import com.architecture.light.domain.transaction.*
import com.architecture.light.settings.AccountCache
import com.gyf.immersionbar.ImmersionBar
import java.io.File


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
        clearData()
    }

    override fun initView() {
        setContentView(binding.root)
        ImmersionBar.setTitleBar(this, binding.titleView)
        binding.ivUserAvatar.click { openActivity<AccountManageActivity>() }
        binding.cvPayment.click { PaymentTrans().execute() }
        binding.cvPledgeMoney.click { ReserveTrans().execute() }
        binding.cvVoid.click { VoidTrans().execute() }
        binding.cvPaymentSynchronize.click { openActivity<PaymentSyncActivity>() }
        binding.cvVoidSynchronize.click { openActivity<VoidSyncActivity>() }
        binding.cvPrint.click { PrintTrans().execute() }

        val bannerImages =
            arrayOf(
                R.drawable.icon_main_banner_new_1,
                R.drawable.icon_main_banner_new_2,
                R.drawable.icon_main_banner_new_3,
            )
        val bannerViews = mutableListOf<View>()
        for (bannerImage in bannerImages) {
            val bannerView = View.inflate(this, R.layout.main_banner_view, null)
            bannerView.findViewById<ImageView>(R.id.iv_image).setImageResource(bannerImage)
            bannerViews.add(bannerView)
        }
        binding.banner.setData(bannerViews)
    }

    private fun clearData() {
        AppExecutors.getInstance().io().execute {
            deleteLog()
            deleteTrans()
        }
    }

    private fun deleteLog() {
        val logPath = FileUtils.getSDCardRootPath() + "/umsips/umsgmc.log"
        val logFile = File(logPath)
        if (logFile.exists()) {
            val size = FileUtils.getFileSize(logPath) / FileUtils.MB
            Logger.i(TAG, "log size:$size MB")
            if (size > 200) {
                val result = logFile.delete()
                Logger.e(TAG, "delete log file，result:$result")
            }
        }
    }

    private fun deleteTrans() {
        val count = TransDataModel.getCount()
        Logger.i(TAG, "transData count:$count")
        if (count > 2000) {
            val size = TransDataModel.deleteOldSuccessData(7)
            Logger.e(TAG, "delete transData count:$size")
        }
    }

}