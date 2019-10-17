package com.sup.itg.itgt.java.animation.behavior.appbarBehaviorlearn.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.sup.itg.base.ItgL;
import com.sup.itg.itgt.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;

public class NestedScrollBehavior<V extends View> extends CoordinatorLayout.Behavior<V> {

    private Rect mTempRect1 = new Rect();
    private Rect mTempRect2 = new Rect();
    private int mSolidOffset;

    private int mSlolideMax = 300;

    public NestedScrollBehavior() {
    }

    public NestedScrollBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean onMeasureChild(@NonNull CoordinatorLayout parent, @NonNull V child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed) {
        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
        if (lp.height == CoordinatorLayout.LayoutParams.MATCH_PARENT || lp.height == CoordinatorLayout.LayoutParams.WRAP_CONTENT) {
            View dependencyView = findSpeficDependcy(parent, child, SimpleBarLayout.class);
            if (dependencyView != null) {
                int availableHeight = View.MeasureSpec.getSize(parentHeightMeasureSpec);
                int height = availableHeight - dependencyView.getMeasuredHeight() + getHideBottomHeight(dependencyView);
                int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(height, lp.height == CoordinatorLayout.LayoutParams.MATCH_PARENT ? View.MeasureSpec.EXACTLY : View.MeasureSpec.AT_MOST);
                parent.onMeasureChild(child, parentWidthMeasureSpec, widthUsed, heightMeasureSpec, heightUsed);
                return true;
            }

        }
        return false;
    }

    @Override
    public boolean onLayoutChild(@NonNull CoordinatorLayout parent, @NonNull V child, int layoutDirection) {
        View dependencyView = findSpeficDependcy(parent, child, SimpleBarLayout.class);
        if (dependencyView != null) {
            CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
            Rect available = getBoundRect(mTempRect1, child, parent);
            GravityCompat.apply(resolveGravity(lp.gravity), child.getMeasuredWidth(), child.getMeasuredHeight()
                    , available, mTempRect2, layoutDirection);
            child.layout(mTempRect2.left, mTempRect2.top + getHideBottomHeight(dependencyView), mTempRect2.right, mTempRect2.bottom + getHideBottomHeight(dependencyView));
        } else {
            parent.onLayoutChild(child, layoutDirection);
        }
        return true;
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull V child, @NonNull View dependency) {
        return dependency instanceof SimpleBarLayout;
    }


    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull V child, @NonNull View dependency) {
        super.onDependentViewChanged(parent, child, dependency);
        ItgL.e(" --- "+dependency.getY()+"   "+dependency.getTop());
        return false;
    }


    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        boolean result = (axes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
        return result;
    }

    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
        consumed[1] = 1;
    }
    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
        ViewCompat.offsetTopAndBottom(child,-dyConsumed);

    }




    private Rect getBoundRect(Rect rect, V child, CoordinatorLayout parent) {
        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
        Rect available = mTempRect1;
        int left = parent.getPaddingLeft() + lp.leftMargin;
        int top = parent.getPaddingTop() + lp.topMargin;
        int right = parent.getWidth() - parent.getPaddingRight() - lp.rightMargin;
        int bottom = parent.getHeight() - parent.getPaddingBottom() - lp.bottomMargin;
        available.set(left, top, right, bottom);
        return available;
    }


    private static int resolveGravity(int gravity) {
        return gravity == 0 ? 8388659 : gravity;
    }


    private View findSpeficDependcy(CoordinatorLayout parent, V child, Class<?> clazz) {
        List<View> dependencies = parent.getDependencies(child);
        View view = null;
        for (int i = 0; i < dependencies.size(); i++) {
            view = dependencies.get(i);
            if (view.getClass() == clazz) {
                return view;
            }
        }
        return null;
    }

    public int getHideBottomHeight(View view) {
        return view.getMeasuredHeight();
    }

}
