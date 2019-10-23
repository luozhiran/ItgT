//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sup.itg.itgt.java.animation.behavior.myappbar;

import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.animation.StateListAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;

import com.google.android.material.R.attr;
import com.google.android.material.R.integer;
import com.google.android.material.internal.ThemeEnforcement;

import androidx.annotation.RequiresApi;

@SuppressLint("RestrictedApi")
@RequiresApi(21)
public class ItgViewUtilsLollipop {
    private static final int[] STATE_LIST_ANIM_ATTRS = new int[]{16843848};

    ItgViewUtilsLollipop() {
    }

    public static void setBoundsViewOutlineProvider(View view) {
        view.setOutlineProvider(ViewOutlineProvider.BOUNDS);
    }

    public static void setStateListAnimatorFromAttrs(View view, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        Context context = view.getContext();
         TypedArray a = ThemeEnforcement.obtainStyledAttributes(context, attrs, STATE_LIST_ANIM_ATTRS, defStyleAttr, defStyleRes, new int[0]);

        try {
            if (a.hasValue(0)) {
                StateListAnimator sla = AnimatorInflater.loadStateListAnimator(context, a.getResourceId(0, 0));
                view.setStateListAnimator(sla);
            }
        } finally {
            a.recycle();
        }

    }

    public static void setDefaultAppBarLayoutStateListAnimator(View view, float elevation) {
        int dur = view.getResources().getInteger(integer.app_bar_elevation_anim_duration);
        StateListAnimator sla = new StateListAnimator();
        sla.addState(new int[]{16842766, attr.state_liftable, -attr.state_lifted}, ObjectAnimator.ofFloat(view, "elevation", new float[]{0.0F}).setDuration((long)dur));
        sla.addState(new int[]{16842766}, ObjectAnimator.ofFloat(view, "elevation", new float[]{elevation}).setDuration((long)dur));
        sla.addState(new int[0], ObjectAnimator.ofFloat(view, "elevation", new float[]{0.0F}).setDuration(0L));
        view.setStateListAnimator(sla);
    }
}
