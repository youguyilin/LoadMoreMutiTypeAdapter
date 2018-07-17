package com.example.demo.mutitypeloadmoreadapterdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.demo.mutitypeloadmoreadapterdemo.adapter.LoadMoreAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private DemoListAdapter mDemoListAdapter;
    private List<String> mStringList = new ArrayList<>();
    int count  = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mRecyclerView.setAdapter(mDemoListAdapter = new DemoListAdapter());
        for (int i = 0; i < 3; i++){
            mStringList.add("test" + i);
        }
        mDemoListAdapter.setStrings(getString(R.string.test));
        mDemoListAdapter.setList(mStringList);
        mDemoListAdapter.setLoadMoreListener(new LoadMoreAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                count++;
                if (count < 5) {
                    mRecyclerView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mDemoListAdapter.addList(mStringList);
                        }
                    }, 1000);
                }else {
                    mDemoListAdapter.showLast();
                }
            }
        },mRecyclerView);
        mDemoListAdapter.disableLoadMoreIfNotFullScreen();
    }
}
