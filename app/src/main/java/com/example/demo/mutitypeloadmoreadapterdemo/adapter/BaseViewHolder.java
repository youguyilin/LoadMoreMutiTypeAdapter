package com.example.demo.mutitypeloadmoreadapterdemo.adapter;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

/**
 * @author ChuYinGen
 *         2017/6/14
 * @Description 基础ViewHolder
 */
public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {

    private final SparseArray<View> views;

    public BaseViewHolder(View itemView) {
        super(itemView);
        this.views = new SparseArray<>();
    }

    public Context getContext() {
        return itemView.getContext();
    }

    @Nullable
    public final View findViewById(@IdRes int id) {
        return itemView.findViewById(id);
    }

    public abstract void onBind(T t);

    public void onBind(T t, RecyclerView.Adapter adapter) {
    }

    /**
     * 设置view 是否可见
     * @param viewId
     * @param visible
     * @return
     */
    public BaseViewHolder setGone(@IdRes int viewId, boolean visible) {
        View view = getView(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    public <T extends View> T getView(@IdRes int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }
}
