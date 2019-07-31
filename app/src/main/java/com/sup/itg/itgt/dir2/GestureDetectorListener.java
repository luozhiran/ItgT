package com.sup.itg.itgt.dir2;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * 接口，用来监听手势事件(6种)。
 */
public class GestureDetectorListener implements GestureDetector.OnGestureListener {
    @Override
    public boolean onDown(MotionEvent e) {
        //当触碰事件按下时触发的方法；
        Log.i("GestureDetectorListener", "onDown");
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        //当用户在屏幕上按下，而且还未移动和松动的时候触发该方法；
        Log.i("GestureDetectorListener", "onShowPress");
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        //当用户在触摸屏上的轻击事件将会触发该方法；
        Log.i("GestureDetectorListener", "onSingleTapUp");
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        //当用户在屏幕上“滚动”时触发该方法；
        Log.i("GestureDetectorListener", "onScroll" + "  distanceX=" + distanceX + "  distanceY=" + distanceY);
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        //当用户在屏幕上长按时触发该方法；
        Log.i("GestureDetectorListener", "onLongPress");

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        //当用户在触摸屏上”拖过”时触发该方法，velocityX，velocityY代表“拖过”动作的横向、纵向上的速度；
        Log.i("GestureDetectorListener", "onFling" + "  velocityX=" + velocityX + "   velocityY=" + velocityY);
        return false;
    }
}
