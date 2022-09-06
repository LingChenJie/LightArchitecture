package com.architecture.light.ui.popup;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.android.architecture.extension.ResourcesKt;
import com.android.architecture.ui.adapter.BaseAdapter;
import com.android.architecture.ui.page.BasePopupWindow;
import com.android.architecture.ui.page.action.AnimAction;
import com.architecture.light.databinding.AdapterPopListBinding;
import com.architecture.light.ui.view.ArrowDrawable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2019/10/18
 * desc   : 列表弹窗
 */
public final class ListPopup {

    public static final class Builder
            extends BasePopupWindow.Builder<Builder> {

        private SelectListener mSelectListener;
        private boolean mAutoDismiss = true;

        private final MenuAdapter mAdapter;

        public Builder(Context context) {
            super(context);

            RecyclerView recyclerView = new RecyclerView(context);
            recyclerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            setContentView(recyclerView);

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

            new ArrowDrawable.Builder(context)
                    .setArrowOrientation(Gravity.TOP)
                    .setArrowGravity(Gravity.CENTER)
                    .setShadowSize((int) ResourcesKt.getResources().getDimension(com.android.architecture.R.dimen.dp_10))
                    .setBackgroundColor(0xFFFFFFFF)
                    .apply(recyclerView);
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

        public Builder setList(int... ids) {
            List<String> data = new ArrayList<>(ids.length);
            for (int id : ids) {
                data.add(getString(id));
            }
            return setList(data);
        }

        public Builder setList(String... data) {
            return setList(Arrays.asList(data));
        }

        public Builder setList(List<String> data) {
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

    private static final class MenuAdapter extends BaseAdapter<String, AdapterPopListBinding> {

        @Override
        protected AdapterPopListBinding getViewBinding(ViewGroup viewGroup) {
            return AdapterPopListBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
        }

        @Override
        protected void bindViewHolder(BaseAdapter.ViewHolder<AdapterPopListBinding> holder, String item, int position) {
            AdapterPopListBinding binding = holder.getBinding();
            binding.tvTitle.setText(item);
            binding.layoutItem.setOnClickListener(v -> {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(binding.layoutItem.getId(), position, item);
                }
            });
        }

    }

    public interface SelectListener {

        /**
         * 选择条目时回调
         */
        void onSelected(BasePopupWindow popupWindow, int position, String item);
    }
}