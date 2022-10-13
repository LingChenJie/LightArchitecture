package com.android.architecture.domain.navigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;

import com.android.architecture.constant.ErrorCode;
import com.android.architecture.helper.Logger;
import com.android.architecture.ui.page.BaseActivity;

/**
 * File describe:为NavigationAction提供服务的Activity基类
 * Author: SuQi
 * Create date: 2022/8/3
 * Modify date: 2022/8/3
 * Version: 1
 */
public abstract class BaseActivityForNavigationAction extends BaseActivity {

    private boolean hasFinished = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NavigationConstant.getInstance().setCurrentActivity(this);
        addOnBackPressed();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        resetFinishedFlag();
        NavigationConstant.getInstance().setCurrentActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        resetFinishedFlag();
        NavigationConstant.getInstance().setCurrentActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void addOnBackPressed() {
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                clickBack();
            }
        });
    }

    protected void clickBack() {
        finish(new NavigationResult(ErrorCode.USER_CANCEL));
    }

    protected void finish(NavigationResult result) {
        if (hasFinished) {
            return;
        }
        hasFinished = true;
        doFinish(result);
    }

    private void doFinish(NavigationResult result) {
        ANavigationAction currentAction = NavigationConstant.getInstance().getCurrentAction();
        Logger.d(TAG, "doFinish action: " + currentAction);
        if (currentAction != null) {
            currentAction.setResult(result);
        } else {
            finish();
        }
    }

    protected void resetFinishedFlag() {
        hasFinished = false;
    }

}
