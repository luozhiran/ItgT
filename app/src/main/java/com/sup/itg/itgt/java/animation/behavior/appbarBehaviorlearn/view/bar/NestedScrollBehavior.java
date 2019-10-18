package com.sup.itg.itgt.java.animation.behavior.appbarBehaviorlearn.view.bar;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import com.sup.itg.base.ItgL;
import com.sup.itg.itgt.R;
import com.sup.itg.itgt.java.animation.behavior.appbarBehaviorlearn.view.SimpleBarLayout;
import com.sup.itg.itgt.java.animation.behavior.appbarBehaviorlearn.view.bar.head.HeaderScrollingViewBehavior;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

public class NestedScrollBehavior extends HeaderScrollingViewBehavior {


    public NestedScrollBehavior() {
    }

    public NestedScrollBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.NestedScrollBehavior_Layout);
        int overlapTop = a.getDimensionPixelSize(R.styleable.NestedScrollBehavior_Layout_nsb_behavior_overlapTop, 0);
        setOverlayTop(overlapTop);
        a.recycle();
    }


    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        return dependency instanceof SimpleBarLayout;
    }


    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        super.onDependentViewChanged(parent, child, dependency);
        ItgL.e(" --- " + dependency.getY() + "   " + dependency.getTop());
        return false;
    }



    @Override
    public View findSpecificDependentView(List<View> views) {
        int count = views.size();
        for (int i = 0; i < count; i++) {
            View view = views.get(i);
            if (view instanceof SimpleBarLayout) {
                return view;
            }
        }
        return null;
    }
}
