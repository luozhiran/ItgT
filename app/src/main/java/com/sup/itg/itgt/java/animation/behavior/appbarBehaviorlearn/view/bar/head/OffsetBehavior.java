package com.sup.itg.itgt.java.animation.behavior.appbarBehaviorlearn.view.bar.head;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.sup.itg.itgt.java.animation.behavior.appbarBehaviorlearn.view.ViewOffsetHelper;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

public class OffsetBehavior<V extends View> extends CoordinatorLayout.Behavior<V> {

    private ViewOffsetHelper viewOffsetHelper;
    private int tempTopBottomOffset = 0;
    private int tempLeftRightOffset = 0;

    public OffsetBehavior() {
    }

    public OffsetBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onLayoutChild(@NonNull CoordinatorLayout parent, @NonNull V child, int layoutDirection) {
        layoutChild(parent, child, layoutDirection);
        return true;
    }

    private void layoutChild(CoordinatorLayout parent, V child, int layoutDirection) {
        parent.onLayoutChild(child, layoutDirection);
        initViewHelper(child);
    }


    private void initViewHelper(V child) {
        if (viewOffsetHelper == null) {
            viewOffsetHelper = new ViewOffsetHelper(child);
        }
        viewOffsetHelper.onViewLayout();

        if (tempLeftRightOffset != 0) {
            viewOffsetHelper.setLeftAndRightOffset(tempLeftRightOffset);
            tempLeftRightOffset = 0;
        }

        if (tempTopBottomOffset != 0) {
            viewOffsetHelper.setTopAndBottomOffset(tempTopBottomOffset);
            tempTopBottomOffset = 0;
        }
    }

    public boolean setTopAndBottomOffset(int offset) {
        if (viewOffsetHelper != null) {
            viewOffsetHelper.setTopAndBottomOffset(offset);
            return true;
        } else {
            tempTopBottomOffset = offset;
            return false;
        }
    }


    public boolean setLeftAndRightOffset(int offset) {
        if (viewOffsetHelper != null) {
            viewOffsetHelper.setLeftAndRightOffset(offset);
            return true;
        } else {
            tempLeftRightOffset = offset;
            return false;
        }
    }


    public int getTopAndBottomOffset() {
        if (viewOffsetHelper != null) {
            int offset = viewOffsetHelper.getTopAndBottomOffset();
            return offset;
        } else {
            return 0;
        }
    }


    public int getLeftAndRightOffset() {
        if (viewOffsetHelper != null) {
            int offset = viewOffsetHelper.getLeftAndRightOffset();
            return offset;
        } else {
            return 0;
        }
    }


}
