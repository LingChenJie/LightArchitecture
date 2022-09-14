package com.android.architecture.ui.page;

import android.annotation.SuppressLint;
import android.app.Activity;

import com.android.architecture.helper.Logger;

import java.util.Stack;

/**
 * Describe:Activity管理
 */
public class ActivityStack {
    private static final String TAG = ActivityStack.class.getSimpleName();

    @SuppressLint("StaticFieldLeak")
    private static final ActivityStack INSTANCE = new ActivityStack();
    private static Stack<Activity> activityStack;
    private Activity topActivity;
    private Activity resumedActivity;

    private ActivityStack() {
    }

    public static ActivityStack getInstance() {
        return INSTANCE;
    }

    /**
     * 添加一个Activity
     *
     * @param activity
     */
    public void add(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
    }

    /**
     * 移除一个activity
     *
     * @param activity
     */
    public void remove(Activity activity) {
        if (activityStack != null) {
            activityStack.remove(activity);
        }
    }

    /**
     * 移除顶层Activity
     */
    public void removeTop() {
        try {
            if (activityStack != null) {
                Activity activity = activityStack.lastElement();
                if (activity != null) {
                    activityStack.remove(activity);
                    activity.finish();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 移除 除自己外的顶层Activity
     *
     * @param activity
     */
    public void removeTopUtilSelf(Activity activity) {
        if (activity != null) {
            while (true) {
                Activity top = getTop();
                if (activity == top) {
                    break;
                }
                if (top != null) {
                    activityStack.remove(top);
                    top.finish();
                }
            }
        }
    }

    /**
     * 获取栈顶Activity
     *
     * @return
     */
    private Activity getTop() {
        if (activityStack.empty()) {
            return null;
        }
        try {
            return activityStack.lastElement();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 移除 除栈底外的Activity
     */
    public void removeAllUtilBottom() {
        while (true) {
            Activity top = getTop();
            if (top == null || top == activityStack.firstElement()) {
                break;
            }
            activityStack.remove(top);
            top.finish();
        }
    }

    /**
     * 除少数外全部移除
     *
     * @param classArray 白名单 Activity
     */
    @SafeVarargs
    public final void removeAllButFew(Class<? extends Activity>... classArray) {
        if (activityStack == null) {
            return;
        }
        Stack<Activity> removeStack = new Stack<>();
        for (Activity activity : activityStack) {
            boolean whiteClazz = false;
            if (classArray != null) {
                for (Class<? extends Activity> clazz : classArray) {
                    if (activity.getClass().equals(clazz)) {
                        whiteClazz = true;
                        break;
                    }
                }
                if (!whiteClazz) {
                    removeStack.add(activity);
                }
            }
        }
        for (Activity activity : removeStack) {
            activityStack.remove(activity);
            activity.finish();
        }
    }

    /**
     * 移除栈中的所有Activity
     */
    public void removeAll() {
        if (activityStack == null) {
            return;
        }
        while (true) {
            Activity top = getTop();
            if (top == null) {
                break;
            }
            Logger.i(TAG, "remove:" + top);
            activityStack.remove(top);
            top.finish();
        }
    }

    public Activity getTopActivity() {
        return topActivity;
    }

    void setTopActivity(Activity topActivity) {
        this.topActivity = topActivity;
    }

    public Activity getResumedActivity() {
        return resumedActivity;
    }

    void setResumedActivity(Activity resumedActivity) {
        this.resumedActivity = resumedActivity;
    }

}
