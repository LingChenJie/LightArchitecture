package com.android.architecture.ui.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * File describe:
 * Author: SuQi
 * Create date: 2022/7/28
 * Modify date: 2022/7/28
 * Version: 1
 */
public abstract class BaseAdapter<T, V extends ViewBinding> extends RecyclerView.Adapter<BaseAdapter.BaseHolder<V>> {
    private final List<T> data;
    protected OnItemClickListener<T> listener;

    public BaseAdapter() {
        data = new ArrayList<>();
    }

    public void setData(List<T> data) {
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public void setItemListener(OnItemClickListener<T> listener) {
        this.listener = listener;
    }

    protected List<T> getData() {
        return data;
    }

    @NonNull
    @Override
    public BaseHolder<V> onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new BaseHolder<>(onBindingView(viewGroup));
    }

    @Override
    public void onBindViewHolder(@NonNull BaseHolder<V> holder, int position) {
        onBindingData(holder, data.get(position), position);
    }

    protected abstract void onBindingData(BaseHolder<V> holder, T t, int position);

    protected abstract V onBindingView(ViewGroup viewGroup);

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class BaseHolder<V extends ViewBinding> extends RecyclerView.ViewHolder {
        private final V binding;

        public BaseHolder(V viewBinding) {
            super(viewBinding.getRoot());
            this.binding = viewBinding;
        }

        public V getBinding() {
            return binding;
        }
    }

    public interface OnItemClickListener<T> {
        void onItemClick(int viewId, int position, T t);
    }
}