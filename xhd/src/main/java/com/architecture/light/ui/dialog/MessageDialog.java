package com.architecture.light.ui.dialog;

import android.content.Context;
import android.widget.TextView;

import androidx.annotation.StringRes;

import com.android.architecture.ui.page.BaseDialog;
import com.architecture.light.R;

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2018/12/2
 * desc   : 消息对话框
 */
public final class MessageDialog {

    public static final class Builder
            extends CommonDialog.Builder<Builder> {

        private final TextView mMessageView;

        private ClickConfirmListener mClickConfirmListener;
        private ClickCancelListener mClickCancelListener;

        public Builder(Context context) {
            super(context);
            setCustomView(R.layout.dialog_message);
            mMessageView = findViewById(R.id.tv_message_message);
            mCancelView.setOnClickListener(v -> {
                autoDismiss();
                if (mClickCancelListener != null) mClickCancelListener.onCancel(getDialog());
            });
            mConfirmView.setOnClickListener(v -> {
                autoDismiss();
                if (mClickConfirmListener != null) mClickConfirmListener.onConfirm(getDialog());
            });
        }

        public Builder setMessage(@StringRes int id) {
            return setMessage(getString(id));
        }

        public Builder setMessage(CharSequence text) {
            mMessageView.setText(text);
            return this;
        }

        @Override
        public BaseDialog create() {
            // 如果内容为空就抛出异常
            if ("".equals(mMessageView.getText().toString())) {
                throw new IllegalArgumentException("Dialog message not null");
            }
            return super.create();
        }

        public Builder setConfirmListener(ClickConfirmListener clickConfirmListener) {
            this.mClickConfirmListener = clickConfirmListener;
            return this;
        }

        public Builder setCancelListener(ClickCancelListener clickCancelListener) {
            this.mClickCancelListener = clickCancelListener;
            return this;
        }

    }

    public interface ClickConfirmListener {

        /**
         * 点击确定时回调
         */
        void onConfirm(BaseDialog dialog);

    }

    public interface ClickCancelListener {

        /**
         * 点击取消时回调
         */
        void onCancel(BaseDialog dialog);
    }

}