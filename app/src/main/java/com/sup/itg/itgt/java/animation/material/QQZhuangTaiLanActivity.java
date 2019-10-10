package com.sup.itg.itgt.java.animation.material;

import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.view.View;

import com.sup.itg.base.ItgHandlerActivity;
import com.sup.itg.base.ItgL;
import com.sup.itg.itgt.R;
import com.sup.itg.itgt.databinding.ActivityCoordinatorLayoutBinding;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

public class QQZhuangTaiLanActivity extends AppCompatActivity {


    private ActivityCoordinatorLayoutBinding mBinding;
    private int mImageHeight;
    private int mTitlaBarHeight;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_coordinator_layout);
        StatusBarUtil.setActivityTransluction(this);
        mBinding.titleBar.getBackground().setAlpha(0);
        mBinding.imgHead.post(new Runnable() {
            @Override
            public void run() {
                mImageHeight = mBinding.imgHead.getHeight();
            }
        });
        mBinding.titleBar.post(new Runnable() {
            @Override
            public void run() {
                mTitlaBarHeight = mBinding.titleBar.getHeight();
            }
        });
        listenerScroll();
    }


    private void listenerScroll() {
        if (Build.VERSION.SDK_INT >= 23) {
            mBinding.scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    changeAlphaByScrollPosition(scrollY);

                }
            });
        } else {
            mBinding.scrollView.setChangeListener(new MyScrollView.ScrollChangeListener() {
                @Override
                public void onScrollChange(int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    changeAlphaByScrollPosition(scrollY);
                }
            });
        }
    }


    private void changeAlphaByScrollPosition(int scrollY) {

        if (mImageHeight == 0) return;
        float alpha = 1.0f*scrollY / (mImageHeight-mTitlaBarHeight);
        if (alpha > 1) {
            alpha = 1;
        }
        ItgL.e(scrollY+"  "+alpha);
        mBinding.titleBar.getBackground().setAlpha((int) (alpha * 255));
    }
}
