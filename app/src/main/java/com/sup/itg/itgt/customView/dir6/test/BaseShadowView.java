package com.sup.itg.itgt.customView.dir6.test;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.sup.itg.itgt.R;

public class BaseShadowView extends View {
    private static final int DEFAULT_WIDTH = 200;
    private static final int DEFAULT_HEIGHT = 200;

    private float mShadowDx, mShadowDy;
    private float mShadowRadius;
    private int mShadowColor;
    private boolean mOpenShadow;
    private Paint mShadowPaint;

    private Bitmap mBitmap;


    public BaseShadowView(Context context) {
        this(context, null);
    }

    public BaseShadowView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        attrs(attrs);
        if (mOpenShadow) {
            mShadowPaint = new Paint();
            mShadowPaint.setColor(mShadowColor);
            mShadowPaint.setTextSize(dp2px(25));
            mShadowPaint.setStrokeWidth(2);
            mShadowPaint.setAntiAlias(true);
            setLayerType(LAYER_TYPE_SOFTWARE, null);
            mShadowPaint.setShadowLayer(mShadowRadius, mShadowDx, mShadowDy, mShadowColor);
        }else {
            mShadowPaint = new Paint();
            mShadowPaint.setColor(mShadowColor);
            mShadowPaint.setTextSize(dp2px(25));
            mShadowPaint.setStrokeWidth(2);
            mShadowPaint.setAntiAlias(true);
        }
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.guest);
    }

    private void attrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.BaseShadowView);
        mOpenShadow = typedArray.getBoolean(R.styleable.BaseShadowView_topenShadow, false);
        mShadowDx = typedArray.getDimension(R.styleable.BaseShadowView_tshadowDx, dp2px(0));
        mShadowDy = typedArray.getDimension(R.styleable.BaseShadowView_tshadowDy, dp2px(0));
        mShadowRadius = typedArray.getDimension(R.styleable.BaseShadowView_tshadowRadius, dp2px(0));
        mShadowColor = typedArray.getColor(R.styleable.BaseShadowView_tshadowColor, Color.parseColor("#00000000"));
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
        canvas.drawCircle(wHafe, hHafe, 100, mShadowPaint);
        canvas.drawText("demo", 100, 100, mShadowPaint);
        canvas.drawBitmap(mBitmap, null, new RectF(0, 20, 80 + mBitmap.getWidth(), 80 + mBitmap.getHeight()), mShadowPaint);
    }

    private float dp2px(float value) {
        float scale = getResources().getDisplayMetrics().density;
        value = value * scale + 0.5f;
        return value;
    }
}
