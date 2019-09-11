package com.sup.itg.itgt.dir10;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.os.Message;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.sup.itg.base.ItgHandlerActivity;
import com.sup.itg.itgt.R;

public class ProgressBarActivity extends ItgHandlerActivity {


    private RingProgressBar mRingProgress;
    @Override
    public int getLayoutId() {
        return R.layout.activity_progress_bar;
    }

    @Override
    public void initView() {
        mRingProgress = findViewById(R.id.ringProgress);
    }

    @Override
    public void initData() {

        mRingProgress.setMaxProgress(100);
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0,100);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.setDuration(3000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                mRingProgress.setProgress((int) value);
            }
        });

        valueAnimator.start();
    }

    @Override
    public void handleMessage(Message msg) {

    }
}
