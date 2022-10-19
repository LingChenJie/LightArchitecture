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
    private static Stack<BaseActivity> activityStack;
    private BaseActivity topActivity;
    private BaseActivity resumedActivity;

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
    public void add(BaseActivity activity) {
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
    public void remove(BaseActivity activity) {
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
                BaseActivity activity = activityStack.lastElement();
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
    public void removeTopUtilSelf(BaseActivity activity) {
        if (activity != null) {
            while (true) {
                BaseActivity top = getTop();
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
    private BaseActivity getTop() {
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
            BaseActivity top = getTop();
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
    public final void removeAllButFew(Class<? extends BaseActivity>... classArray) {
        if (activityStack == null) {
            return;
        }
        Stack<BaseActivity> removeStack = new Stack<>();
        for (BaseActivity activity : activityStack) {
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
        for (BaseActivity activity : removeStack) {
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
            BaseActivity top = getTop();
            if (top == null) {
                break;
            }
            Logger.i(TAG, "remove:" + top);
            activityStack.remove(top);
            top.finish();
        }
    }

    public BaseActivity getTopActivity() {
        return topActivity;
    }

    void setTopActivity(BaseActivity topActivity) {
        this.topActivity = topActivity;
    }

    public BaseActivity getResumedActivity() {
        return resumedActivity;
    }

    void setResumedActivity(BaseActivity resumedActivity) {
        this.resumedActivity = resumedActivity;
    }

}
