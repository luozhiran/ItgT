//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sup.itg.itgt.java.animation.behavior.myappbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;

import com.google.android.material.R.attr;
import com.google.android.material.R.style;
import com.google.android.material.R.styleable;
import com.google.android.material.internal.ThemeEnforcement;
import com.sup.itg.itgt.java.animation.behavior.appbar.AppBarLayout;
import com.sup.itg.itgt.java.animation.behavior.myappbar.bar.AppBarLayoutParams;
import com.sup.itg.itgt.java.animation.behavior.myappbar.bar.Behavior;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.VisibleForTesting;
import androidx.coordinatorlayout.widget.CoordinatorLayout.DefaultBehavior;
import androidx.core.util.ObjectsCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

@SuppressLint("RestrictedApi")
@DefaultBehavior(Behavior.class)
public class ItgAppBarLayout extends LinearLayout {
    static final int PENDING_ACTION_NONE = 0;
    static final int PENDING_ACTION_EXPANDED = 1;
    static final int PENDING_ACTION_COLLAPSED = 2;
    static final int PENDING_ACTION_ANIMATE_ENABLED = 4;
    static final int PENDING_ACTION_FORCE = 8;
    private static final int INVALID_SCROLL_RANGE = -1;
    private int totalScrollRange;
    private int downPreScrollRange;
    private int downScrollRange;
    private boolean haveChildWithInterpolator;
    private int pendingAction;
    private WindowInsetsCompat lastInsets;
    private List<ItgAppBarLayout.BaseOnOffsetChangedListener> listeners;
    private boolean liftableOverride;
    private boolean liftable;
    private boolean lifted;
    private boolean liftOnScroll;
    private int[] tmpStatesArray;

    public ItgAppBarLayout(Context context) {
        this(context, (AttributeSet) null);
    }

    public ItgAppBarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.totalScrollRange = -1;
        this.downPreScrollRange = -1;
        this.downScrollRange = -1;
        this.pendingAction = 0;
        this.setOrientation(LinearLayout.VERTICAL);
        if (VERSION.SDK_INT >= 21) {
            ItgViewUtilsLollipop.setBoundsViewOutlineProvider(this);
            ItgViewUtilsLollipop.setStateListAnimatorFromAttrs(this, attrs, 0, style.Widget_Design_AppBarLayout);
        }

        TypedArray a = ThemeEnforcement.obtainStyledAttributes(context, attrs, styleable.AppBarLayout, 0, style.Widget_Design_AppBarLayout, new int[0]);
        ViewCompat.setBackground(this, a.getDrawable(styleable.AppBarLayout_android_background));
        if (a.hasValue(styleable.AppBarLayout_expanded)) {
            this.setExpanded(a.getBoolean(styleable.AppBarLayout_expanded, false), false, false);
        }

        if (VERSION.SDK_INT >= 21 && a.hasValue(styleable.AppBarLayout_elevation)) {
            ItgViewUtilsLollipop.setDefaultAppBarLayoutStateListAnimator(this, (float) a.getDimensionPixelSize(styleable.AppBarLayout_elevation, 0));
        }

        if (VERSION.SDK_INT >= 26) {
            if (a.hasValue(styleable.AppBarLayout_android_keyboardNavigationCluster)) {
                this.setKeyboardNavigationCluster(a.getBoolean(styleable.AppBarLayout_android_keyboardNavigationCluster, false));
            }

            if (a.hasValue(styleable.AppBarLayout_android_touchscreenBlocksFocus)) {
                this.setTouchscreenBlocksFocus(a.getBoolean(styleable.AppBarLayout_android_touchscreenBlocksFocus, false));
            }
        }

