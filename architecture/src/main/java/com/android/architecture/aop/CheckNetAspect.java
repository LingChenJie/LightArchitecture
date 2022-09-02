package com.android.architecture.aop;

import com.android.architecture.R;
import com.android.architecture.extension.ToastKt;
import com.android.architecture.utils.NetworkUtils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2020/01/11
 * desc   : 网络检测切面
 */
@Aspect
public class CheckNetAspect {

    /**
     * 方法切入点
     */
    @Pointcut("execution(@com.android.architecture.aop.CheckNet * *(..))")
    public void method() {
    }

    /**
     * 在连接点进行方法替换
     */
    @Around("method() && @annotation(checkNet)")
    public void aroundJoinPoint(ProceedingJoinPoint joinPoint, CheckNet checkNet) throws Throwable {
        if (!NetworkUtils.isConnected()) {
            ToastKt.toast(R.string.common_network_hint);
            return;
        }
        //执行原方法
        joinPoint.proceed();
    }
}