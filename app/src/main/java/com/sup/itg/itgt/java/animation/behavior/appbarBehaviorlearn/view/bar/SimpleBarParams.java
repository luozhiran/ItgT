package com.sup.itg.itgt.java.animation.behavior.appbarBehaviorlearn.view.bar;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;

import com.sup.itg.itgt.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;

/**
 * 设置插值器和滚动flag
 */
public class SimpleBarParams extends LinearLayout.LayoutParams {
    private int scrollFlags = 1;
    private Interpolator scrollInterpolator;

    public SimpleBarParams(Context c, AttributeSet attrs) {
        super(c, attrs);
        TypedArray a = c.obtainStyledAttributes(attrs, R.styleable.SimpleBarLayout_Layout);
        scrollFlags = a.getInt(R.styleable.SimpleBarLayout_Layout_simple_bar_layout_scrollFlags, 0);
        if (a.hasValue(R.styleable.SimpleBarLayout_Layout_bar_layout_scrollInterpolator)) {
            int resId = a.getResourceId(R.styleable.SimpleBarLayout_Layout_bar_layout_scrollInterpolator, 0);
            scrollInterpolator = AnimationUtils.loadInterpolator(c, resId);//插值器通过xml定义,后面可以研究一下插值其如何加载的?
        }
        a.recycle();
    }

    public SimpleBarParams(int width, int height) {
        super(width, height);
    }

    public SimpleBarParams(int width, int height, float weight) {
        super(width, height, weight);
    }

    public SimpleBarParams(ViewGroup.LayoutParams p) {
        super(p);
    }

    public SimpleBarParams(ViewGroup.MarginLayoutParams source) {
        super(source);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public SimpleBarParams(LinearLayout.LayoutParams source) {
        super(source);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public SimpleBarParams(SimpleBarParams source) {
        super(source);
        scrollFlags = source.scrollFlags;
        scrollInterpolator = source.scrollInterpolator;
    }


    public void setScrollInterpolator(Interpolator interpolator) {
        scrollInterpolator = interpolator;
    }

    public Interpolator getScrollInterpolator() {
        return scrollInterpolator;
    }

    public int getScrollFlags(){
        return scrollFlags;
    }

    /**
     * 判断是不是可折叠的
     * scrollFlags 0x1 0x2 0x4 0x8 0x10
     * 0x1 & 1 = 0001 & 0001 =1     0x1 & 10 = 0001 &1010 = 0
     * 0x2 & 1 = 0010 & 0001 =0     0x2 & 10 = 0010 &1010 = 2
     * 0x4 & 1 = 0100 & 0001 =0     0x4 & 10 = 0100 &1010 = 0
     * 0x8 & 1 = 1000 & 0001 =0     0x8 & 10 = 1000 &1010 = 8
     * 0x10 & 1 = 1010 & 0001 =0    0x10 &10 = 1010 &1010 = 10
     * @return 返回一定是false
     */
    public boolean isCollapsible() {
        return (scrollFlags & 1) == 1 && (scrollFlags & 10) != 0;
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface ScrollFlags {
    }
}
