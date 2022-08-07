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
public abstract class BaseAdapter<T, V extends ViewBinding> extends RecyclerView.Adapter<BaseAdapter.ViewHolder<V>> {
    private final List<T> data;
    protected OnItemClickListener<T> listener;
    protected OnItemLongClickListener<T> longListener;

    public BaseAdapter() {
        data = new ArrayList<>();
    }

    public void setData(List<T> data) {
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public void addData(List<T> data) {
        this.data.addAll(data);
        notifyItemRangeInserted(this.data.size() - data.size(), data.size());
    }

    public void setItemClickListener(OnItemClickListener<T> listener) {
        this.listener = listener;
    }

    public void setItemLongClickListener(OnItemLongClickListener<T> listener) {
        this.longListener = listener;
    }

    protected List<T> getData() {
        return data;
    }

    @NonNull
    @Override
    public ViewHolder<V> onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder<>(onBindingView(viewGroup));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder<V> holder, int position) {
        onBindingData(holder, data.get(position), position);
    }

    protected abstract V onBindingView(ViewGroup viewGroup);

    protected abstract void onBindingData(ViewHolder<V> holder, T data, int position);

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder<V extends ViewBinding> extends RecyclerView.ViewHolder {
        private final V binding;

        public ViewHolder(V viewBinding) {
            super(viewBinding.getRoot());
            this.binding = viewBinding;
        }

        public V getBinding() {
            return binding;
        }
    }

    public interface OnItemClickListener<T> {
        void onItemClick(int viewId, int position, T item);
    }

    public interface OnItemLongClickListener<T> {
        void onItemClick(int viewId, int position, T item);
    }

}