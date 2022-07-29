package com.android.architecture.utils;

import com.android.architecture.helper.AppExecutors;

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/7/29
 * Modify date: 2022/7/29
 * Version: 1
 */
public class AsyncTask {

    public static <T> void doAction(ActionStart<T> start, ActionEnd<T> end) {
        AppExecutors.getInstance().io().execute(() -> {
            T data = start.getData();
            AppExecutors.getInstance().main().execute(() -> {
                end.onResult(data);
            });
        });
    }

    public interface ActionStart<T> {
        T getData();
    }

    public interface ActionEnd<T> {
        void onResult(T t);
    }

}
