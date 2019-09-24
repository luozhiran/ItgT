package com.sup.itg.itgt.touch.dir2;

import android.view.ScaleGestureDetector;

public class ScaleGestureListener implements ScaleGestureDetector.OnScaleGestureListener {

    private ScaleImageView mScaleView;

    public ScaleGestureListener(ScaleImageView view) {
        mScaleView = view;
    }

    /**
     * 缩放进行中，返回值表示是否下次缩放需要重置，如果返回ture，那么detector就会重置缩放事件，如果返回false，detector会在之前的缩放上继续进行计算
     *
     * @param detector
     * @return
     */
    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        return mScaleView.onScale(detector);
    }

    /**
     * 缩放开始，返回值表示是否受理后续的缩放事件
     */
    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return mScaleView.onScaleBegin(detector);
    }

    /**
     * 缩放结束
     */
    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {
        mScaleView.onScaleEnd(detector);
    }
}
