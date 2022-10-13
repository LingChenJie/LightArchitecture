package com.android.architecture.domain.navigation;

/**
 * File describe:定义导航的每一步骤
 * Author: SuQi
 * Create date: 2022/8/3
 * Modify date: 2022/8/3
 * Version: 1
 */
public abstract class ANavigationAction {
    private final String ACTION_NAME = getClass().getSimpleName();
    private final ActionStartListener actionStartListener;
    private ActionEndListener actionEndListener;

    /**
     * 子类构造方法必须调用super设置ActionStartListener
     *
     * @param listener {@link ActionStartListener}
     */
    public ANavigationAction(ActionStartListener listener) {
        this.actionStartListener = listener;
    }

    /**
     * 设置Action执行结束的监听
     *
     * @param listener
     */
    public ANavigationAction setEndListener(ActionEndListener listener) {
        this.actionEndListener = listener;
        return this;
    }

    /**
     * 执行ACTION之前需要先设置{@link ActionEndListener},
     * 此接口内部先调用 {@link ActionStartListener#onStart(ANavigationAction)}
     * , 再调用 {@link ANavigationAction#onExecute}
     */
    public void execute() {
        NavigationConstant.getInstance().setCurrentAction(this);
        if (actionStartListener != null) {
            actionStartListener.onStart(this);
        }
        onExecute();
    }

    /**
     * Action执行的时候调用
     */
    protected abstract void onExecute();

    /**
     * 设置action结果, 此接口内部调用{@link ActionEndListener#onEnd(ANavigationAction, NavigationResult)} 方法
     *
     * @param result
     */
    protected void setResult(NavigationResult result) {
        setResult(result, true);
    }

    /**
     * 设置action结果, 此接口内部调用{@link ActionEndListener#onEnd(ANavigationAction, NavigationResult)} 方法
     *
     * @param result
     */
    protected void setResult(NavigationResult result, boolean isClear) {
        if (isClear) {
            clear();
        }
        if (actionEndListener != null) {
            actionEndListener.onEnd(this, result);
        }
    }

    /**
     * 释放资源
     */
    void clear() {
        onClear();
    }

    /**
     * 资源的释放
     */
    protected void onClear() {
    }

    /**
     * 获取当前导航的栈顶Activity
     *
     * @return
     */
    protected BaseActivityForNavigationAction getActivity() {
        return NavigationConstant.getInstance().getCurrentActivity();
    }

    /**
     * 获取当前导航的栈顶Fragment
     *
     * @return
     */
    protected BaseFragmentForNavigationAction<? extends BaseActivityForNavigationAction> getFragment() {
        return NavigationConstant.getInstance().getCurrentFragment();
    }

    /**
     * action 执行开始的接口
     */
    public interface ActionStartListener {
        void onStart(ANavigationAction action);
    }

    /**
     * action 执行结束的接口
     */
    public interface ActionEndListener {
        void onEnd(ANavigationAction action, NavigationResult result);
    }
}
