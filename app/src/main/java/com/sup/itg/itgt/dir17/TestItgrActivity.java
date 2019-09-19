package com.sup.itg.itgt.dir17;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Message;

import com.sup.itg.base.ItgHandlerActivity;
import com.sup.itg.itgt.R;

public class TestItgrActivity extends ItgHandlerActivity {


    private RecyclerView mRecyclerview;

    @Override
    public int getLayoutId() {
        return R.layout.activity_test_itgr;
    }

    @Override
    public void initView() {

        mRecyclerview = findViewById(R.id.recyclerview);
    }

    @Override
    public void initData() {

    }

    @Override
    public void handleMessage(Message msg) {

    }
}
