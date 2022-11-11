package com.architecture.light.ui.dialog;

import android.content.Context;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import androidx.annotation.StringRes;

import com.android.architecture.extension.ViewKt;
import com.android.architecture.ui.page.BaseDialog;
import com.android.architecture.ui.widget.view.RegexEditText;
import com.architecture.light.R;

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2019/02/27
 * desc   : 输入对话框
 */
public final class InputDialog {

    public static final class Builder
            extends CommonDialog.Builder<Builder>
            implements BaseDialog.OnShowListener,
            TextView.OnEditorActionListener {

        private final RegexEditText mInputView;

        private ClickConfirmListener mClickConfirmListener;
        private ClickCancelListener mClickCancelListener;

        public Builder(Context context) {
            super(context);
            setCustomView(R.layout.dialog_input);

            mInputView = findViewById(R.id.tv_input_message);
            mInputView.setOnEditorActionListener(this);

            addOnShowListener(this);
            mCancelView.setOnClickListener(v -> {
                autoDismiss();
                if (mClickCancelListener != null) mClickCancelListener.onCancel(getDialog());
            });
            mConfirmView.setOnClickListener(v -> {
                autoDismiss();
                if (mClickConfirmListener != null) {
                    Editable editable = mInputView.getText();
                    mClickConfirmListener.onConfirm(getDialog(), editable != null ? editable.toString() : "");
                }
            });

        }

        public Builder setHint(@StringRes int id) {
            return setHint(getString(id));
        }

        public Builder setHint(CharSequence text) {
            mInputView.setHint(text);
            return this;
        }

        public Builder setContent(@StringRes int id) {
            return setContent(getString(id));
        }

        public Builder setContent(CharSequence text) {
            mInputView.setText(text);
            Editable editable = mInputView.getText();
            if (editable == null) {
                return this;
            }
            int index = editable.length();
            if (index <= 0) {
                return this;
            }
            mInputView.requestFocus();
            mInputView.setSelection(index);
            return this;
        }

        public Builder setInputRegex(String regex) {
            mInputView.setInputRegex(regex);
            return this;
        }

        public Builder setConfirmListener(ClickConfirmListener clickConfirmListener) {
            this.mClickConfirmListener = clickConfirmListener;
            return this;
        }

        public Builder setCancelListener(ClickCancelListener clickCancelListener) {
            this.mClickCancelListener = clickCancelListener;
            return this;
        }

        /**
         * {@link BaseDialog.OnShowListener}
         */
        @Override
        public void onShow(BaseDialog dialog) {
            postDelayed(500, () -> ViewKt.showKeyboard(mInputView));
        }

        /**
         * {@link TextView.OnEditorActionListener}
         */
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                // 模拟点击确认按钮
                mConfirmView.performClick();
                return true;
            }
            return false;
        }
    }

    public interface ClickConfirmListener {

        /**
         * 点击确定时回调
         */
        void onConfirm(BaseDialog dialog, String content);

    }

    public interface ClickCancelListener {

        /**
         * 点击取消时回调
         */
        void onCancel(BaseDialog dialog);
    }
}