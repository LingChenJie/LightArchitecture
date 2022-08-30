package com.android.architecture.domain.task;

import com.android.architecture.helper.MainThreadHelper;

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/8/3
 * Modify date: 2022/8/3
 * Version: 1
 */
public abstract class BaseTask<P, R> implements ITask<P, R> {

    protected P param;
    protected R response;

    @Override
    public R execute(P param) {
        if (MainThreadHelper.INSTANCE.judgeMainThread()) {
            throw new RuntimeException("The task cannot be executed in the main thread.");
        }
        doExecute(param);
        return response;
    }

    private void doExecute(P param) {
        this.param = param;
        initParams(param);
        onPreExecute();
        onExecute();
    }

    protected abstract void initParams(P param);

    protected abstract void onPreExecute();

    protected abstract void onExecute();

}
