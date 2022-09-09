package com.architecture.light.ui.page.activity

import android.content.Intent
import android.os.Environment
import android.view.View
import com.android.architecture.domain.transaction.TransactionConstant
import com.android.architecture.extension.click
import com.android.architecture.extension.getContext
import com.android.architecture.extension.measuredView
import com.android.architecture.helper.Logger
import com.architecture.light.app.AppActivity
import com.architecture.light.databinding.ActivityMainBinding
import com.architecture.light.helper.PermissionsHelper
import com.architecture.light.print.view.PreviewBillView
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
//            thread {
//                SearchRoomTask().execute(GlobalParams.newTransData())
//            }
//            PaymentTrans().execute()
//            val images =
//                arrayOf("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fc-ssl.duitang.com%2Fuploads%2Fitem%2F201902%2F23%2F20190223231014_csxbp.thumb.400_0.jpg&refer=http%3A%2F%2Fc-ssl.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1665297652&t=900d1ee5770f512c8328246188f8c153")
            val path = Environment.getExternalStorageDirectory().path
//            val images = arrayOf("$path/test.png")
//            thread {
//                PdfHelper.imgTransformPdf(images, "$path/test.pdf")
//                Logger.d("suqi", "通过。。。")
//            }
            thread {
                val billView = PreviewBillView(getContext())
                billView.fullData()
                val displayMetrics = resources.displayMetrics
                billView.measuredView(1240, 1754)
                ImageUtils.viewSaveImage(billView, "$path/test.png")
                ImageUtils.viewSaveImage(billView, "$path/test2.png")
                ImageUtils.viewSaveImage(billView, "$path/test3.png")
                val images = arrayOf("$path/test.png")
                Logger.d("suqi", "通过。。。")
            }
        }
//        val billView = PreviewBillView(getContext())
//        billView.fullData()
//        binding.layoutContent.addView(billView)
        binding.layoutCommon.click {
            startActivity(Intent(this, CommonActivity::class.java))
        }
        PermissionsHelper.requirePermissions(Permission.WRITE_EXTERNAL_STORAGE)
    }

}