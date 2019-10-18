package com.sup.itg.itgt.java.animation.behavior.appbarBehaviorlearn.view.bar.head;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.sup.itg.itgt.java.animation.behavior.appbarBehaviorlearn.view.SimpleBarLayout;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

@SuppressLint("RestrictedApi")
public abstract class HeaderScrollingViewBehavior extends CoordinatorLayout.Behavior<View> {

    private Rect mTempRect1 = new Rect();
    private Rect mTempRect2 = new Rect();
    private int overlayTop;
    private int verticalLayoutGap = 0;


    public HeaderScrollingViewBehavior() {
    }

    public HeaderScrollingViewBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onMeasureChild(@NonNull CoordinatorLayout parent, @NonNull View child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed) {
        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
        if (lp.height == CoordinatorLayout.LayoutParams.MATCH_PARENT || lp.height == CoordinatorLayout.LayoutParams.WRAP_CONTENT) {
            List<View> dependencies = parent.getDependencies(child);
            View dependencyView = findSpecificDependentView(dependencies);
            if (dependencyView != null) {
                if (ViewCompat.getFitsSystemWindows(dependencyView) && !ViewCompat.getFitsSystemWindows(child)) {
                    ViewCompat.setFitsSystemWindows(child, true);
                    if (ViewCompat.getFitsSystemWindows(child)) {
                        child.requestLayout();
                        return true;
                    }
                }

                int availableHeight = View.MeasureSpec.getSize(parentHeightMeasureSpec);
                if (availableHeight == 0) {
                    availableHeight = parent.getHeight();
                }
                int height = availableHeight - dependencyView.getMeasuredHeight() + getScrollRange(dependencyView);
                int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(height, lp.height == CoordinatorLayout.LayoutParams.MATCH_PARENT ? View.MeasureSpec.EXACTLY : View.MeasureSpec.AT_MOST);
                parent.onMeasureChild(child, parentWidthMeasureSpec, widthUsed, heightMeasureSpec, heightUsed);
                return true;
            }

        }
        return false;
    }

    @Override
    public boolean onLayoutChild(@NonNull CoordinatorLayout parent, @NonNull View child, int layoutDirection) {
        List<View> dependencies = parent.getDependencies(child);
        View dependencyView = findSpecificDependentView(dependencies);
        if (dependencyView != null) {
            CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) child.getLayoutParams();

            Rect available = mTempRect1;
            int left = parent.getPaddingLeft() + lp.leftMargin;
            int top = parent.getPaddingTop() + lp.topMargin;
            int right = parent.getWidth() - parent.getPaddingRight() - lp.rightMargin;
            int bottom = parent.getHeight() - parent.getPaddingBottom() - lp.bottomMargin;
            available.set(left, top, right, bottom);
            WindowInsetsCompat parentInsets = parent.getLastWindowInsets();
            if (parentInsets != null && ViewCompat.getFitsSystemWindows(parent) && !ViewCompat.getFitsSystemWindows(child)) {
                available.left += parentInsets.getSystemWindowInsetLeft();
                available.right -= parentInsets.getStableInsetRight();
            }
            Rect out = mTempRect2;
            GravityCompat.apply(resolveGravity(lp.gravity), child.getMeasuredWidth(), child.getMeasuredHeight(), available, out, layoutDirection);
            child.layout(out.left, out.top - overlayTop, out.right, out.bottom - overlayTop);
            verticalLayoutGap = out.top - dependencyView.getBottom();
        } else {
            parent.onLayoutChild(child, layoutDirection);
            verticalLayoutGap = 0;

        }
        return true;
    }

    private static int resolveGravity(int gravity) {
        return gravity == 0 ? 8388659 : gravity;
    }


    public abstract View findSpecificDependentView(List<View> views);

    public int getScrollRange(View v) {
        return v.getMeasuredHeight();
    }

    public final void setOverlayTop(int overlayTop) {
        this.overlayTop = overlayTop;
    }

    public final int getOverlayTop() {
        return overlayTop;
    }
}
