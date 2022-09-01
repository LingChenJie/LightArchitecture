package com.android.architecture.utils;

import android.os.CountDownTimer;

/**
 * 计时器
 */
public class Timer extends CountDownTimer {

    public interface TimerListener {
        void onFinish();

        void onTick(long leftTime);
    }

    private TimerListener listener;

    public void setTimerListener(TimerListener listener) {
        this.listener = listener;
    }

    public Timer(long timeout) {
        this(timeout, 1);
    }

    public Timer(long timeout, long tickInterval) {
        super(timeout * 1000, tickInterval * 1000);
    }

    @Override
    public void onFinish() {
        if (listener != null) {
            listener.onFinish();
        }
    }

    @Override
    public void onTick(long millisUntilFinished) {
        if (listener != null) {
            listener.onTick(millisUntilFinished / 1000);
        }
    }

}
