package com.android.architecture.domain.navigation;

import android.os.Handler;
import android.os.Looper;

import com.android.architecture.helper.Logger;
import com.android.architecture.ui.page.BaseActivity;
import com.android.architecture.ui.page.BaseFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Describe:控制导航执行的状态
 */
public abstract class ANavigation {
    protected final String TAG = this.getClass().getSimpleName();

    private Map<String, ANavigationAction> state2ActionMap;// state和action绑定关系表
    private String previousState;// 前一个执行步骤
    private String currentState;// 当前执行步骤
    private final TransEndListener transEndListener;
    protected static final Handler HANDLER = new Handler(Looper.getMainLooper());
    private static boolean navigationIsRunning = false;

    public ANavigation(TransEndListener transEndListener) {
        this.transEndListener = transEndListener;
    }

    /**
     * 执行交易
     */
    public void execute() {
        if (getNavigationIsRunningState()) {
            Logger.e(TAG, "There are transaction being executed.");
            return;
        }
        setNavigationIsRunningState(true);
        Logger.d(TAG, "trans start.");
        onPreExecute();
        if (checkExecutionConditions()) {
            bindStateOnAction();
        } else {
            setNavigationIsRunningState(false);
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
    protected void transEnd(NavigationResult result) {
        Logger.d(TAG, "trans end.");
        // 释放资源
        NavigationConstant.getInstance().setCurrentAction(null);
        NavigationConstant.getInstance().setCurrentActivity(null);
        NavigationConstant.getInstance().setCurrentFragment(null);
        setNavigationIsRunningState(false);
        HANDLER.post(() -> {
            if (transEndListener != null) {
                transEndListener.onEnd(result);
            }
        });
    }

    /**
     * state绑定action抽象方法
     * 在此实现中调用{@link #bind(String, ANavigationAction)}方法
     * 并在最后调用 {@link #gotoState(String)}方法，执行第一个state
     */
    protected abstract void bindStateOnAction();

    /**
     * action处理结果
     *
     * @param state
     * @param result
     */
    protected abstract void onActionResult(String state, NavigationResult result);

    /**
     * 绑定state和action
     *
     * @param state
     * @param action
     */
    protected void bind(String state, ANavigationAction action) {
        if (state2ActionMap == null) {
            state2ActionMap = new HashMap<>();
        }
        state2ActionMap.put(state, action);
        action.setEndListener((action2, result) -> {
            HANDLER.post(() -> {
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
        this.previousState = this.currentState;
        this.currentState = state;
        ANavigationAction action = getAction(state);
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
    protected ANavigationAction getAction(String state) {
        if (state2ActionMap != null) {
            return state2ActionMap.get(state);
        }
        return null;
    }

    /**
     * 获取当前是否有导航运行
     *
     * @return
     */
    public static boolean getNavigationIsRunningState() {
        return navigationIsRunning;
    }

    /**
     * 设置导航运行状态
     */
    protected static void setNavigationIsRunningState(boolean transIsRunning) {
        ANavigation.navigationIsRunning = transIsRunning;
    }

    /**
     * 获取当前导航的栈顶的Activity
     *
     * @return
     */
    protected BaseActivity getCurrentActivity() {
        return NavigationConstant.getInstance().getCurrentActivity();
    }

    /**
     * 获取当前导航的栈顶的Fragment
     *
     * @return
     */
    protected BaseFragment<? extends BaseActivity> getCurrentFragment() {
        return NavigationConstant.getInstance().getCurrentFragment();
    }

    /**
     * 获取当前交易前一个执行的的State
     *
     * @return
     */
    public String getPreviousState() {
        return previousState;
    }

    /**
     * 交易结果监听
     */
    public interface TransEndListener {
        void onEnd(NavigationResult result);
    }
}
