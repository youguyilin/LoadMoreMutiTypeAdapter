package com.example.demo.mutitypeloadmoreadapterdemo.loadmore;

import com.example.demo.mutitypeloadmoreadapterdemo.R;

/**
 * @author ChuYinGen
 *         2018/7/17
 * @Description
 */
public class SimpleLoadMoreView extends LoadMoreView {
    @Override
    public int getLayoutId() {
        return R.layout.simple_view_load_more;
    }

    @Override
    protected int getLoadingViewId() {
        return R.id.load_more_loading_view;
    }

    @Override
    protected int getLoadFailViewId() {
        return R.id.load_more_load_fail_view;
    }

    @Override
    protected int getLoadEndViewId() {
        return R.id.load_more_load_end_view;
    }
}
