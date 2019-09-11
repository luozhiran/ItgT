package com.sup.itg.itgt.dir14;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;

import androidx.core.view.ViewCompat;

import com.sup.itg.base.ItgL;
import com.sup.itg.itgt.R;
import com.sup.itg.itgt.Utils;

public class SlideMenu extends HorizontalScrollView {
    private float mMenuMarginRight = 0;
    private int mMeunWidth;
    private View mContentView,mMenuView;

    public SlideMenu(Context context) {
        this(context, null);
    }

    public SlideMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SlideMenu);
        mMenuMarginRight = ta.getDimension(R.styleable.SlideMenu_iMenuMarginRight, mMenuMarginRight);
        ta.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
       ViewGroup parent = (ViewGroup) getChildAt(0);


        if (parent.getChildCount() != 2) {
            throw new RuntimeException("必须两个子view");
        }
        mMenuView = parent.getChildAt(0);
        ViewGroup.LayoutParams params = mMenuView.getLayoutParams();
        mMeunWidth = (int) (getScreenWidth() - mMenuMarginRight);
        params.width = mMeunWidth;
        params.height = getScreenHeight();
        mMenuView.setLayoutParams(params);

        mContentView= parent.getChildAt(1);
        params = mContentView.getLayoutParams();
        params.width = getScreenWidth();
        params.height = getScreenHeight();
        mContentView.setLayoutParams(params);

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
                int scrollX = getScrollX();
                if (scrollX > mMeunWidth / 2) {
                    //关闭
                    clese();
                } else {
                    //打开
                    open();
                }
                return true;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        scrollTo(mMeunWidth, 0);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        float scale = 1f * l / mMeunWidth;
        float rightScale = 0.7f+0.3f*scale;
        mContentView.setPivotX(0);
        mContentView.setPivotY(getScreenHeight()/2);
        mContentView.setScaleX(rightScale);
        mContentView.setScaleY(rightScale);


        ItgL.e(" "+scale);
        float alpha = 0.7f +(1f -scale)*0.3f;
        mMenuView.setAlpha(alpha);
        float menuFloat = 0.7f+(1f-scale)*0.3f;
        mMenuView.setScaleX(menuFloat);
        mMenuView.setScaleY(menuFloat);


    }

    private int getScreenWidth() {
        WindowManager vm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        vm.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    private int getScreenHeight() {
        WindowManager vm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        vm.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    private void clese() {
        smoothScrollTo(mMeunWidth, 0);
    }

    private void open() {
        smoothScrollTo(0, 0);
    }

}
