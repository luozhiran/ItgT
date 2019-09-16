package com.sup.itg.itgt.mvvm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.sup.itg.itgt.R;
import com.sup.itg.itgt.databinding.ActivityMvvmBinding;
import com.sup.itg.itgt.dir9.TextView;

public class MvvmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_mvvm);
        User user = new User("Lawn","me");
        ActivityMvvmBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_mvvm);
        binding.setUser(user);

    }
}
