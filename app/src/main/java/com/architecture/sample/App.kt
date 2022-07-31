package com.architecture.sample

import android.app.Application
import com.android.architecture.utils.Utils

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/7/30
 * Modify date: 2022/7/30
 * Version: 1
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Utils.init(this)
    }

}