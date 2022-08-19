package com.android.architecture.ui.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.android.architecture.extension.ResourcesKt;
import com.android.architecture.utils.AppUtils;

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
    private RecyclerView recyclerView;
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
        return new ViewHolder<>(getViewBinding(viewGroup));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder<V> holder, int position) {
        bindViewHolder(holder, data.get(position), position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
        if (this.recyclerView.getLayoutManager() == null) {
            RecyclerView.LayoutManager layoutManager = generateDefaultLayoutManager();
            if (layoutManager != null) {
                this.recyclerView.setLayoutManager(layoutManager);
            }
        }
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        this.recyclerView = null;
    }

    protected abstract V getViewBinding(ViewGroup viewGroup);

    protected abstract void bindViewHolder(ViewHolder<V> holder, T item, int position);

    protected RecyclerView.LayoutManager generateDefaultLayoutManager() {
        return new LinearLayoutManager(ResourcesKt.getContext());
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
        void onItemLongClick(int viewId, int position, T item);
    }

}