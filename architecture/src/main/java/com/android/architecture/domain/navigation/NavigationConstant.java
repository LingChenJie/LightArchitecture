package com.android.architecture.domain.navigation;

import com.android.architecture.ui.page.BaseActivity;
import com.android.architecture.ui.page.BaseFragment;

/**
 * File describe:导航过程中的常量，导航过程中进行赋值，交易结束进行置为null，防止内存泄漏
 * Author: SuQi
 * Create date: 2022/8/3
 * Modify date: 2022/8/3
 * Version: 1
 */
public class NavigationConstant {
    private static final String TAG = NavigationConstant.class.getSimpleName();
    private static final NavigationConstant INSTANCE = new NavigationConstant();

    private BaseActivity currentActivity;
    private BaseFragment<? extends BaseActivity> currentFragment;
    private ANavigationAction currentAction;

    private NavigationConstant() {
    }

    public static NavigationConstant getInstance() {
        return INSTANCE;
    }

    public BaseActivity getCurrentActivity() {
        return currentActivity;
    }

    public void setCurrentActivity(BaseActivity currentActivity) {
        this.currentActivity = currentActivity;
    }

    public BaseFragment<? extends BaseActivity> getCurrentFragment() {
        return currentFragment;
    }

    public void setCurrentFragment(BaseFragment<? extends BaseActivity> currentFragment) {
        this.currentFragment = currentFragment;
    }

    public ANavigationAction getCurrentAction() {
        return currentAction;
    }

    public void setCurrentAction(ANavigationAction currentAction) {
        this.currentAction = currentAction;
    }

}
