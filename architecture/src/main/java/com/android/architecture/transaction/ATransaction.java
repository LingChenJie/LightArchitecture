package com.android.architecture.transaction;

import android.os.Handler;
import android.os.Looper;

import com.android.architecture.helper.Logger;
import com.android.architecture.ui.page.BaseActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * Describe:控制交易执行的状态
 */
public abstract class ATransaction {
    protected String TAG = this.getClass().getSimpleName();

    private Map<String, AAction> state2ActionMap;// state和action绑定关系表
    private String currentState;// 当前执行步骤
    private final TransEndListener transEndListener;
    protected final Handler handler = new Handler(Looper.getMainLooper());
    private static boolean transIsRunning = false;

    public ATransaction(TransEndListener transEndListener) {
        this.transEndListener = transEndListener;
    }

    /**
     * 执行交易
     */
    public void execute() {
        if (getTransIsRunningState()) {
            Logger.e(TAG, "There are transaction being executed.");
            return;
        }
        setTransIsRunningState(true);
        Logger.d(TAG, "trans start.");
        onPreExecute();
        if (checkExecutionConditions()) {
            bindStateOnAction();
        } else {
            setTransIsRunningState(false);
        }
    }

    /**
     * 交易执行前调用
     */
    protected void onPreExecute() {
    }

    /**
     * 检查执行条件
     *
     * @return
     */
    protected boolean checkExecutionConditions() {
        return true;
    }

    /**
     * 设置交易执行结果
     *
     * @param result
     */
    protected void transEnd(ActionResult result) {
        Logger.d(TAG, "trans end.");
        // 释放资源
        TransactionConstant.getInstance().setCurrentAction(null);
        TransactionConstant.getInstance().setCurrentActivity(null);
        setTransIsRunningState(false);
        handler.post(() -> {
            if (transEndListener != null) {
                transEndListener.onEnd(result);
            }
        });
    }

    /**
     * state绑定action抽象方法
     * 在此实现中调用{@link #bind(String, AAction)}方法
     * 并在最后调用 {@link #gotoState(String)}方法，执行第一个state
     */
    protected abstract void bindStateOnAction();

    /**
     * action处理结果
     *
     * @param state
     * @param result
     */
    protected abstract void onActionResult(String state, ActionResult result);

    /**
     * 绑定state和action
     *
     * @param state
     * @param action
     */
    protected void bind(String state, AAction action) {
        if (state2ActionMap == null) {
            state2ActionMap = new HashMap<>();
        }
        state2ActionMap.put(state, action);
        action.setEndListener((action2, result) -> {
            handler.post(() -> {
                Logger.d(TAG, "action " + currentState + " end.");
                Logger.d(TAG, "action " + currentState + " result: (code:" + result.code + "; message:" + result.message + ")");
                onActionResult(currentState, result);
            });
        });
    }

    /**
     * 执行执行绑定的action
     *
     * @param state
     */
    protected void gotoState(String state) {
        Logger.d(TAG, "action " + state + " start.");
        this.currentState = state;
        AAction action = getAction(state);
        if (action != null) {
            action.execute();
        } else {
            throw new RuntimeException("无效的state:" + state);
        }
    }

    /**
     * 根据state获取的Action
     *
     * @param state
     * @return
     */
    protected AAction getAction(String state) {
        if (state2ActionMap != null) {
            return state2ActionMap.get(state);
        }
        return null;
    }

    /**
     * 获取当前是否有交易运行
     *
     * @return
     */
    public static boolean getTransIsRunningState() {
        return transIsRunning;
    }

    /**
     * 设置交易运行状态
     */
    protected static void setTransIsRunningState(boolean transIsRunning) {
        ATransaction.transIsRunning = transIsRunning;
    }

    /**
     * 获取当前交易的栈顶的Activity
     *
     * @return
     */
    protected BaseActivity getCurrentActivity() {
        return TransactionConstant.getInstance().getCurrentActivity();
    }

    /**
     * 交易结果监听
     */
    public interface TransEndListener {
        void onEnd(ActionResult result);
    }

}
