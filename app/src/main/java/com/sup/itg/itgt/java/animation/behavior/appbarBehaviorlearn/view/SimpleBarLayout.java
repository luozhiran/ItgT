package com.sup.itg.itgt.java.animation.behavior.appbarBehaviorlearn.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;

import com.google.android.material.internal.ThemeEnforcement;
import com.sup.itg.itgt.R;
import com.sup.itg.itgt.java.animation.behavior.appbarBehaviorlearn.util.ViewUtilsLollipop;
import com.sup.itg.itgt.java.animation.behavior.appbarBehaviorlearn.view.bar.SimpleBarParams;
import com.sup.itg.itgt.java.animation.behavior.myappbar.bar.AppBarLayoutParams;

import androidx.annotation.Nullable;
import androidx.core.util.ObjectsCompat;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SimpleBarLayout extends LinearLayout {

    private boolean haveChildWithInterpolator;
    private boolean liftableOverride;
    private boolean liftable;
    private boolean liftOnScroll;
    private int totalScrollRange;//滚动最大范围值
    private int downPreScrollRange;//按下时,上一次的滚动范围值
    private int downScrollRange;//按下时的滚动范围值
    private int pendingAction;//计划行为

    private WindowInsetsCompat lastInsets;

    public SimpleBarLayout(Context context) {
        this(context, null);
    }

    public SimpleBarLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
    }

    @SuppressLint("RestrictedApi")
    public SimpleBarLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        totalScrollRange = -1;
        downPreScrollRange = -1;
        downScrollRange = -1;
        setOrientation(LinearLayout.VERTICAL);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ViewUtilsLollipop.setBoundsViewOutlineProvider(this);
            ViewUtilsLollipop.setStateListAnimatorFromAttrs(this, attrs, 0, R.style.Widget_Design_AppBarLayout);
        }

        TypedArray a = ThemeEnforcement.obtainStyledAttributes(context, attrs, R.styleable.SimpleBarLayout, 0, R.style.Widget_Design_AppBarLayout, new int[0]);

        ViewCompat.setBackground(this, a.getDrawable(R.styleable.SimpleBarLayout_android_background));
        if (a.hasValue(R.styleable.SimpleBarLayout_sbl_expanded)) {
            setExpanded(a.getBoolean(R.styleable.SimpleBarLayout_sbl_expanded, false), false, false);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            float elevation = a.getDimensionPixelSize(R.styleable.SimpleBarLayout_sbl_elevation, 0);
            ViewUtilsLollipop.setDefaultAppBarLayoutStateListAnimator(this, elevation);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (a.hasValue(R.styleable.SimpleBarLayout_android_keyboardNavigationCluster)) {
                boolean isCluster = a.getBoolean(R.styleable.SimpleBarLayout_android_keyboardNavigationCluster, false);
                setKeyboardNavigationCluster(isCluster);
            }

            if (a.hasValue(R.styleable.SimpleBarLayout_android_touchscreenBlocksFocus)) {
                boolean touchscreenBlocksFocus = a.getBoolean(R.styleable.SimpleBarLayout_android_touchscreenBlocksFocus, false);
                setTouchscreenBlocksFocus(touchscreenBlocksFocus);
            }
        }
        liftOnScroll = a.getBoolean(R.styleable.SimpleBarLayout_liftOnScroll, false);
        a.recycle();

        ViewCompat.setOnApplyWindowInsetsListener(this, new androidx.core.view.OnApplyWindowInsetsListener() {
            @Override
            public WindowInsetsCompat onApplyWindowInsets(View v, WindowInsetsCompat insets) {
                return onWindowInsetChanged(insets);
            }
        });

    }


    private WindowInsetsCompat onWindowInsetChanged(WindowInsetsCompat insets) {
        WindowInsetsCompat newInsets = null;
        if (ViewCompat.getFitsSystemWindows(this)) {
            newInsets = insets;
        }
        if (!ObjectsCompat.equals(lastInsets, newInsets)) {
            lastInsets = newInsets;
            invalidateScrollRanges();
        }
        return insets;
    }


    private void invalidateScrollRanges() {
        totalScrollRange = -1;
        downPreScrollRange = -1;
        downScrollRange = -1;
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new SimpleBarParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams lp) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT && lp instanceof LinearLayout.LayoutParams) {
            return new SimpleBarParams((LinearLayout.LayoutParams) lp);
        } else {
            if (lp instanceof MarginLayoutParams) {
                return new SimpleBarParams((MarginLayoutParams) lp);
            } else {
                return new SimpleBarParams(lp);
            }
        }
    }


    public void setOrientation(int orientation) {
        if (orientation != LinearLayout.VERTICAL) {
            throw new IllegalArgumentException("该控件只能竖向滚动,不支持横向滚动");
        } else {
            super.setOrientation(LinearLayout.VERTICAL);
        }
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new SimpleBarParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        invalidateScrollRanges();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        invalidateScrollRanges();
        int count = getChildCount();
        haveChildWithInterpolator = false;
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            SimpleBarParams childLp = (SimpleBarParams) child.getLayoutParams();
            Interpolator interpolator = childLp.getScrollInterpolator();
            if (interpolator != null) {//检测子view中有没有存在带插值器的孩子
                haveChildWithInterpolator = true;
                break;
            }
        }

        if (!liftableOverride) {
            setLiftableState(liftOnScroll || hasCollapsibleChild());
        }

    }


    public boolean hasChildWidthInterpolator() {
        return haveChildWithInterpolator;
    }
    public void resetPendingAction(){
        pendingAction = 0;
    }

    public boolean setLiftable(boolean liftable) {
        liftableOverride = true;
        return setLiftableState(liftable);
    }

    /**
     * 强制刷新drawable 状态,并且回调drawableStateChanged()方法
     *
     * @param liftable
     * @return
     */
    public boolean setLiftableState(boolean liftable) {
        if (this.liftable != liftable) {
            this.liftable = liftable;
            refreshDrawableState();
            return true;
        } else {
            return false;
        }
    }

    /**
     * 检测子view中有没有可以折叠的view
     *
     * @return
     */
    private boolean hasCollapsibleChild() {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            SimpleBarParams lp = (SimpleBarParams) getChildAt(i).getLayoutParams();
            if (lp.isCollapsible()) {
                return true;
            }
        }
        return false;
    }


    /**
     * @param expanded
     * @param animate
     * @param force
     * @return <p>
     * (false,false,false) = 2|0|0 = 2
     * (true,false,false) = 1|0|0 = 1
     * (true,true,false) = 1|4|0=   5
     * (true,true,true) = 1|4|8 = 13
     * </p>
     */
    private void setExpanded(boolean expanded, boolean animate, boolean force) {
        int value1 = expanded ? 1 : 2;
        int value2 = animate ? 4 : 0;
        int value3 = force ? 8 : 0;
        pendingAction = value1 | value2 | value3;
    }


    public int getPendingAction() {
        return pendingAction;
    }

    public final int getTopInset() {
        if (lastInsets != null) {
            int insetTop = lastInsets.getSystemWindowInsetTop();
            return insetTop;
        } else {
            return 0;
        }
    }

    /**
     * 在behavior中调用
     */
    public void dispatchOffsetUpdaates(int offset) {

    }

    public boolean isLiftOnScroll() {
        return liftOnScroll;
    }

    public int getUpNestedPreScrollRange() {
        return getTotalScrollRange();
    }

    public final int getTotalScrollRange() {
        if (totalScrollRange != -1) {
            return totalScrollRange;
        } else {
            int range = 0;
            int count = getChildCount();
            for (int i = 0; i < count; i++) {
                View child = getChildAt(i);
                SimpleBarParams lp = (SimpleBarParams) child.getLayoutParams();
                int childHeight = child.getMeasuredHeight();
                int flags = lp.getScrollFlags();
                if ((flags & 1) == 0) {
                    break;
                }
                range = range + childHeight + lp.topMargin + lp.bottomMargin;
                if ((flags & 2) != 0) {
                    range = range - ViewCompat.getMinimumHeight(child);
                    break;
                }
            }

            return totalScrollRange = Math.max(0, range - getTopInset());
        }
    }

}
