package com.sup.itg.itgt.java.animation.behavior;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.google.android.material.appbar.AppBarLayout;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

public class FooterBehavior2 extends CoordinatorLayout.Behavior<View> {

    public FooterBehavior2() {
    }

    public FooterBehavior2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 决定提供的child是否需要依赖另一个滚动的View
     * 该方法在绘制布局的时候，至少调用一次。如果该方法对传入的child和依赖View返回true，CoordinatorLayout将总是依赖View布局完之后，在布局这个child
     * 和子View的顺序无关。
     * 依赖View的layout或者位置发生改变，则调用{@link #onDependentViewChanged}方法
     *
     * @param parent     传入参数child的父布局
     * @param child      提供Behavior的view
     * @param dependency child依赖的view
     * @return 如果child的layout依赖dependency的layout ，返回true，反之返回false
     */
    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        super.layoutDependsOn(parent, child, dependency);
        return dependency instanceof AppBarLayout;
    }


    /**
     * 传入参数child的依赖布局dependency发生改变调用该方法
     * <p>
     * 不管传入参数dependency的size改变 还是 position outside改变,都会调用该方法。
     * Behavior可以使用这个方法合适的更新child View
     * <p>
     * 传入参数dependency由{@link #layoutDependsOn(CoordinatorLayout, View, View)}或者child的锚点决定。
     * <p>
     * 注意：如果Behavior可以通过这个方法改变child的布局，该方法可以用{@link #onLayoutChild(CoordinatorLayout, View, int)
     * 重建正确的位置
     * <p>
     * 当每个child view都在layout，onDependentViewChanged()无法被调用。
     * 如果Behavior改变传入参数child的size或者position，该方法返回true，默认实现返回false
     *
     * @param parent     childview的父亲
     * @param child      被操纵的view
     * @param dependency 依赖view
     * @return 如果Behavior改变chld的size或者position，返回true，否者false
     */
    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        super.onDependentViewChanged(parent, child, dependency);
        float translationY = Math.abs(dependency.getY());
        child.setTranslationY(translationY);
        return true;
    }
}
