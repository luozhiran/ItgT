package com.sup.itg.itgt.java.animation.behavior.appbarBehaviorlearn.view.bar.head;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewConfiguration;

import com.sup.itg.itgt.java.animation.behavior.appbarBehaviorlearn.view.SimpleBarLayout;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.math.MathUtils;

public class HeaderSimpleBehavior<V extends View> extends OffsetBehavior<V> {

    private int touchSlop = -1;

    public HeaderSimpleBehavior() {
    }

    public HeaderSimpleBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }



    public int setHeaderTopBottomOffset(CoordinatorLayout parent, SimpleBarLayout header, int newOffset) {

        int offset = setHeaderTopBottomOffset(parent, header, newOffset, Integer.MIN_VALUE, Integer.MAX_VALUE);
        return offset;
    }

    public int setHeaderTopBottomOffset(CoordinatorLayout parent, SimpleBarLayout header, int newOffset, int minOffset, int maxOffset) {

        int curOffset = getTopAndBottomOffset();
        int consumed = 0;
        if (minOffset != 0 && curOffset >= minOffset && curOffset <= maxOffset) {
            newOffset = MathUtils.clamp(newOffset, minOffset, maxOffset);
            if (curOffset != newOffset) {
                setTopAndBottomOffset(newOffset);
                consumed = curOffset - newOffset;
            }
        }
        return consumed;
    }

}
