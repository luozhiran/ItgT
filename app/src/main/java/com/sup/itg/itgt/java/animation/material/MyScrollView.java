package com.sup.itg.itgt.java.animation.material;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

import androidx.core.widget.NestedScrollView;

public class MyScrollView extends NestedScrollView {

    private ScrollChangeListener changeListener;

    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (changeListener != null) {
            changeListener.onScrollChange( l, t, oldl, oldt);
        }
    }


    public void setChangeListener(ScrollChangeListener changeListener) {
        this.changeListener = changeListener;
    }

    public interface ScrollChangeListener {
        void onScrollChange(int scrollX, int scrollY, int oldScrollX, int oldScrollY);
    }
}