        this.liftOnScroll = a.getBoolean(styleable.AppBarLayout_liftOnScroll, false);
        a.recycle();
        ViewCompat.setOnApplyWindowInsetsListener(this, new androidx.core.view.OnApplyWindowInsetsListener() {
            public WindowInsetsCompat onApplyWindowInsets(View v, WindowInsetsCompat insets) {
                return ItgAppBarLayout.this.onWindowInsetChanged(insets);
            }
        });
    }

    public void addOnOffsetChangedListener(ItgAppBarLayout.BaseOnOffsetChangedListener listener) {
        if (this.listeners == null) {
            this.listeners = new ArrayList();
        }

        if (listener != null && !this.listeners.contains(listener)) {
            this.listeners.add(listener);
        }

    }

    public void addOnOffsetChangedListener(ItgAppBarLayout.OnOffsetChangedListener listener) {
        this.addOnOffsetChangedListener((ItgAppBarLayout.BaseOnOffsetChangedListener) listener);
    }

    public void removeOnOffsetChangedListener(ItgAppBarLayout.BaseOnOffsetChangedListener listener) {
        if (this.listeners != null && listener != null) {
            this.listeners.remove(listener);
        }

    }

    public void removeOnOffsetChangedListener(ItgAppBarLayout.OnOffsetChangedListener listener) {
        this.removeOnOffsetChangedListener((ItgAppBarLayout.BaseOnOffsetChangedListener) listener);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.invalidateScrollRanges();
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        this.invalidateScrollRanges();
        this.haveChildWithInterpolator = false;
        int i = 0;

        for (int z = this.getChildCount(); i < z; ++i) {
            View child = this.getChildAt(i);
            AppBarLayoutParams childLp = (AppBarLayoutParams) child.getLayoutParams();
            Interpolator interpolator = childLp.getScrollInterpolator();
            if (interpolator != null) {
                this.haveChildWithInterpolator = true;
                break;
            }
        }

        if (!this.liftableOverride) {
            this.setLiftableState(this.liftOnScroll || this.hasCollapsibleChild());
        }

    }

    private boolean hasCollapsibleChild() {
        int i = 0;

        for (int z = this.getChildCount(); i < z; ++i) {
            if (((AppBarLayoutParams)this.getChildAt(i).getLayoutParams()).isCollapsible()) {
                return true;
            }
        }

        return false;
    }

    private void invalidateScrollRanges() {
        this.totalScrollRange = -1;
        this.downPreScrollRange = -1;
        this.downScrollRange = -1;
    }

    public void setOrientation(int orientation) {
        if (orientation != 1) {
            throw new IllegalArgumentException("AppBarLayout is always vertical and does not support horizontal orientation");
        } else {
            super.setOrientation(orientation);
        }
    }

    public void setExpanded(boolean expanded) {
        this.setExpanded(expanded, ViewCompat.isLaidOut(this));
    }

    public void setExpanded(boolean expanded, boolean animate) {
        this.setExpanded(expanded, animate, true);
    }

    private void setExpanded(boolean expanded, boolean animate, boolean force) {
        this.pendingAction = (expanded ? 1 : 2) | (animate ? 4 : 0) | (force ? 8 : 0);
        this.requestLayout();
    }

    protected boolean checkLayoutParams(android.view.ViewGroup.LayoutParams p) {
        return p instanceof AppBarLayoutParams;
    }

    protected AppBarLayoutParams generateDefaultLayoutParams() {
        return new AppBarLayoutParams(-1, -2);
    }

    public AppBarLayoutParams generateLayoutParams(AttributeSet attrs) {
        return new AppBarLayoutParams(this.getContext(), attrs);
    }

    protected AppBarLayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams p) {
        if (VERSION.SDK_INT >= 19 && p instanceof LinearLayout.LayoutParams) {
            return new AppBarLayoutParams((LinearLayout.LayoutParams) p);
        } else {
            return p instanceof MarginLayoutParams ? new AppBarLayoutParams((MarginLayoutParams) p) : new AppBarLayoutParams(p);
        }
    }

    public boolean hasChildWithInterpolator() {
        return this.haveChildWithInterpolator;
    }

    public final int getTotalScrollRange() {
        if (this.totalScrollRange != -1) {
            return this.totalScrollRange;
        } else {
            int range = 0;
            int i = 0;

            for (int z = this.getChildCount(); i < z; ++i) {
                View child = this.getChildAt(i);
                AppBarLayoutParams lp = (AppBarLayoutParams) child.getLayoutParams();
                int childHeight = child.getMeasuredHeight();
                int flags = lp.scrollFlags;
                if ((flags & 1) == 0) {
                    break;
                }

                range += childHeight + lp.topMargin + lp.bottomMargin;
                if ((flags & 2) != 0) {
                    range -= ViewCompat.getMinimumHeight(child);
                    break;
                }
            }

            return this.totalScrollRange = Math.max(0, range - this.getTopInset());
        }
    }

    public boolean hasScrollableChildren() {
        return this.getTotalScrollRange() != 0;
    }

    public int getUpNestedPreScrollRange() {
        return this.getTotalScrollRange();
    }

    public int getDownNestedPreScrollRange() {
        if (this.downPreScrollRange != -1) {
            return this.downPreScrollRange;
        } else {
            int range = 0;

            for (int i = this.getChildCount() - 1; i >= 0; --i) {
                View child = this.getChildAt(i);
                AppBarLayoutParams lp = (AppBarLayoutParams) child.getLayoutParams();
                int childHeight = child.getMeasuredHeight();
                int flags = lp.scrollFlags;
                if ((flags & 5) == 5) {
                    range += lp.topMargin + lp.bottomMargin;
                    if ((flags & 8) != 0) {
                        range += ViewCompat.getMinimumHeight(child);
                    } else if ((flags & 2) != 0) {
                        range += childHeight - ViewCompat.getMinimumHeight(child);
                    } else {
                        range += childHeight - this.getTopInset();
                    }
                } else if (range > 0) {
                    break;
                }
            }

            return this.downPreScrollRange = Math.max(0, range);
        }
    }

    public int getDownNestedScrollRange() {
        if (this.downScrollRange != -1) {
            return this.downScrollRange;
        } else {
            int range = 0;
            int i = 0;

            for (int z = this.getChildCount(); i < z; ++i) {
                View child = this.getChildAt(i);
                AppBarLayoutParams lp = (AppBarLayoutParams) child.getLayoutParams();
                int childHeight = child.getMeasuredHeight();
                childHeight += lp.topMargin + lp.bottomMargin;
                int flags = lp.scrollFlags;
                if ((flags & 1) == 0) {
                    break;
                }

                range += childHeight;
                if ((flags & 2) != 0) {
                    range -= ViewCompat.getMinimumHeight(child) + this.getTopInset();
                    break;
                }
            }

            return this.downScrollRange = Math.max(0, range);
        }
    }

    public void dispatchOffsetUpdates(int offset) {
        if (this.listeners != null) {
            int i = 0;

            for (int z = this.listeners.size(); i < z; ++i) {
                ItgAppBarLayout.BaseOnOffsetChangedListener listener = (ItgAppBarLayout.BaseOnOffsetChangedListener) this.listeners.get(i);
                if (listener != null) {
                    listener.onOffsetChanged(this, offset);
                }
            }
        }

    }

    public final int getMinimumHeightForVisibleOverlappingContent() {
        int topInset = this.getTopInset();
        int minHeight = ViewCompat.getMinimumHeight(this);
        if (minHeight != 0) {
            return minHeight * 2 + topInset;
        } else {
            int childCount = this.getChildCount();
            int lastChildMinHeight = childCount >= 1 ? ViewCompat.getMinimumHeight(this.getChildAt(childCount - 1)) : 0;
            return lastChildMinHeight != 0 ? lastChildMinHeight * 2 + topInset : this.getHeight() / 3;
        }
    }

    protected int[] onCreateDrawableState(int extraSpace) {
        if (this.tmpStatesArray == null) {
            this.tmpStatesArray = new int[4];
        }

        int[] extraStates = this.tmpStatesArray;
        int[] states = super.onCreateDrawableState(extraSpace + extraStates.length);
        extraStates[0] = this.liftable ? attr.state_liftable : -attr.state_liftable;
        extraStates[1] = this.liftable && this.lifted ? attr.state_lifted : -attr.state_lifted;
        extraStates[2] = this.liftable ? attr.state_collapsible : -attr.state_collapsible;
        extraStates[3] = this.liftable && this.lifted ? attr.state_collapsed : -attr.state_collapsed;
        return mergeDrawableStates(states, extraStates);
    }

    public boolean setLiftable(boolean liftable) {
        this.liftableOverride = true;
        return this.setLiftableState(liftable);
    }

    private boolean setLiftableState(boolean liftable) {
        if (this.liftable != liftable) {
            this.liftable = liftable;
            this.refreshDrawableState();
            return true;
        } else {
            return false;
        }
    }

    public boolean setLifted(boolean lifted) {
        return this.setLiftedState(lifted);
    }

   public boolean setLiftedState(boolean lifted) {
        if (this.lifted != lifted) {
            this.lifted = lifted;
            this.refreshDrawableState();
            return true;
        } else {
            return false;
        }
    }

    public void setLiftOnScroll(boolean liftOnScroll) {
        this.liftOnScroll = liftOnScroll;
    }

    public boolean isLiftOnScroll() {
        return this.liftOnScroll;
    }

    /**
     * @deprecated
     */
    @Deprecated
    public void setTargetElevation(float elevation) {
        if (VERSION.SDK_INT >= 21) {
            ItgViewUtilsLollipop.setDefaultAppBarLayoutStateListAnimator(this, elevation);
        }

    }

    /**
     * @deprecated
     */
    @Deprecated
    public float getTargetElevation() {
        return 0.0F;
    }

    public int getPendingAction() {
        return this.pendingAction;
    }

    public void resetPendingAction() {
        this.pendingAction = 0;
    }

    @VisibleForTesting
    public final int getTopInset() {
        return this.lastInsets != null ? this.lastInsets.getSystemWindowInsetTop() : 0;
    }

    WindowInsetsCompat onWindowInsetChanged(WindowInsetsCompat insets) {
        WindowInsetsCompat newInsets = null;
        if (ViewCompat.getFitsSystemWindows(this)) {
            newInsets = insets;
        }

        if (!ObjectsCompat.equals(this.lastInsets, newInsets)) {
            this.lastInsets = newInsets;
            this.invalidateScrollRanges();
        }

        return insets;
    }




    public interface OnOffsetChangedListener extends ItgAppBarLayout.BaseOnOffsetChangedListener<ItgAppBarLayout> {
        void onOffsetChanged(ItgAppBarLayout var1, int var2);
    }

    public interface BaseOnOffsetChangedListener<T extends ItgAppBarLayout> {
        void onOffsetChanged(T var1, int var2);
    }
}
