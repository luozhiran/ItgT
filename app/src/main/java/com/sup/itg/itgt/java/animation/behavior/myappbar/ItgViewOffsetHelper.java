//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sup.itg.itgt.java.animation.behavior.myappbar;

import android.view.View;

import androidx.core.view.ViewCompat;

public class ItgViewOffsetHelper {
    private final View view;
    private int layoutTop;
    private int layoutLeft;
    private int offsetTop;
    private int offsetLeft;

    public ItgViewOffsetHelper(View view) {
        this.view = view;
    }

    public void onViewLayout() {
        this.layoutTop = this.view.getTop();
        this.layoutLeft = this.view.getLeft();
        this.updateOffsets();
    }

    private void updateOffsets() {
        ViewCompat.offsetTopAndBottom(this.view, this.offsetTop - (this.view.getTop() - this.layoutTop));
        ViewCompat.offsetLeftAndRight(this.view, this.offsetLeft - (this.view.getLeft() - this.layoutLeft));
    }

    public boolean setTopAndBottomOffset(int offset) {
        if (this.offsetTop != offset) {
            this.offsetTop = offset;
            this.updateOffsets();
            return true;
        } else {
            return false;
        }
    }

    public boolean setLeftAndRightOffset(int offset) {
        if (this.offsetLeft != offset) {
            this.offsetLeft = offset;
            this.updateOffsets();
            return true;
        } else {
            return false;
        }
    }

    public int getTopAndBottomOffset() {
        return this.offsetTop;
    }

    public int getLeftAndRightOffset() {
        return this.offsetLeft;
    }

    public int getLayoutTop() {
        return this.layoutTop;
    }

    public int getLayoutLeft() {
        return this.layoutLeft;
    }
}
