package com.sup.itg.itgt.java.customView.bethel.view;

import android.app.MediaRouteActionProvider;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.sup.itg.base.ItgL;

/**
 * 1. 两个圆：
 * <p>
 * 其中一个圆：      位置不动，半径变化；
 * 两圆距离越远，该圆半径越小，距离越近，半径越大；
 * <p>
 * <p>
 * 另一个圆：       半径不变，位置跟随手指移动
 * <p>
 * <p>
 * <p>
 * 2. 两个圆之间有一个不规则图形
 */

public class BethelCircleView extends View {

    /**
     * 构建两个圆的原心
     */
    private PointF mCirclePoint, mDragCirclePoint;
    private Paint mCirclePaint, mDragCirclePaint;

    private final int RADIUS_MAX = 15;

    private final int mDragRadius = 20;


    private float mRadius = RADIUS_MAX;


    public BethelCircleView(Context context) {
        this(context, null);
    }

    public BethelCircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BethelCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mCirclePaint = new Paint();
        mCirclePaint.setColor(Color.RED);
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setStyle(Paint.Style.FILL);

        mDragCirclePaint = new Paint();
        mDragCirclePaint.setColor(Color.BLUE);
        mDragCirclePaint.setAntiAlias(true);
        mDragCirclePaint.setStyle(Paint.Style.FILL);

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mCirclePoint != null) {
            canvas.drawCircle(mCirclePoint.x, mCirclePoint.y, mRadius, mCirclePaint);
            float diff = getOffsetRadius(mCirclePoint,mDragCirclePoint);
            mRadius = RADIUS_MAX - diff /RADIUS_MAX;
        }

        if (mDragCirclePoint != null) {
            canvas.drawCircle(mDragCirclePoint.x, mDragCirclePoint.y, mDragRadius, mDragCirclePaint);
        }

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                float x = event.getX();
                float y = event.getY();
                initPoint(x, y);
                mRadius = RADIUS_MAX;
                break;
            case MotionEvent.ACTION_MOVE:
                x = event.getX();
                y = event.getY();
                updateDragPoint(x, y);
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        invalidate();

        return true;
    }

    private void initPoint(float downX, float downY) {
        mCirclePoint = new PointF(downX, downY);
        mDragCirclePoint = new PointF(downX, downY);
    }

    private void updateDragPoint(float moveX, float moveY) {
        mDragCirclePoint.x = moveX;
        mDragCirclePoint.y = moveY;
    }


    private float getOffsetRadius(PointF circle,PointF dragCircle) {
        return (float) Math.sqrt((dragCircle.x-circle.x)*(dragCircle.x-circle.x)
                +(dragCircle.y-circle.y)*(dragCircle.y-circle.y));
    }
}
