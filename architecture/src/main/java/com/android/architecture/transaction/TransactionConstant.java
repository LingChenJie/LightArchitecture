package com.android.architecture.transaction;

import android.annotation.SuppressLint;

import com.android.architecture.helper.Logger;
import com.android.architecture.ui.page.BaseActivity;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Describe:交易过程常量
 */
public class TransactionConstant {

    private static final String TAG = TransactionConstant.class.getSimpleName();

    @SuppressLint("StaticFieldLeak")
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
