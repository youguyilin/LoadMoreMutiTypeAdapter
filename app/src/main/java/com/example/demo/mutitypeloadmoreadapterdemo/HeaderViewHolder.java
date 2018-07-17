package com.example.demo.mutitypeloadmoreadapterdemo;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demo.mutitypeloadmoreadapterdemo.adapter.BaseViewHolder;

/**
 * @author ChuYinGen
 *         2018/6/26
 * @Description
 */
public class HeaderViewHolder extends BaseViewHolder<String> {
    private ImageView mImageView;
    private TextView header;

    public HeaderViewHolder(View itemView) {
        super(itemView);
        initView();
    }

    private void initView() {
        mImageView = (ImageView) findViewById(R.id.iv);
        header = (TextView) findViewById(R.id.header);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Header", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBind(String o) {
        if (o == null){
            return;
        }
        header.setText(o);
    }
}
