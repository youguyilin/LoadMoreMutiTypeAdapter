package com.example.demo.mutitypeloadmoreadapterdemo;

import android.view.View;
import android.view.ViewGroup;

import com.example.demo.mutitypeloadmoreadapterdemo.adapter.BaseViewHolder;
import com.example.demo.mutitypeloadmoreadapterdemo.adapter.LoadMoreAdapter;

/**
 * @author ChuYinGen
 *         2018/6/ @Description
 */
public class DemoListAdapter extends LoadMoreAdapter<String> {
    private static final int TYPE_CONTENT = 0;
    private static final int TYPE_FOCUS = 1;
    public String header;

    public void setStrings(String strings) {
        header = strings;
    }

    /**
     * 获取添加header后条目数
     * @return
     */
    @Override
    public int getItemCount() {
        int count = super.getItemCount();
        return header == null ? count : count + 1;
    }

    /**
     * 获取指定位置的Item类型
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        if (position == 0 && header != null) {
            return TYPE_FOCUS;
        }
        return super.getItemViewType(getRealPosition(position));
    }

    /**
     * 获取列表 item 类型
     * @param s
     * @return
     */
    @Override
    public int getViewType(String s) {
        return TYPE_CONTENT;
    }

    /**
     * 列表list的真实位置
     * @param position
     * @return
     */
    private int getRealPosition(int position) {
        if (header != null) {
            return position - 1;
        }
        return position;
    }


    /**
     * 装饰者模式，父类抽象方法，用于 创建ViewHolder
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    protected BaseViewHolder buildViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_CONTENT:
                View view = inflate(parent, R.layout.card_list);
                return new DataListViewHolder(view);
            case TYPE_FOCUS:
                View focusView = inflate(parent, R.layout.card_header);
                return new HeaderViewHolder(focusView);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == TYPE_CONTENT){
            String str = mList.get(getRealPosition(position));
            holder.onBind(str);
        }else if (viewType == TYPE_FOCUS){
            holder.onBind(header);
        }else {
            super.onBindViewHolder(holder, position);
        }
    }
}
