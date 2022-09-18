package com.architecture.light.ui.popup;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.android.architecture.extension.ResourcesKt;
import com.android.architecture.ui.adapter.BaseAdapter;
import com.android.architecture.ui.page.BasePopupWindow;
import com.android.architecture.ui.page.action.AnimAction;
import com.android.architecture.utils.DisplayUtils;
import com.android.architecture.utils.ScreenUtils;
import com.architecture.light.R;
import com.architecture.light.data.remote.bean.LoginResponse;
import com.architecture.light.databinding.AdapterPopListBankAccountBinding;
import com.architecture.light.databinding.AdapterPopListBinding;
import com.architecture.light.ui.view.ArrowDrawable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class BankAccountListPopup {

    public static final class Builder
            extends BasePopupWindow.Builder<Builder> {

        private SelectListener mSelectListener;
        private boolean mAutoDismiss = true;

        private final MenuAdapter mAdapter;

        public Builder(Context context) {
            super(context);

            View view = View.inflate(context, R.layout.pop_bank_account, null);
            setContentView(view);
            setWidth(ScreenUtils.getScreenWidth() - DisplayUtils.dp2px(120));

            RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
            mAdapter = new MenuAdapter();
            recyclerView.setAdapter(mAdapter);
            mAdapter.setItemClickListener((viewId, position, item) -> {
                if (mAutoDismiss) {
                    dismiss();
                }
                if (mSelectListener != null) {
                    mSelectListener.onSelected(getPopupWindow(), position, item);
                }
            });

        }

        @Override
        public Builder setGravity(int gravity) {
            switch (gravity) {
                // 如果这个是在中间显示的
                case Gravity.CENTER:
                case Gravity.CENTER_VERTICAL:
                    // 重新设置动画
                    setAnimStyle(AnimAction.ANIM_SCALE);
                    break;
                default:
                    break;
            }
            return super.setGravity(gravity);
        }

        public Builder setList(List<LoginResponse.Data.Project.Bank> data) {
            mAdapter.setData(data);
            return this;
        }

        public Builder setAutoDismiss(boolean dismiss) {
            mAutoDismiss = dismiss;
            return this;
        }

        public Builder setSelectListener(SelectListener listener) {
            mSelectListener = listener;
            return this;
        }

    }

    private static final class MenuAdapter extends BaseAdapter<LoginResponse.Data.Project.Bank, AdapterPopListBankAccountBinding> {

        @Override
        protected AdapterPopListBankAccountBinding getViewBinding(ViewGroup viewGroup) {
            return AdapterPopListBankAccountBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
        }

        @Override
        protected void bindViewHolder(ViewHolder<AdapterPopListBankAccountBinding> holder, LoginResponse.Data.Project.Bank item, int position) {
            AdapterPopListBankAccountBinding binding = holder.getBinding();
            binding.tvBankName.setText(item.getBankName());
            binding.tvBankAccount.setText(item.getBankAccount());
            binding.layoutBankAccount.setOnClickListener(v -> {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(binding.layoutBankAccount.getId(), position, item);
                }
            });
        }

    }

    public interface SelectListener {

        /**
         * 选择条目时回调
         */
        void onSelected(BasePopupWindow popupWindow, int position, LoginResponse.Data.Project.Bank item);
    }
}