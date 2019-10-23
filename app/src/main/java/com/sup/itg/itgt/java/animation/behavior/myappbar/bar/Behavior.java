package com.sup.itg.itgt.java.animation.behavior.myappbar.bar;

import android.content.Context;
import android.util.AttributeSet;

import com.sup.itg.itgt.java.animation.behavior.myappbar.ItgAppBarLayout;

public  class Behavior extends BaseBehavior<ItgAppBarLayout> {
    public Behavior() {
    }

    public Behavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public abstract static class DragCallback extends BaseDragCallback<ItgAppBarLayout> {
        public DragCallback() {
        }
    }
}
