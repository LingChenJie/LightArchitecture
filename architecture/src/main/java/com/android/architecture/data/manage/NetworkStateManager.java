package com.android.architecture.data.manage;

import android.content.IntentFilter;
import android.net.ConnectivityManager;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import com.android.architecture.utils.AppUtils;

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/7/28
 * Modify date: 2022/7/28
 * Version: 1
 */
public class NetworkStateManager implements DefaultLifecycleObserver {

    private NetworkStateManager() {
    }

    private static final NetworkStateManager INSTANCE = new NetworkStateManager();
    private final NetworkStateReceiver mNetworkStateReceiver = new NetworkStateReceiver();

    public static NetworkStateManager getInstance() {
        return INSTANCE;
    }

    @Override
    public void onResume(@NonNull LifecycleOwner owner) {
        DefaultLifecycleObserver.super.onResume(owner);
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        AppUtils.getApp().getApplicationContext().registerReceiver(mNetworkStateReceiver, filter);
    }

    @Override
    public void onPause(@NonNull LifecycleOwner owner) {
        AppUtils.getApp().getApplicationContext().unregisterReceiver(mNetworkStateReceiver);
    }

}
