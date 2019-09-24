package com.sup.itg.itgt.touch.dir2;

import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * 接口，用来监听双击事件。
 */
public class DoubleTapListener implements GestureDetector.OnDoubleTapListener {
    /**
     * 单击事件(onSingleTapConfirmed，onDoubleTap是两个互斥的函数)
     */
    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    /**
     * 双击事件
     */
    @Override
    public boolean onDoubleTap(MotionEvent e) {
        return false;
    }

    /**
     * 双击事件产生之后手指还没有抬起的时候的后续事件
     */
    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }
}
