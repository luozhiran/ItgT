package com.sup.itg.itgt.java.customView.dir17;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Message;

import com.sup.itg.base.ItgHandlerActivity;
import com.sup.itg.itgt.R;

import java.util.ArrayList;
import java.util.List;

public class TestItgrActivity extends ItgHandlerActivity {


    private RecyclerView mRecyclerview;
    private ItgAdapter mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_test_itgr;
    }

    @Override
    public void initView() {
        mRecyclerview = findViewById(R.id.recyclerview);
        mAdapter = new ItgAdapter();
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerview.setAdapter(mAdapter);
    }

    @Override
    public void initData() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("itgr " + i);
        }
        mAdapter.addData(list);
    }

    @Override
    public void handleMessage(Message msg) {

    }
}
