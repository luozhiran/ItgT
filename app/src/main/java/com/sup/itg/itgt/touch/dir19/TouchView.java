package com.sup.itg.itgt.touch.dir19;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.sup.itg.base.ItgL;

public class TouchView extends ViewGroup {
    public TouchView(Context context) {
        this(context, null);
    }

    public TouchView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TouchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        int maxHeight = 0;
        int maxWidth = 0;

        for (int i=0;i<childCount;i++){
            final View child = getChildAt(i);
            measureChild(child,widthMeasureSpec,heightMeasureSpec);
            final ViewGroup.LayoutParams lp =  child.getLayoutParams();
            maxWidth = Math.max(maxWidth, child.getMeasuredWidth());
            maxHeight = Math.max(maxHeight, child.getMeasuredHeight());
        }
        setMeasuredDimension(resolveSize(maxWidth, widthMeasureSpec), resolveSize(maxHeight, heightMeasureSpec));


        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            final ViewGroup.LayoutParams lp =  child.getLayoutParams();
            final int childWidthMeasureSpec;
            if (lp.width == LayoutParams.MATCH_PARENT) {
                final int width = getMeasuredWidth();
                childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
            } else {
                childWidthMeasureSpec = getChildMeasureSpec(widthMeasureSpec, 0, lp.width);
            }
            final int childHeightMeasureSpec;
            if (lp.height == LayoutParams.MATCH_PARENT) {
                final int height = getMeasuredHeight();
                childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
            } else {
                childHeightMeasureSpec = getChildMeasureSpec(widthMeasureSpec, 0, lp.height);
            }
            child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();
            l = (getWidth() - childWidth) / 2;
            t = (getHeight() - childHeight) / 2;
            child.layout(l, t, l + childWidth, t + childHeight);
        }

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                ItgL.e("dispatchTouchEvent ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                ItgL.e("dispatchTouchEvent ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                ItgL.e("dispatchTouchEvent ACTION_UP");
                break;
        }
        boolean handle = super.dispatchTouchEvent(ev);

        return handle;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                ItgL.e("onInterceptTouchEvent ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                ItgL.e("onInterceptTouchEvent ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                ItgL.e("onInterceptTouchEvent ACTION_UP");
                break;
        }
        boolean h = super.onInterceptTouchEvent(ev);
        ItgL.e(h+" ");
        return h;
    }
}
