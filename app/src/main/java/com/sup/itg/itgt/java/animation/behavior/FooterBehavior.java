package com.sup.itg.itgt.java.animation.behavior;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewPropertyAnimator;

import com.sup.itg.base.ItgL;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import androidx.interpolator.view.animation.FastOutLinearInInterpolator;


public class FooterBehavior extends CoordinatorLayout.Behavior<View> {

    private int directionChange;
    public FooterBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     *  当CoordinatorLayout的后代尝试启动嵌套滚动时，回调该方法
     *
     * CoordinatorLayout直接后代有关的可以响应该Behavior事件，返回true，表明CoordinatorLayout是该嵌套滚动的父View，这个方法返回true后，这个Behaviors才能接受
     * 后续的嵌套滚动事件
     * @param coordinatorLayout  这个带有Behaviors View 的父布局，
     * @param child  CoordinatorLayout布局的子View，携带Behavior的View
     * @param directTargetChild  CoordinatorLayout布局的子View，这个View本身或者包含嵌套滚动操作
     * @param target CoordinatorLayout布局的子View，这个View发起了嵌套滚动
     * @param axes 嵌套滚动的方向，是竖直还是横向
     * @param type  引起这个嵌套滚动的类型
     * @return  如果Behavior希望接收这个嵌套滚动，则返回true
     */
    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, axes, type);

        boolean result = (axes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
        ItgL.e("onStartNestedScroll  "+result);
         return result;
    }


    /**
     * 发起嵌套滚动的View，进行滑动时，这个方法会回调，嵌套滚动会更新。
     *
     * Behavior 和CoordinatorLayout的直接子View有关联，该直接子View可以选择接收onStartNestedScroll()的部分嵌套滚动，每一个Behavior返回true，那么这个行为可以接收后面的嵌套滚动
     * @param coordinatorLayout
     * @param child
     * @param target
     * @param dx
     * @param dy
     * @param consumed
     * @param type
     */
    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);

        ItgL.e("onNestedPreScroll  "+dy+"  "+consumed[1]);
        if (dy > 0 && directionChange < 0 || dy < 0 && directionChange > 0) {
            child.animate().cancel();
            directionChange = 0;
        }
        directionChange += dy;
        if (directionChange > child.getHeight() && child.getVisibility() == View.VISIBLE) {
            hide(child);
        } else if (directionChange < 0 && child.getVisibility() == View.INVISIBLE) {
            show(child);
        }
    }

    private void hide(final View view) {
        ViewPropertyAnimator viewPropertyAnimator = view.animate()
                .translationY(view.getHeight())
                .setInterpolator(new FastOutLinearInInterpolator())
                .setDuration(200);
        viewPropertyAnimator.setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.INVISIBLE);
            }
        });
        viewPropertyAnimator.start();
    }

    private void show(final View view) {
        ViewPropertyAnimator viewPropertyAnimator = view.animate()
                .translationY(0)
                .setInterpolator(new FastOutLinearInInterpolator())
                .setDuration(200);
        viewPropertyAnimator.setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.VISIBLE);
            }
        });
        viewPropertyAnimator.start();
    }
}
