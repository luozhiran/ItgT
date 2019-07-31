package com.sup.itg.itgt.dir2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import android.os.Bundle;
import android.view.MotionEvent;

import com.sup.itg.itgt.R;

public class GestureActivity extends AppCompatActivity {

    private GestureDetectorCompat mGestureDetectorCompat;
    private GestureDetectorListener mGestureDetectorListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture);
        mGestureDetectorListener = new GestureDetectorListener();
        mGestureDetectorCompat = new GestureDetectorCompat(this, mGestureDetectorListener);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        return mGestureDetectorCompat.onTouchEvent(event);
    }
}
