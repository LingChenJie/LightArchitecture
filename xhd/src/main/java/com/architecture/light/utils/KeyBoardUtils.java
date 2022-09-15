package com.architecture.light.utils;

import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * Created by SuQi on 2022/9/8.
 * Describe:
 */
public class KeyBoardUtils {

    /**
     * 设置键盘不遮挡按钮
     *
     * @param main   上层布局
     * @param scroll 需要显示的最下方View
     */
    public static void addLayoutListener(final View main, final View scroll) {
        main.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                main.getWindowVisibleDisplayFrame(rect);
                int mainInvisibleHeight = main.getRootView().getHeight() - rect.bottom;
                if (mainInvisibleHeight > 100) {
                    int[] location = new int[2];
                    scroll.getLocationInWindow(location);
                    int scrollHeight = (location[1] + scroll.getHeight()) - rect.bottom;
                    if (isKeyboardShown(main)) {
                        main.scrollTo(0, scrollHeight + 100);
                    } else {
                        main.scrollTo(0, 0);
                    }
                } else {
                    main.scrollTo(0, 0);
                }
            }
        });
    }

    /**
     * 判断软键盘是否弹起
     */
    public static boolean isKeyboardShown(View rootView) {
        try {
            final int softKeyboardHeight = 120;
            Rect r = new Rect();
            rootView.getWindowVisibleDisplayFrame(r);
            DisplayMetrics dm = rootView.getResources().getDisplayMetrics();
            int heightDiff = rootView.getBottom() - r.bottom;
            return heightDiff > softKeyboardHeight * dm.density;
        } catch (Exception e) {
            return false;
        }
    }
}
