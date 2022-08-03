package com.android.architecture.domain.transaction;

/**
 * File describe:定义交易的每一步骤
 * Author: SuQi
 * Create date: 2022/8/3
 * Modify date: 2022/8/3
 * Version: 1
 */
public abstract class AAction {
    protected String TAG = getClass().getSimpleName();
    private final ActionStartListener actionStartListener;
    private ActionEndListener actionEndListener;

    /**
     * 子类构造方法必须调用super设置ActionStartListener
     *
     * @param listener {@link ActionStartListener}
     */
    public AAction(ActionStartListener listener) {
        this.actionStartListener = listener;
    }

    /**
     * 设置Action执行结束的监听
     *
     * @param listener
     */
    public AAction setEndListener(ActionEndListener listener) {
        this.actionEndListener = listener;
        return this;
    }

    /**
     * 执行ACTION之前需要先设置{@link ActionEndListener},
     * 此接口内部先调用 {@link ActionStartListener#onStart(AAction)}
     * , 再调用 {@link AAction#onExecute}
     */
    public void execute() {
        if (isSingleAction()) {
            TransactionConstant.getInstance().addSingleAction(TAG, this);
        } else {
            TransactionConstant.getInstance().setCurrentAction(this);
        }
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
     * 设置Action为一个独立于交易外的Action
     *
     * @return
     */
    protected boolean isSingleAction() {
        return false;
    }

    /**
     * 设置action结果, 此接口内部调用{@link ActionEndListener#onEnd(AAction, ActionResult)} 方法
     *
     * @param result
     */
    public void setResult(ActionResult result) {
        clear();
        if (actionEndListener != null) {
            actionEndListener.onEnd(this, result);
        }
    }

    public void clear() {
        onClear();
    }

    /**
     * 资源的释放
     */
    protected void onClear() {
    }

    /**
     * 移除当前Action
     * 有对应Activity的不用调用这个方法，会在对应Activity的onDestroy移除当前Action
     */
    protected void removeCurrentAction() {
        if (isSingleAction()) {
            TransactionConstant.getInstance().removeSingleAction(TAG);
        }
    }

    /**
     * action 执行开始的接口
     */
    public interface ActionStartListener {
        void onStart(AAction action);
    }

    /**
     * action 执行结束的接口
     */
    public interface ActionEndListener {
        void onEnd(AAction action, ActionResult result);
    }
}
