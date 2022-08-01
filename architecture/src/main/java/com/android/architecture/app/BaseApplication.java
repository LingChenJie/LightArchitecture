package com.android.architecture.app;

import android.app.Application;

import com.android.architecture.utils.AppUtils;
import com.tencent.mmkv.MMKV;

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/8/1
 * Modify date: 2022/8/1
 * Version: 1
 */
public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppUtils.init(this);
        MMKV.initialize(this);
    }

}
