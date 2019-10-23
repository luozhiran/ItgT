package com.sup.itg.itgt.java.animation.behavior.myappbar.bar;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;

public class AppBarLayoutParams extends LinearLayout.LayoutParams{

    public static final int SCROLL_FLAG_SCROLL = 1;
    public static final int SCROLL_FLAG_EXIT_UNTIL_COLLAPSED = 2;
    public static final int SCROLL_FLAG_ENTER_ALWAYS = 4;
    public static final int SCROLL_FLAG_ENTER_ALWAYS_COLLAPSED = 8;
    public static final int SCROLL_FLAG_SNAP = 16;
    public static final int SCROLL_FLAG_SNAP_MARGINS = 32;
    static final int FLAG_QUICK_RETURN = 5;
    static final int FLAG_SNAP = 17;
    static final int COLLAPSIBLE_FLAGS = 10;
    public int scrollFlags = 1;
    Interpolator scrollInterpolator;

    public AppBarLayoutParams(Context c, AttributeSet attrs) {
        super(c, attrs);
        TypedArray a = c.obtainStyledAttributes(attrs, com.google.android.material.R.styleable.AppBarLayout_Layout);
        this.scrollFlags = a.getInt(com.google.android.material.R.styleable.AppBarLayout_Layout_layout_scrollFlags, 0);
        if (a.hasValue(com.google.android.material.R.styleable.AppBarLayout_Layout_layout_scrollInterpolator)) {
            int resId = a.getResourceId(com.google.android.material.R.styleable.AppBarLayout_Layout_layout_scrollInterpolator, 0);
            this.scrollInterpolator = android.view.animation.AnimationUtils.loadInterpolator(c, resId);
        }

        a.recycle();
    }

    public AppBarLayoutParams(int width, int height) {
        super(width, height);
    }

    public AppBarLayoutParams(int width, int height, float weight) {
        super(width, height, weight);
    }

    public AppBarLayoutParams(android.view.ViewGroup.LayoutParams p) {
        super(p);
    }

    public AppBarLayoutParams(ViewGroup.MarginLayoutParams source) {
        super(source);
    }

    @RequiresApi(19)
    public AppBarLayoutParams(LinearLayout.LayoutParams source) {
        super(source);
    }

    @RequiresApi(19)
    public AppBarLayoutParams(AppBarLayoutParams source) {
        super(source);
        this.scrollFlags = source.scrollFlags;
        this.scrollInterpolator = source.scrollInterpolator;
    }

    public void setScrollFlags(int flags) {
        this.scrollFlags = flags;
    }

    public int getScrollFlags() {
        return this.scrollFlags;
    }

    public void setScrollInterpolator(Interpolator interpolator) {
        this.scrollInterpolator = interpolator;
    }

    public Interpolator getScrollInterpolator() {
        return this.scrollInterpolator;
    }

    public boolean isCollapsible() {
        return (this.scrollFlags & 1) == 1 && (this.scrollFlags & 10) != 0;
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface ScrollFlags {
    }

}
