package com.sup.itg.itgt.java.animation.behavior.myappbar.bar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.sup.itg.itgt.java.animation.behavior.myappbar.ItgAppBarLayout;
import com.sup.itg.itgt.java.animation.behavior.myappbar.ItgHeaderScrollingViewBehavior;

import java.util.List;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;

public class ScrollingViewBehavior extends ItgHeaderScrollingViewBehavior {

    public ScrollingViewBehavior() {
    }

    public ScrollingViewBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, com.google.android.material.R.styleable.ScrollingViewBehavior_Layout);
        this.setOverlayTop(a.getDimensionPixelSize(com.google.android.material.R.styleable.ScrollingViewBehavior_Layout_behavior_overlapTop, 0));
        a.recycle();
    }

    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof ItgAppBarLayout;
    }

    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        this.offsetChildAsNeeded(child, dependency);
        this.updateLiftedStateIfNeeded(child, dependency);
        return false;
    }

    public boolean onRequestChildRectangleOnScreen(CoordinatorLayout parent, View child, Rect rectangle, boolean immediate) {
        ItgAppBarLayout header = this.findFirstDependency(parent.getDependencies(child));
        if (header != null) {
            rectangle.offset(child.getLeft(), child.getTop());
            Rect parentRect = this.tempRect1;
            parentRect.set(0, 0, parent.getWidth(), parent.getHeight());
            if (!parentRect.contains(rectangle)) {
                header.setExpanded(false, !immediate);
                return true;
            }
        }

        return false;
    }

    private void offsetChildAsNeeded(View child, View dependency) {
        CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) dependency.getLayoutParams()).getBehavior();
        if (behavior instanceof BaseBehavior) {
            BaseBehavior ablBehavior = (BaseBehavior) behavior;
            ViewCompat.offsetTopAndBottom(child, dependency.getBottom() - child.getTop() + ablBehavior.offsetDelta + this.getVerticalLayoutGap() - this.getOverlapPixelsForOffset(dependency));
        }

    }

    float getOverlapRatioForOffset(View header) {
        if (header instanceof ItgAppBarLayout) {
            ItgAppBarLayout abl = (ItgAppBarLayout) header;
            int totalScrollRange = abl.getTotalScrollRange();
            int preScrollDown = abl.getDownNestedPreScrollRange();
            int offset = getAppBarLayoutOffset(abl);
            if (preScrollDown != 0 && totalScrollRange + offset <= preScrollDown) {
                return 0.0F;
            }

            int availScrollRange = totalScrollRange - preScrollDown;
            if (availScrollRange != 0) {
                return 1.0F + (float) offset / (float) availScrollRange;
            }
        }

        return 0.0F;
    }

    private static int getAppBarLayoutOffset(ItgAppBarLayout abl) {
        CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) abl.getLayoutParams()).getBehavior();
        return behavior instanceof BaseBehavior ? ((BaseBehavior) behavior).getTopBottomOffsetForScrollingSibling() : 0;
    }

    @Override
    public ItgAppBarLayout findFirstDependency(List<View> views) {
        int i = 0;

        for (int z = views.size(); i < z; ++i) {
            View view = (View) views.get(i);
            if (view instanceof ItgAppBarLayout) {
                return (ItgAppBarLayout) view;
            }
        }

        return null;
    }

    @Override
    public int getScrollRange(View v) {
        super.getScrollRange(v);
        return v instanceof ItgAppBarLayout ? ((ItgAppBarLayout) v).getTotalScrollRange() : super.getScrollRange(v);
    }

    private void updateLiftedStateIfNeeded(View child, View dependency) {
        if (dependency instanceof ItgAppBarLayout) {
            ItgAppBarLayout appBarLayout = (ItgAppBarLayout) dependency;
            if (appBarLayout.isLiftOnScroll()) {
                appBarLayout.setLiftedState(child.getScrollY() > 0);
            }
        }

    }
}
