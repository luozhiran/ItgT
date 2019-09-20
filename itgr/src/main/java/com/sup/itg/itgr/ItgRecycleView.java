package com.sup.itg.itgr;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Scroller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.customview.widget.ViewDragHelper;
import androidx.recyclerview.widget.RecyclerView;

public class ItgRecycleView extends RecyclerView {

    private Scroller mScroller;
    private int mWidth = 400;
    private View mCurView, mMenuView;
    private float startX, startY;

    public ItgRecycleView(@NonNull Context context) {
        this(context, null);
    }

    public ItgRecycleView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ItgRecycleView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mScroller = new Scroller(context);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        return super.onInterceptTouchEvent(e);
    }


    @Override
    public boolean onTouchEvent(MotionEvent e) {
        boolean handler = false;
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = e.getX();
                startY = e.getY();
                ViewGroup viewGroup = (ViewGroup) findChildViewUnder(startX, startY);
                mMenuView = viewGroup.getChildAt(0);
                mCurView = viewGroup.getChildAt(1);
                mWidth = mMenuView.getWidth();
                break;
            case MotionEvent.ACTION_MOVE:
                move(e);
                break;
            case MotionEvent.ACTION_UP:
                smoothScroll();
                break;
        }
        if (true) {
            return true;
        } else {
            return super.onTouchEvent(e);
        }
    }

    private void move(MotionEvent e) {
        float offset = startX - e.getX();
        if (mCurView != null) {

            if (offset < 0 && mCurView.getScrollX() <= 0) {//不能朝右边滑动

            } else {
                if (mCurView.getScrollX() <= mWidth) {
                    if (mCurView.getScrollX() + offset > mWidth) {
                        offset = mWidth - mCurView.getScrollX();
                        mCurView.scrollBy((int) offset, 0);
                    } else if (mCurView.getScrollX() + offset < 0) {
                        offset = mCurView.getScrollX();
                        mCurView.scrollBy((int) -offset, 0);
                    } else {
                        mCurView.scrollBy((int) offset, 0);
                    }

                }
            }
        }
        startX = e.getX();
    }


    private void smoothScroll() {
        int offsetX = mCurView.getScrollX();
        int startX = offsetX;
        if (offsetX < mWidth / 2) {
            mScroller.startScroll(startX, 0, -offsetX, 0, 200);
        } else {
            mScroller.startScroll(startX, 0, mWidth - offsetX, 0, 200);
        }
        invalidate();
    }


    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            mCurView.scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }

    private boolean canH(float diffX, float diffY) {
        double d = Math.atan2(diffY, diffX);
        double angle = d * 180 / 3.14159;
        if (angle < 30) {
            return true;
        } else {
            return false;
        }
    }
}
