package com.sup.itg.itgt.java.animation.behavior.appbarBehaviorlearn.view.bar;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Interpolator;

import com.google.android.material.animation.AnimationUtils;
import com.sup.itg.base.ItgL;
import com.sup.itg.itgt.java.animation.behavior.appbarBehaviorlearn.view.SimpleBarLayout;
import com.sup.itg.itgt.java.animation.behavior.appbarBehaviorlearn.view.ViewOffsetHelper;
import com.sup.itg.itgt.java.animation.behavior.appbarBehaviorlearn.view.bar.head.HeaderSimpleBehavior;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.math.MathUtils;
import androidx.core.view.NestedScrollingChild;
import androidx.core.view.ViewCompat;

public class SimpleBehavior extends HeaderSimpleBehavior<SimpleBarLayout> {


    private ViewOffsetHelper viewOffsetHelper;
    private int tempTopBottomOffset = 0;
    private int tempLeftRightOffset = 0;
    private int offsetDelta;
    private int offsetToChildIndexOnLayout = -1;
    private boolean offsetToChildIndexOnLayoutIsMinHeight;
    private float offsetToChildIndexOnLayoutPerc;
    private ValueAnimator offsetAnimator;

    public SimpleBehavior() {
    }

    public SimpleBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onMeasureChild(@NonNull CoordinatorLayout parent, @NonNull SimpleBarLayout child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed) {
        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
        if (lp.height == CoordinatorLayout.LayoutParams.WRAP_CONTENT) {
            parent.onMeasureChild(child, parentWidthMeasureSpec, widthUsed, View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), heightUsed);
            return true;
        } else {
            return super.onMeasureChild(parent, child, parentWidthMeasureSpec, widthUsed, parentHeightMeasureSpec, heightUsed);
        }
    }


    /**
     * pendingAction 1 2 5 13
     * 1&8 = 0001 & 1000 = 0000 = 0
     * 2&8 = 0010 & 1000 = 0000 = 0
     * 5&8 = 0101 & 1000 = 0000 = 0
     * 13&8 =1101 &1000 =1000 = 8
     *
     * @param parent
     * @param child
     * @param layoutDirection
     * @return
     */
    @Override
    public boolean onLayoutChild(@NonNull CoordinatorLayout parent, @NonNull SimpleBarLayout child, int layoutDirection) {
        boolean handled = super.onLayoutChild(parent, child, layoutDirection);
        int pendingAction = child.getPendingAction();
        int offset;
        if (offsetToChildIndexOnLayout >= 0 && (pendingAction & 8) == 0) {
            View childOfSimpleBarLayout = child.getChildAt(offsetToChildIndexOnLayout);
            offset = -childOfSimpleBarLayout.getBottom();
            if (offsetToChildIndexOnLayoutIsMinHeight) {
                offset = offset + ViewCompat.getMinimumHeight(childOfSimpleBarLayout) + child.getTopInset();
            } else {
                offset = offset + Math.round(1.0f * child.getHeight() * offsetToChildIndexOnLayoutPerc);
            }
            setHeaderTopBottomOffset(parent, child, offset);
        } else if (pendingAction != 0) {
            boolean animate = (pendingAction & 4) != 0;
            if ((pendingAction & 2) != 0) {
                offset = -child.getUpNestedPreScrollRange();
                if (animate) {
                    animateOffsetTo(parent, child, offset, 0.0f);
                } else {
                    setHeaderTopBottomOffset(parent, child, offset);
                }
            } else if ((pendingAction & 1) != 0) {
                if (animate) {
                    animateOffsetTo(parent, child, 0, 0f);
                } else {
                    setHeaderTopBottomOffset(parent, child, 0);
                }
            }
        }

        child.resetPendingAction();
        offsetToChildIndexOnLayout = -1;
        setTopAndBottomOffset(MathUtils.clamp(getTopAndBottomOffset(), -child.getTotalScrollRange(), 0));
        updateSimpleBarLayoutDrawableState(parent, child, getTopAndBottomOffset(), 0, true);
        child.dispatchOffsetUpdaates(getTopAndBottomOffset());
        return handled;
    }


    private void animateOffsetTo(CoordinatorLayout parent, SimpleBarLayout child, int offset, float velocity) {
        int distance = Math.abs(getTopBottomOffsetForScrollingSibling() - offset);
        velocity = Math.abs(velocity);
        int duration;
        if (velocity > 0.0f) {
            duration = 3 * Math.round(1000f * (1.0f * distance / velocity));
        } else {
            float distanceRatio = 1.0f * distance / child.getHeight();
            duration = (int) ((distanceRatio + 1.0f) * 150f);
        }

        animateOffsetWithDuration(parent, child, offset, duration);
    }

    private void animateOffsetWithDuration(final CoordinatorLayout parent, final SimpleBarLayout child, int offset, int duration) {
        int currentOffset = getTopBottomOffsetForScrollingSibling();
        if (currentOffset == offset) {
            if (offsetAnimator != null && offsetAnimator.isRunning()) {
                offsetAnimator.cancel();
            }
        } else {
            if (offsetAnimator == null) {
                offsetAnimator = new ValueAnimator();
                offsetAnimator.setInterpolator(AnimationUtils.DECELERATE_INTERPOLATOR);
                offsetAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        setHeaderTopBottomOffset(parent, child, (Integer) animation.getAnimatedValue());
                    }
                });
            } else {
                offsetAnimator.cancel();
            }
            offsetAnimator.setDuration(Math.min(duration, 600));
            offsetAnimator.setIntValues(new int[]{currentOffset, offset});
            offsetAnimator.start();
        }
    }


    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull SimpleBarLayout child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        return (axes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }


    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull SimpleBarLayout child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {

    }

    @Override
    public int setHeaderTopBottomOffset(CoordinatorLayout parent, SimpleBarLayout simpleBarLayout, int newOffset, int minOffset, int maxOffset) {

        int curOffset = getTopBottomOffsetForScrollingSibling();
        int consumed = 0;
        if (minOffset != 0 && curOffset >= minOffset && curOffset <= maxOffset) {
            newOffset = MathUtils.clamp(newOffset, minOffset, maxOffset);
            if (curOffset != newOffset) {
                int interpolatedOffset = newOffset;
                if (simpleBarLayout.hasChildWidthInterpolator()) {
                    interpolatedOffset = interpolateOffset(simpleBarLayout, newOffset);
                } else {
                    interpolatedOffset = newOffset;
                }
                boolean offsetChanged = setTopAndBottomOffset(interpolatedOffset);
                if (!offsetChanged && simpleBarLayout.hasChildWidthInterpolator()) {
                    parent.dispatchDependentViewsChanged(simpleBarLayout);
                }

                //回调给外界的,暂时不实现
                simpleBarLayout.dispatchOffsetUpdaates(getTopAndBottomOffset());

                updateSimpleBarLayoutDrawableState(parent, simpleBarLayout, newOffset, newOffset < curOffset ? -1 : 1, false)
            }
        } else {
            offsetDelta = 0;
        }

        return consumed;
    }


    /**
     * scrollFlags = [0x1,bar_scroll
     * 0x2,bar_exitUntilCollapsed
     * 0x4 ,bar_exitUntilCollapsed
     * 0x8,bar_enterAlwaysCollapsed
     * 0x10,bar_snap]
     * <p>
     * 0x1 & 1 = 1
     * 0x2 & 1 = 0
     * 0x4 & 1 = 0
     * 0x8 & 1 = 0
     * 0x10 & 1 = 0
     * <p>
     * 0x1 & 2 = 0
     * 0x2 & 2 = 2
     * 0x4 & 2 = 0
     * 0x8 & 2 = 0
     * 0x10 & 2 = 2
     *
     * @param simpleBarLayout
     * @param offset
     * @return
     */
    private int interpolateOffset(SimpleBarLayout simpleBarLayout, int offset) {
        int simpleBarLayoutOffset = Math.abs(offset);
        int count = simpleBarLayout.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = simpleBarLayout.getChildAt(i);
            SimpleBarParams lp = (SimpleBarParams) child.getLayoutParams();
            Interpolator interpolator = lp.getScrollInterpolator();
            if (simpleBarLayoutOffset >= child.getTop() && simpleBarLayoutOffset <= child.getBottom()) {
                if (interpolator != null) {
                    int childScrollableHeight = 0;
                    int flags = lp.getScrollFlags();
                    if ((flags & 1) != 0) {//bar_scroll
                        childScrollableHeight = childScrollableHeight + child.getHeight() + lp.topMargin + lp.bottomMargin;
                        if ((flags & 2) != 0) {//bar_exitUntilCollapsed |bar_snap
                            childScrollableHeight = childScrollableHeight - ViewCompat.getMinimumHeight(child);
                        }
                    }

                    if (ViewCompat.getFitsSystemWindows(child)) {
                        childScrollableHeight = childScrollableHeight - simpleBarLayout.getTopInset();
                    }
                    if (childScrollableHeight > 0) {
                        int offsetForView = simpleBarLayoutOffset - child.getTop();
                        float interpolatorValue = interpolator.getInterpolation((float) offsetForView / (float) childScrollableHeight);
                        int interpolatedDiff = Math.round(1.0f * childScrollableHeight * interpolatorValue);
                        return Integer.signum(offset) * (child.getTop() + interpolatedDiff);
                    }

                }
                break;
            }
        }
        return offset;
    }


    private void updateSimpleBarLayoutDrawableState(CoordinatorLayout parent, SimpleBarLayout simpleBarLayout, int offset, int direction, boolean forceJump) {

        View child = getSimpleBarLayoutChildOnOffset(simpleBarLayout, offset);
        if (child != null) {
            SimpleBarParams lp = (SimpleBarParams) child.getLayoutParams();
            int flags = lp.getScrollFlags();
            boolean lifted = false;
            if ((flags & 1) != 0) {
                int minHeight = ViewCompat.getMinimumHeight(child);
                if (direction > 0 && (flags & 12) != 0) {
                    int temp = child.getBottom() - minHeight - simpleBarLayout.getTopInset();
                    if (-offset >= temp) {
                        lifted = true;
                    } else {
                        lifted = false;
                    }
                } else if ((flags & 2) != 0) {
                    int temp = child.getBottom() - minHeight - simpleBarLayout.getTopInset();
                    if (-offset >= temp) {
                        lifted = true;
                    } else {
                        lifted = false;
                    }
                }
            }


            if (simpleBarLayout.isLiftOnScroll()) {
                View scrollingChild = findFirstScrollingChild(parent);
                if (scrollingChild != null) {
                    lifted = scrollingChild.getScaleY() > 0;
                }
            }
            boolean changed = simpleBarLayout.setLiftableState(lifted);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB && (forceJump || changed && shouldJumpElevatioState(parent, simpleBarLayout))) {
                simpleBarLayout.jumpDrawablesToCurrentState();
            }

        }
    }


    private boolean shouldJumpElevatioState(CoordinatorLayout parent, SimpleBarLayout simpleBarLayout) {

        List<View> dependencies = parent.getDependencies(simpleBarLayout);
        int count = dependencies.size();
        for (int i = 0; i < count; i++) {
            View dependencityView = dependencies.get(i);
            CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) dependencityView.getLayoutParams();
            CoordinatorLayout.Behavior behavior = lp.getBehavior();
            if (behavior instanceof NestedScrollBehavior) {
                NestedScrollBehavior scrollBehavior = (NestedScrollBehavior) behavior;
                return scrollBehavior.getOverlayTop() != 0;
            }
        }
        return false;

    }


    private static View getSimpleBarLayoutChildOnOffset(SimpleBarLayout layout, int offset) {
        int sblOffset = Math.abs(offset);
        int count = layout.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = layout.getChildAt(i);
            if (sblOffset >= child.getTop() && sblOffset <= child.getBottom()) {
                return child;
            }
        }
        return null;
    }


    /**
     * 得到滚动距离
     *
     * @return
     */
    private int getTopBottomOffsetForScrollingSibling() {
        return getTopAndBottomOffset() + offsetDelta;
    }

    private View findFirstScrollingChild(CoordinatorLayout parent) {
        int count = parent.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = parent.getChildAt(i);
            if (child instanceof NestedScrollingChild) {
                return child;
            }
        }
        return null;
    }
}
