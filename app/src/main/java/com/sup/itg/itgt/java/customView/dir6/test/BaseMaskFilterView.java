package com.sup.itg.itgt.java.customView.dir6.test;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.sup.itg.itgt.R;

public class BaseMaskFilterView extends View {
    private static final int DEFAULT_WIDTH = 200;
    private static final int DEFAULT_HEIGHT = 200;

    private static final int INNER = 1;
    private static final int SOLID = 2;
    private static final int NORMAL = 3;
    private static final int OUTER = 4;

    private float mMaskRadius;
    private int mBlurType;
    private boolean mOpenMaskFilter;
    private int mMaskColor;

    private Paint mMaskFilterPaint;

    public BaseMaskFilterView(Context context) {
        this(context, null);
    }

    public BaseMaskFilterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        attrs(attrs);

        mMaskFilterPaint = new Paint();
        BlurMaskFilter blurMaskFilter = null;
        if (mOpenMaskFilter) {
            switch (mBlurType) {
                case INNER:
                    blurMaskFilter = new BlurMaskFilter(mMaskRadius, BlurMaskFilter.Blur.INNER);
                    break;
                case SOLID:
                    blurMaskFilter = new BlurMaskFilter(mMaskRadius, BlurMaskFilter.Blur.SOLID);
                    break;
                case NORMAL:
                    blurMaskFilter = new BlurMaskFilter(mMaskRadius, BlurMaskFilter.Blur.NORMAL);
                    break;
                case OUTER:
                    blurMaskFilter = new BlurMaskFilter(mMaskRadius, BlurMaskFilter.Blur.OUTER);
                    break;
                default:
                    blurMaskFilter = new BlurMaskFilter(mMaskRadius, BlurMaskFilter.Blur.INNER);
                    break;
            }
            setLayerType(LAYER_TYPE_SOFTWARE, null);//对单独的View在运行时阶段禁用硬件加速
            mMaskFilterPaint.setColor(mMaskColor);
            mMaskFilterPaint.setStyle(Paint.Style.FILL);
            mMaskFilterPaint.setAntiAlias(true);
            mMaskFilterPaint.setMaskFilter(blurMaskFilter);

        }
    }

    private void attrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.BaseMaskFilterView);
        mOpenMaskFilter = typedArray.getBoolean(R.styleable.BaseMaskFilterView_openMaskFilter, false);
        mMaskRadius = typedArray.getDimension(R.styleable.BaseMaskFilterView_maskRadius, dp2px(0));
        mBlurType = typedArray.getInt(R.styleable.BaseMaskFilterView_maskFilterBlurType, INNER);
        mMaskColor = typedArray.getColor(R.styleable.BaseMaskFilterView_maskColor, Color.parseColor("#00000000"));
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int wSize = MeasureSpec.getSize(widthMeasureSpec);
        int hMode = MeasureSpec.getMode(heightMeasureSpec);
        int hSize = MeasureSpec.getSize(heightMeasureSpec);

        if (wMode == MeasureSpec.EXACTLY) {

        } else {
            wSize = DEFAULT_WIDTH;
        }
        if (hMode == MeasureSpec.EXACTLY) {

        } else {
            hSize = DEFAULT_HEIGHT;
        }
        setMeasuredDimension(wSize, hSize);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int wHafe = getWidth() / 2;
        int hHafe = getHeight() / 2;
        RectF rectF = new RectF(0,0,getWidth(),getHeight());
        canvas.drawRect(rectF,mMaskFilterPaint);
    }

    private float dp2px(float value) {
        float scale = getResources().getDisplayMetrics().density;
        value = value * scale + 0.5f;
        return value;
    }
}
