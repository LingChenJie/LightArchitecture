package com.android.architecture.ui.page;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;

import com.android.architecture.data.manage.NetworkStateManager;
import com.android.architecture.extension.ViewKt;
import com.android.architecture.ui.page.action.BundleAction;
import com.android.architecture.ui.scope.ViewModelScope;
import com.android.architecture.utils.AdaptScreenUtils;
import com.android.architecture.utils.ScreenUtils;

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/7/28
 * Modify date: 2022/7/28
 * Version: 1
 */
public abstract class BaseActivity extends AppCompatActivity implements BundleAction {

    protected String TAG = this.getClass().getSimpleName();
    private static final int STATUS_BAR_TRANSPARENT_COLOR = 0x00000000;
    private final ViewModelScope mViewModelScope = new ViewModelScope();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        transparentStatusBar();
        super.onCreate(savedInstanceState);
        ActivityStack.getInstance().add(this);

        getLifecycle().addObserver(NetworkStateManager.getInstance());

        initView();
        initSoftKeyboard();
        output();
        input();
    }

    protected abstract void initView();

    protected void output() {
    }

    protected void input() {
    }

    @Override
    public Resources getResources() {
        if (ScreenUtils.isPortrait()) {
            return AdaptScreenUtils.adaptWidth(super.getResources(), 360);
        } else {
            return AdaptScreenUtils.adaptHeight(super.getResources(), 640);
        }
    }

    @Override
    protected void onDestroy() {
        ActivityStack.getInstance().remove(this);
        super.onDestroy();
    }

    @Override
    public Bundle getBundle() {
        return getIntent().getExtras();
    }

    protected <T extends ViewModel> T getActivityScopeViewModel(@NonNull Class<T> modelClass) {
        return mViewModelScope.getActivityScopeViewModel(this, modelClass);
    }

    protected <T extends ViewModel> T getApplicationScopeViewModel(@NonNull Class<T> modelClass) {
        return mViewModelScope.getApplicationScopeViewModel(modelClass);
    }

    protected void transparentStatusBar() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        int option = View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        int systemUiVisibility = window.getDecorView().getSystemUiVisibility();
        window.getDecorView().setSystemUiVisibility(option | systemUiVisibility);
        window.setStatusBarColor(STATUS_BAR_TRANSPARENT_COLOR);
    }

    protected void initSoftKeyboard() {
        // 点击外部隐藏软键盘，提升用户体验
        getContentView().setOnClickListener(v -> {
            if (getCurrentFocus() != null)
                ViewKt.hideKeyboard(getCurrentFocus());
        });
    }

    /**
     * 和 setContentView 对应的方法
     */
    public ViewGroup getContentView() {
        return findViewById(Window.ID_ANDROID_CONTENT);
    }

}
