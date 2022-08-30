package com.android.architecture.domain.task;

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/8/3
 * Modify date: 2022/8/3
 * Version: 1
 */
public interface ITask<P, R> {
    R execute(P param);
}
