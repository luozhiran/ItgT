package com.sup.itg.itgt.java.customView.dir12;

import android.os.Message;

import com.sup.itg.base.ItgHandlerActivity;
import com.sup.itg.itgt.R;

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
