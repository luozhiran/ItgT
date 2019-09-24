package com.sup.itg.itgt.touch.dir19;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.Nullable;

import com.sup.itg.base.ItgL;

public class MyTextView extends android.widget.TextView {
    public MyTextView(Context context) {
        super(context);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                ItgL.e("ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                ItgL.e("ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                ItgL.e("ACTION_UP");
                break;
        }
        boolean handle = super.dispatchTouchEvent(ev);

        return handle;
    }
}
