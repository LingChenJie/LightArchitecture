package com.android.architecture.ui.toast;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.TypedValue;

import com.android.architecture.R;
import com.hjq.toast.style.BlackToastStyle;

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2021/02/27
 * desc   : Toast 样式配置
 */
public final class ToastStyle extends BlackToastStyle {

    @Override
    protected Drawable getBackgroundDrawable(Context context) {
        GradientDrawable drawable = new GradientDrawable();
        // 设置颜色
        drawable.setColor(0XD9000000);
        // 设置圆角
        drawable.setCornerRadius(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (int) context.getResources().getDimension(R.dimen.dp_4), context.getResources().getDisplayMetrics()));
        return drawable;
    }

    @Override
    protected float getTextSize(Context context) {
        return context.getResources().getDimension(R.dimen.sp_14);
    }

    @Override
    protected int getHorizontalPadding(Context context) {
        return (int) context.getResources().getDimension(R.dimen.sp_24);
    }

    @Override
    protected int getVerticalPadding(Context context) {
        return (int) context.getResources().getDimension(R.dimen.sp_16);
    }
}