package com.sup.itg.itgt.dir19;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class InnerView extends View {

    private final int MIN_WIDTH = 200;
    private final int MIN_HEIGHT = 200;

    public InnerView(Context context) {
        this(context, null);
    }

    public InnerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InnerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode == MeasureSpec.AT_MOST) {
            width = Math.min(width, MIN_WIDTH);
        }
        if (heightMode == MeasureSpec.AT_MOST) {
            height = Math.min(height, MIN_HEIGHT);
        }
        setMeasuredDimension(width, height);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
