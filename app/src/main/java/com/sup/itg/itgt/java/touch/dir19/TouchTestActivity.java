package com.sup.itg.itgt.java.touch.dir19;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;

import com.sup.itg.base.ItgL;
import com.sup.itg.itgt.R;

public class TouchTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                ItgL.e("TouchTestActivity ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                ItgL.e("TouchTestActivity ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                ItgL.e("TouchTestActivity ACTION_UP");
                break;
        }
        return super.dispatchTouchEvent(ev);
    }




}
