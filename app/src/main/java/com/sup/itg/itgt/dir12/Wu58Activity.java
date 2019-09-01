package com.sup.itg.itgt.dir12;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Message;

import com.sup.itg.base.ItgHandlerActivity;
import com.sup.itg.itgt.R;

import java.util.Random;

public class Wu58Activity extends ItgHandlerActivity {



    private Wu58View wu58View;
    @Override
    public int getLayoutId() {
        return R.layout.activity_wu58;
    }

    @Override
    public void initView() {
        wu58View = findViewById(R.id.wu58View);
    }

    @Override
    public void initData() {
        mItgHandler.sendEmptyMessageDelayed(1,1000);

    }

    @Override
    public void handleMessage(Message msg) {
        wu58View.exchange();
        mItgHandler.sendEmptyMessageDelayed(1,1000);

    }
}
