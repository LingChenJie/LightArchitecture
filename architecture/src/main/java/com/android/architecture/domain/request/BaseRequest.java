package com.android.architecture.domain.request;

import com.android.architecture.helper.MainThreadHelper;

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/8/3
 * Modify date: 2022/8/3
 * Version: 1
 */
public abstract class BaseRequest<T, R> implements IRequest<T, R> {

    protected T param;
    protected R response;

    @Override
    public R execute(T param) {
        if (MainThreadHelper.INSTANCE.judgeMainThread()) {
            throw new RuntimeException("The request cannot be executed in the main thread.");
        }
        doExecute(param);
        return response;
    }

    private void doExecute(T param) {
        this.param = param;
        initParams(param);
        onPreExecute();
        onExecute();
    }

    protected abstract void initParams(T param);

    protected abstract void onPreExecute();

    protected abstract void onExecute();

}
