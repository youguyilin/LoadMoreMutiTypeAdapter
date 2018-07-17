package com.example.demo.mutitypeloadmoreadapterdemo.adapter;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.demo.mutitypeloadmoreadapterdemo.R;

/**
 * @author ChuYinGen
 *         2017/6/14
 * @Description 加载更多 ViewHolder
 */
public class LoadMoreViewHolder extends BaseViewHolder {
    private ProgressBar mProgressBar;
    private TextView mInfo;

    private LoadMoreAdapter mAdapter;

    public LoadMoreViewHolder(View itemView) {
        super(itemView);

        mProgressBar = (ProgressBar) findViewById(R.id.pb_load);
        mInfo = (TextView) findViewById(R.id.tv_info);
    }

    @Override
    public void onBind(Object o) {
    }

    public void showLoad() {
        mProgressBar.setVisibility(View.VISIBLE);
        mInfo.setText(R.string.tip_status_loading);
    }

    public void showLast() {
        mProgressBar.setVisibility(View.GONE);
        mInfo.setText(R.string.tip_status_last);
        itemView.setClickable(false);
    }

    public void showError(View.OnClickListener listener) {
        mProgressBar.setVisibility(View.GONE);
        mInfo.setText(R.string.tip_status_error);
        itemView.setClickable(true);
        itemView.setOnClickListener(listener);
    }
}
