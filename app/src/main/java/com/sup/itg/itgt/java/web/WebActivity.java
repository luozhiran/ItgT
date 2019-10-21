package com.sup.itg.itgt.java.web;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.sup.itg.itgt.R;
import com.sup.itg.itgt.databinding.ActivityWebBinding;

public class WebActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityWebBinding activityWebBinding = DataBindingUtil.setContentView(this,R.layout.activity_web);
        activityWebBinding.webView.loadUrl("file:///android_asset/about_us.html");
    }
}
