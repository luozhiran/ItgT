package com.sup.itg.itgt.dir6.test;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.sup.itg.itgt.R;

public class BaseShadowView extends View {

    private float mShadowDx, mShadowDy;
    private float mShadowRadius;
    private int mShadowColor;
    private boolean mOpenShadow;
    private Paint mShadowPaint;

    public BaseShadowView(Context context) {
        this(context, null);
    }

    public BaseShadowView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        attrs(attrs);
        if (mOpenShadow) {
            mShadowPaint = new Paint();
            mShadowPaint.setColor(mShadowColor);
            mShadowPaint.setStrokeWidth(2);
            mShadowPaint.setAntiAlias(true);
            mShadowPaint.setShadowLayer(mShadowRadius, mShadowDx, mShadowDy, mShadowColor);
        }
    }

    private void attrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.BaseShadowView);
        mOpenShadow = typedArray.getBoolean(R.styleable.BaseShadowView_openShadow, false);
        if (mOpenShadow) {
            mShadowDx = typedArray.getDimension(R.styleable.BaseShadowView_shadowDx, dp2px(0));
            mShadowDy = typedArray.getDimension(R.styleable.BaseShadowView_shadowDy, dp2px(0));
            mShadowRadius = typedArray.getDimension(R.styleable.BaseShadowView_shadowRadius, dp2px(0));
            mShadowColor = typedArray.getColor(R.styleable.BaseShadowView_shadowDx, Color.parseColor("#00000000"));
        }
        typedArray.recycle();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int wSize = MeasureSpec.getSize(widthMeasureSpec);
        int hMode = MeasureSpec.getMode(heightMeasureSpec);
        int hSize = MeasureSpec.getSize(heightMeasureSpec);
    }

    private float dp2px(float value) {
        float scale = getResources().getDisplayMetrics().density;
        value = value * scale + 0.5f;
        return value;
    }
}
