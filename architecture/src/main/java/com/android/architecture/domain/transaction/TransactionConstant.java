package com.android.architecture.domain.transaction;

import com.android.architecture.helper.Logger;
import com.android.architecture.ui.page.BaseActivity;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * File describe:交易过程中的常量，交易初始进行赋值，交易结束进行置为null，防止内存泄漏
 * Author: SuQi
 * Create date: 2022/8/3
 * Modify date: 2022/8/3
 * Version: 1
 */
public class TransactionConstant {
    private static final String TAG = TransactionConstant.class.getSimpleName();
    private static final TransactionConstant INSTANCE = new TransactionConstant();

    private BaseActivity currentActivity;
    private AAction currentAction;
    private final Map<String, AAction> singleActionMap = new HashMap<>();

    private TransactionConstant() {
    }

    public static TransactionConstant getInstance() {
        return INSTANCE;
    }

    public BaseActivity getCurrentActivity() {
        return currentActivity;
    }

    public void setCurrentActivity(BaseActivity currentActivity) {
        this.currentActivity = currentActivity;
    }

    public AAction getCurrentAction() {
        return currentAction;
    }

    public void setCurrentAction(AAction currentAction) {
        this.currentAction = currentAction;
    }

    public void addSingleAction(String name, AAction action) {
        singleActionMap.put(name, action);
        Logger.d(TAG, "addSingleAction: " + name + "; size:" + singleActionMap.size());
    }

    public AAction getSingleAction(String name) {
        AAction action = null;
        if (singleActionMap.size() > 0) {
            Set<Map.Entry<String, AAction>> entries = singleActionMap.entrySet();
            for (Map.Entry<String, AAction> entry : entries) {
                if (name.equals(entry.getKey())) {
                    action = entry.getValue();
                    break;
                }
            }
        }
        Logger.d(TAG, "getSingleAction: " + name + "; action:" + action);
        return action;
    }

    public void removeSingleAction(String name) {
        singleActionMap.remove(name);
        Logger.d(TAG, "removeSingleAction: " + name + "; size:" + singleActionMap.size());
    }
}
