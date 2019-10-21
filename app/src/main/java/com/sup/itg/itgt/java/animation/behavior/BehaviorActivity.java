package com.sup.itg.itgt.java.animation.behavior;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.sup.itg.itgt.R;
import com.sup.itg.itgt.databinding.ActivityBehaviorBinding;

public class BehaviorActivity extends AppCompatActivity {
    private ActivityBehaviorBinding mDataBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//         DataBindingUtil.setContentView(this, R.layout.activity_behavior_custom);
//        DataBindingUtil.setContentView(this, R.layout.activity_behavior);
//
//        DataBindingUtil.setContentView(this, R.layout.activity_behavior_spefic);
//        DataBindingUtil.setContentView(this, R.layout.activity_behavior_spefic1);

        DataBindingUtil.setContentView(this, R.layout.activity_behavior_my);

    }
}
