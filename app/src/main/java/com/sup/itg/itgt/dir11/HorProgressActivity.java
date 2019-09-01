package com.sup.itg.itgt.dir11;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.os.Message;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.sup.itg.base.ItgHandlerActivity;
import com.sup.itg.itgt.R;

public class HorProgressActivity extends ItgHandlerActivity {


    private HorProgressView horProgressView;
    @Override
    public int getLayoutId() {
        return R.layout.activity_hor_progress;
    }

    @Override
    public void initView() {

        horProgressView = findViewById(R.id.horprogress);
    }

    @Override
    public void initData() {

        horProgressView.setMaxProgress(100);
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0,100);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int progress = (int) animation.getAnimatedValue();
                horProgressView.setProgress( progress);
            }
        });
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.setDuration(2000);
        valueAnimator.start();
    }

    @Override
    public void handleMessage(Message msg) {

    }
}
