package com.sup.itg.itgt.java.customView.bethel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.sup.itg.itgt.R;
import com.sup.itg.itgt.databinding.ActivityBaseLinkBinding;
import com.sup.itg.itgt.databinding.ActivityBethelBinding;

public class BethelActivity extends AppCompatActivity {

    private ActivityBethelBinding mDataBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataBinding = DataBindingUtil.setContentView(this,R.layout.activity_bethel);
    }
}
