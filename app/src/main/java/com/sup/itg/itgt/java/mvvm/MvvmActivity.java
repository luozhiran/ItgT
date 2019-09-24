package com.sup.itg.itgt.java.mvvm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

import com.sup.itg.itgt.R;
import com.sup.itg.itgt.databinding.ActivityMvvmBinding;

//https://blog.csdn.net/u011897062/article/details/82185027
// https://developer.android.google.cn/topic/libraries/data-binding#java
public class MvvmActivity extends AppCompatActivity {

    private int i = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final User user = new User("Lawn", "me");
        ActivityMvvmBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_mvvm);
        binding.setUser(user);
        binding.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setFirstName(String.format("Lawn %d",i++));
            }
        });

    }
}
