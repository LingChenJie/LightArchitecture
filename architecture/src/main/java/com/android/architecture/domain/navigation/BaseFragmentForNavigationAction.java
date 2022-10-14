package com.android.architecture.domain.navigation;

import com.android.architecture.constant.ErrorCode;
import com.android.architecture.helper.Logger;
import com.android.architecture.ui.page.BaseFragment;

/**
 * File describe:为NavigationAction提供服务的Activity基类
 * Author: SuQi
 * Create date: 2022/8/3
 * Modify date: 2022/8/3
 * Version: 1
 */
public abstract class BaseFragmentForNavigationAction<A extends BaseActivityForNavigationAction> extends BaseFragment<A> {

    private boolean hasFinished = false;

    @Override
    public void onResume() {
        super.onResume();
        resetFinishedFlag();
        NavigationConstant.getInstance().setCurrentFragment(this);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            resetFinishedFlag();
            NavigationConstant.getInstance().setCurrentFragment(this);
        }
    }

    @Override
    protected boolean onBackPressed() {
        clickBack();
        return true;
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
        currentAction.setResult(result);
    }

    protected void resetFinishedFlag() {
        hasFinished = false;
    }

}
