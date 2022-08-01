package com.android.architecture.transaction;

import android.os.Bundle;
import android.view.KeyEvent;

import androidx.annotation.Nullable;

import com.android.architecture.constant.LightConstant;
import com.android.architecture.helper.Logger;
import com.android.architecture.ui.page.BaseActivity;

/**
 * File describe:为Action提供的基类Activity
 */
public abstract class BaseActivityForAction extends BaseActivity {

    public static final String SINGLE_ACTION = "SINGLE_ACTION";

    private boolean hasFinished = false;
    private boolean isSingleAction = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isSingleAction = getIntent().getBooleanExtra(SINGLE_ACTION, false);
        if (isSingleAction) {
            AAction action = TransactionConstant.getInstance().getSingleAction(getSingleActionName());
            if (action == null) {
                Logger.e(LightConstant.TAG, TAG + " can't find a match Action");
                Logger.e(LightConstant.TAG, TAG + " can't find a match Action");
                Logger.e(LightConstant.TAG, TAG + " can't find a match Action");
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        resetFinishedFlag();
        TransactionConstant.getInstance().setCurrentActivity(this);
    }

    @Override
    protected void onDestroy() {
        Logger.d(TAG, "--onDestroy");
        clearAction();
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            clickBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void clickBack() {
        finish(new ActionResult(ActionCode.USER_CANCEL));
    }

    protected void finish(ActionResult result) {
        if (hasFinished) {
            return;
        }
        hasFinished = true;
        doFinish(result);
    }

    private void doFinish(ActionResult result) {
        AAction currentAction;
        if (isSingleAction) {
            currentAction = TransactionConstant.getInstance().getSingleAction(getSingleActionName());
        } else {
            currentAction = TransactionConstant.getInstance().getCurrentAction();
        }
        Logger.d(TAG, "doFinish action: " + currentAction);
        if (currentAction != null) {
            currentAction.setResult(result);
        } else {
            Logger.e(LightConstant.TAG, TAG + " can't find a match Action");
            Logger.e(LightConstant.TAG, TAG + " can't find a match Action");
            finish();
        }
    }

    /**
     * 返回对应Action的类名
     *
     * @return
     */
    protected String getSingleActionName() {
        throw new RuntimeException("You have to override this method.");
    }

    protected void resetFinishedFlag() {
        hasFinished = false;
    }

    private void clearAction() {
        if (isSingleAction) {
            AAction action = TransactionConstant.getInstance().getSingleAction(getSingleActionName());
            if (action != null) {
                action.clear();
            }
            TransactionConstant.getInstance().removeSingleAction(getSingleActionName());
        }
    }

}
