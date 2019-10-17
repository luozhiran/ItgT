package com.sup.itg.itgt.java.animation.behavior.appbarBehaviorlearn.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.sup.itg.base.ItgL;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.math.MathUtils;
import androidx.core.view.ViewCompat;

public class SimpleBehavior<V extends View> extends CoordinatorLayout.Behavior<V> {


    private ViewOffsetHelper viewOffsetHelper;
    private int tempTopBottomOffset = 0;
    private int tempLeftRightOffset = 0;
    private int offsetDelta;

    public SimpleBehavior() {
    }

    public SimpleBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onMeasureChild(@NonNull CoordinatorLayout parent, @NonNull V child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed) {
        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
        if (lp.height == CoordinatorLayout.LayoutParams.WRAP_CONTENT) {
            parent.onMeasureChild(child, parentWidthMeasureSpec, widthUsed, View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), heightUsed);
            return true;
        } else {
            return super.onMeasureChild(parent, child, parentWidthMeasureSpec, widthUsed, parentHeightMeasureSpec, heightUsed);
        }
    }


    @Override
    public boolean onLayoutChild(@NonNull CoordinatorLayout parent, @NonNull V child, int layoutDirection) {
        parent.onLayoutChild(child, layoutDirection);
        if (viewOffsetHelper == null) {
            viewOffsetHelper = new ViewOffsetHelper(child);
        }
        viewOffsetHelper.onViewLayout();
        if (this.tempTopBottomOffset != 0) {
            this.viewOffsetHelper.setTopAndBottomOffset(this.tempTopBottomOffset);
            this.tempTopBottomOffset = 0;
        }

        if (this.tempLeftRightOffset != 0) {
            this.viewOffsetHelper.setLeftAndRightOffset(this.tempLeftRightOffset);
            this.tempLeftRightOffset = 0;
        }
        return true;
    }


    int getTopBottomOffsetForScrollingSibling() {
        return viewOffsetHelper.getTopAndBottomOffset() + this.offsetDelta;
    }
}
