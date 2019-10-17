package com.sup.itg.itgt.java.animation.behavior.myappbar.bar;

import com.sup.itg.itgt.java.animation.behavior.myappbar.ItgAppBarLayout;

import androidx.annotation.NonNull;

public abstract class BaseDragCallback<T extends ItgAppBarLayout> {
    public BaseDragCallback() {
    }

    public abstract boolean canDrag(@NonNull T var1);
}
