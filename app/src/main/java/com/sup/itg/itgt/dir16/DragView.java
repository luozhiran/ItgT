package com.sup.itg.itgt.dir16;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.customview.widget.ViewDragHelper;

import com.sup.itg.base.ItgL;

public class DragView extends FrameLayout {

    private ViewDragHelper mViewDragHelper;
    private int mMenuHeight = 0;
    private View mMenuView;
    private View mContentView;

    public DragView(@NonNull Context context) {
        this(context, null);
    }

    public DragView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mMenuView = getChildAt(0);
        mContentView = getChildAt(1);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        getMenuHeight(changed);
    }

    private void getMenuHeight(boolean changed) {
        if (changed) {
            View view = getChildAt(0);
            mMenuHeight = view.getHeight();
        }
    }

    private void initView() {
        mViewDragHelper = ViewDragHelper.create(this, 0.2f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(@NonNull View child, int pointerId) {
                return mContentView == child;
            }

            @Override
            public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
                return child.getLeft();
            }

            @Override
            public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
                if (top < 0) {
                    top = 0;
                } else if (top > mMenuHeight) {
                    top = mMenuHeight;
                }
                return top;
            }

            @Override
            public int getViewHorizontalDragRange(@NonNull View child) {

                return super.getViewHorizontalDragRange(child);
            }

            @Override
            public int getViewVerticalDragRange(@NonNull View child) {
                return super.getViewVerticalDragRange(child);
            }

            @Override
            public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
                super.onViewReleased(releasedChild, xvel, yvel);
                ItgL.e("yvel =" + releasedChild.getTop());
                if (releasedChild.getTop() > mMenuHeight / 2) {
                    mViewDragHelper.settleCapturedViewAt(0, mMenuHeight);
                } else {
                    mViewDragHelper.settleCapturedViewAt(0, 0);
                }
                invalidate();
            }

            @Override
            public void onViewPositionChanged(@NonNull View changedView, int left, int top, int dx, int dy) {
                super.onViewPositionChanged(changedView, left, top, dx, dy);

            }

            @Override
            public void onViewDragStateChanged(int state) {
                super.onViewDragStateChanged(state);
            }

            @Override
            public void onEdgeDragStarted(int edgeFlags, int pointerId) {
                super.onEdgeDragStarted(edgeFlags, pointerId);
            }
        });

    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mViewDragHelper.continueSettling(true)) {
            invalidate();
        }

    }

    private float mDownY = 0;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        super.onInterceptTouchEvent(ev);
        mViewDragHelper.shouldInterceptTouchEvent(ev);
        boolean handler = false;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownY = ev.getY();
                ItgL.e("ACTION_DOWN");
                mViewDragHelper.processTouchEvent(ev);
                break;
            case MotionEvent.ACTION_MOVE:
                if (mDownY > ev.getY()) {
                    return true;
                }
                ItgL.e("ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                ItgL.e("ACTION_UP");
                break;
        }

        return handler;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        mViewDragHelper.processTouchEvent(event);
        return true;
    }
}
