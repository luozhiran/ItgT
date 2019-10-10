package com.sup.itg.itgt.java.customView.yinying.test;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.sup.itg.itgt.R;

public class BaseImgMaskFilterView extends View {
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
    private Bitmap mBitmap;
    private Bitmap mNullBitmap;

    public BaseImgMaskFilterView(Context context) {
        this(context, null);
    }

    public BaseImgMaskFilterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        attrs(attrs);

        mMaskFilterPaint = new Paint();
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.matrix);
        mNullBitmap = mBitmap.extractAlpha(); //extractAlpha()新建一张仅具有Alpha值的空白图像,这张图像的颜色，是由canvas.drawBitmap时的画笔指定的。
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
        canvas.drawBitmap(mNullBitmap, null, new Rect(10, 10, 10 + mNullBitmap.getWidth(), 10 + mNullBitmap.getHeight()), mMaskFilterPaint);
        canvas.drawBitmap(mBitmap, null, new Rect((int) (dp2px(30)+mNullBitmap.getWidth()), 10, (int) (dp2px(30)+mNullBitmap.getWidth() + mBitmap.getWidth()), 10 + mBitmap.getHeight()), mMaskFilterPaint);

    }

    private float dp2px(float value) {
        float scale = getResources().getDisplayMetrics().density;
        value = value * scale + 0.5f;
        return value;
    }
}
