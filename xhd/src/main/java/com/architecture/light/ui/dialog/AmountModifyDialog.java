package com.architecture.light.ui.dialog;

import android.content.Context;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.architecture.helper.AmountHelper;
import com.android.architecture.helper.AmountInputFilter;
import com.android.architecture.helper.Logger;
import com.android.architecture.ui.page.BaseDialog;
import com.android.architecture.ui.page.action.AnimAction;
import com.architecture.light.R;
import com.architecture.light.ui.view.NumberKeyboardView;

/**
 * File describe:
 * Author: SuQi
 * Create date: 9/15/22
 * Modify date: 9/15/22
 * Version: 1
 */
public class AmountModifyDialog {

    public static final class Builder extends BaseDialog.Builder<Builder> {
        private static final double MAX_AMOUNT = 9999999.99;
        private final ImageView ivMinus;
        private final EditText etAmount;
        private final ImageView ivClose;
        private final TextView tvTip;
        private final NumberKeyboardView keyboard;
        private double originAmount;
        private ClickConfirmListener clickConfirmListener;

        public Builder(Context context) {
            super(context);
            setContentView(R.layout.dialog_amount_modify);
            setAnimStyle(AnimAction.ANIM_BOTTOM);

            ivMinus = findViewById(R.id.iv_minus);
            etAmount = findViewById(R.id.et_amount);
            ivClose = findViewById(R.id.iv_close);
            tvTip = findViewById(R.id.tv_tip);
            keyboard = findViewById(R.id.keyboard);

            InputFilter[] filters = {new AmountInputFilter()};
            etAmount.setFilters(filters);
            keyboard.bindEditText(etAmount);
            keyboard.setOnInputListener(new NumberKeyboardView.OnInputListener() {
                @Override
                public void textChanged() {
                    notifyConfirmEnabled();
                }

                @Override
                public void confirm() {
                    dismiss();
                    String amount = etAmount.getText().toString();
                    double amt = Double.parseDouble(amount);
                    if (originAmount < 0) {
                        amt = -amt;
                    }
                    Logger.d("AmountModifyDialog", "confirm:" + amt);
                    if (clickConfirmListener != null) {
                        clickConfirmListener.onConfirm(getDialog(), amt);
                    }
                }
            });
            ivClose.setOnClickListener(v -> {
                dismiss();
            });
        }

        public Builder setAmount(double amount, double originAmount) {
            this.originAmount = originAmount;
            if (originAmount >= 0) {
                ivMinus.setVisibility(View.GONE);
            } else {
                ivMinus.setVisibility(View.VISIBLE);
            }
            etAmount.setText(AmountHelper.INSTANCE.formatAmountNoSymbols(Math.abs(amount)));
            return this;
        }

        private void notifyConfirmEnabled() {
            String amount = etAmount.getText().toString();
            if (!TextUtils.isEmpty(amount)) {
                double amt = Double.parseDouble(amount);
                if (originAmount >= 0) {
                    if (amt > MAX_AMOUNT) {
                        tvTip.setText("金额输入超过最大金额");
                        keyboard.setConfirmEnabled(false);
                    } else {
                        tvTip.setText("");
                        keyboard.setConfirmEnabled(true);
                    }
                } else {
                    double origin = Math.abs(originAmount);
                    if (amt > origin) {
                        tvTip.setText("不能小于余额");
                        keyboard.setConfirmEnabled(false);
                    } else {
                        tvTip.setText("");
                        keyboard.setConfirmEnabled(true);
                    }
                }
            } else {
                keyboard.setConfirmEnabled(false);
            }
        }

        public Builder setClickConfirmListener(ClickConfirmListener listener) {
            clickConfirmListener = listener;
            return this;
        }
    }

    public interface ClickConfirmListener {

        /**
         * 点击确定时回调
         */
        void onConfirm(BaseDialog dialog, double amount);
    }

}
