package com.android.architecture.domain.request;

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/8/3
 * Modify date: 2022/8/3
 * Version: 1
 */
public interface IRequest<T, R> {
    R execute(T param);
}
