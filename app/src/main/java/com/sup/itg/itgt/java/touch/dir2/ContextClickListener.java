package com.sup.itg.itgt.java.touch.dir2;

import android.os.Build;
import android.view.GestureDetector;
import android.view.MotionEvent;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.M)
public class ContextClickListener implements GestureDetector.OnContextClickListener {
    @Override
    public boolean onContextClick(MotionEvent e) {
        return false;
    }
}
