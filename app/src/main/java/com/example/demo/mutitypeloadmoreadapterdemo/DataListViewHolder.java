package com.example.demo.mutitypeloadmoreadapterdemo;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demo.mutitypeloadmoreadapterdemo.adapter.BaseViewHolder;

/**
 * @author ChuYinGen
 *         2018/6/26
 * @Description 列表资源 viewholder
 */
public class DataListViewHolder extends BaseViewHolder<String> {
    private TextView mTextView;

    public DataListViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "item", Toast.LENGTH_SHORT).show();
            }
        });
        initView();
    }

    private void initView() {
        mTextView = (TextView) findViewById(R.id.tv_txt);
    }

    @Override
    public void onBind(String s) {
       mTextView.setText(s);
    }
}
