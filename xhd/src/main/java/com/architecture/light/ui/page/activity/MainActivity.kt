package com.architecture.light.ui.page.activity

import android.view.View
import android.widget.ImageView
import com.android.architecture.domain.transaction.TransactionConstant
import com.android.architecture.extension.click
import com.android.architecture.extension.openActivity
import com.architecture.light.R
import com.architecture.light.app.AppActivity
import com.architecture.light.data.model.db.entity.TransData
import com.architecture.light.databinding.ActivityMainBinding
import com.architecture.light.domain.task.SearchPaymentListTask
import com.architecture.light.domain.transaction.LogonTrans
import com.architecture.light.domain.transaction.PaymentTrans
import com.architecture.light.domain.transaction.ReserveTrans
import com.architecture.light.domain.transaction.VoidTrans
import com.architecture.light.settings.AccountCache
import com.gyf.immersionbar.ImmersionBar
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
        ImmersionBar.setTitleBar(this, binding.titleView)
        binding.ivUserAvatar.click { openActivity<AccountManageActivity>() }
        binding.cvPayment.click { PaymentTrans().execute() }
        binding.cvPledgeMoney.click { ReserveTrans().execute() }
        binding.cvVoid.click { VoidTrans().execute() }
        binding.cvPrint.click {
            thread {
                SearchPaymentListTask().execute(TransData())
            }
        }

        val bannerImages =
            arrayOf(
                R.drawable.icon_main_banner_1,
                R.drawable.icon_main_banner_2,
                R.drawable.icon_main_banner_3,
            )
        val bannerViews = mutableListOf<View>()
        for (bannerImage in bannerImages) {
            val bannerView = View.inflate(this, R.layout.main_banner_view, null)
            bannerView.findViewById<ImageView>(R.id.iv_image).setImageResource(bannerImage)
            bannerViews.add(bannerView)
        }
        binding.banner.setData(bannerViews)
    }

}