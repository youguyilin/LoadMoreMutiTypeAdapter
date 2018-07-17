package com.example.demo.mutitypeloadmoreadapterdemo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author ChuYinGen
 *         2017/6/14
 * @Description 多类型适配器
 */
public abstract class MultiTypeAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {
    protected List<T> mList = new ArrayList<>();

    public MultiTypeAdapter() {
    }

    /**
     * 初始化list
     * @param list
     */
    public void setList(List<T> list) {
        if (list == null || list.isEmpty()) {
            if(mList!=null && !mList.isEmpty()){
                mList.clear();
                notifyDataSetChanged();
            }
            return;
        }
        mList.addAll(list);
        notifyDataSetChanged();
    }

    /**
     * 加载更多list添加数据
     * @param list
     */
    public void addList(List<T> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        if (mList == null) {
            setList(list);
            return;
        }
        mList.addAll(list);
        notifyDataSetChanged();
    }

    /**
     * 删除
     *
     * @param position
     */
    public void removeItem(int position) {
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
        mList.remove(position);
    }

    /**
     * 删除
     *
     * @param position
     */
    public void removeItemData(int position) {
        mList.remove(position);
        notifyDataSetChanged();
    }

    /**
     * 交换
     *
     * @param viewHolder
     * @param target
     */
    public void swap(RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        int fromPosition = viewHolder.getAdapterPosition();
        int toPosition = target.getAdapterPosition();

        Collections.swap(mList, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
    }

    protected View inflate(ViewGroup parent, int resource) {
        return LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        T t = getItem(position);
        holder.onBind(t);
        holder.onBind(t, this);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public T getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        return getViewType(getItem(position));
    }

    public abstract int getViewType(T t);

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public boolean isEmpty() {
        return mList == null || mList.isEmpty();
    }
}
