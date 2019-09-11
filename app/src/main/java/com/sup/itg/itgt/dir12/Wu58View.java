package com.sup.itg.itgt.dir12;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.Random;

public class Wu58View extends View {

    private static int DEFAULT_SIZE = 80;
    private int COLORS[] = {Color.RED, Color.GREEN, Color.BLUE};
    public enum Shape {CIRCLE, RECT, TRIANGLE}
    private Shape mShape = Shape.CIRCLE;
    private Paint mPaint;

    public Wu58View(Context context) {
        this(context, null);
    }

    public Wu58View(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Wu58View(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(COLORS[0]);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (MeasureSpec.AT_MOST == widthMode) {
            width = Math.min(DEFAULT_SIZE, width);
        }
        if (MeasureSpec.AT_MOST == heightMode) {
            height = Math.min(height, DEFAULT_SIZE);
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        switch (mShape){
            case RECT:
                mPaint.setColor(COLORS[0]);
                drawCicle(canvas, 0, 0, getWidth(), getHeight(), mPaint);
                break;
            case CIRCLE:
                mPaint.setColor(COLORS[1]);
                drawTriangle(canvas, 0, 0, getWidth(), getHeight(), mPaint);

                break;
            case TRIANGLE:
                mPaint.setColor(COLORS[2]);
                drawRect(canvas, 0, 0, getWidth(), getHeight(), mPaint);

                break;
        }

    }


    private void drawRect(Canvas canvas, int startX, int startY, int width, int height, Paint paint) {
        Rect rect = new Rect(startX, startY, width, height);
        canvas.drawRect(rect, paint);
    }

    private void drawTriangle(Canvas canvas, int startX, int startY, int width, int height, Paint paint) {
        Path path = new Path();
        path.moveTo(width / 2, startY);

        path.lineTo(width, (float) (height*Math.sin(Math.toRadians(60))));

        path.lineTo(startX, (float) (height*Math.sin(Math.toRadians(60))));

        path.close();
        canvas.drawPath(path, paint);

    }

    private void drawCicle(Canvas canvas, int startX, int startY, int width, int height, Paint paint) {

        canvas.drawCircle(width / 2, height / 2, Math.min(height / 2, width / 2), paint);
    }

    public void exchange(){
        switch (mShape){
            case RECT:
                mShape = Shape.CIRCLE;
                break;
            case CIRCLE:
                mShape = Shape.TRIANGLE;
                break;
            case TRIANGLE:
                mShape = Shape.RECT;
                break;

        }
        invalidate();
    }
}
