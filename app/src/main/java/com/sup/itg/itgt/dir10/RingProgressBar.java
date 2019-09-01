package com.sup.itg.itgt.dir10;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.Nullable;

import com.sup.itg.itgt.R;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

public class RingProgressBar extends View {

    private static int DEFAULT_SIZE = 200;

    private int mInColor = Color.parseColor("#FD4C5B");
    private int mOutColor = Color.parseColor("#3F51B5");
    private int mTextColor = Color.parseColor("#FD4C5B");
    private int mTextSize;
    private int mBorderWidth;
    private Paint mInPaint, mOutPaint, mTextPaint;
    private int mProgress = 20;
    private int mMaxProgress = 100;


    public RingProgressBar(Context context) {
        this(context, null);
    }

    public RingProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RingProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
        initPaint();
    }


    private void init(AttributeSet attrs) {
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.RingProgressBar);
        mInColor = array.getColor(R.styleable.RingProgressBar_irInColor, mInColor);
        mOutColor = array.getColor(R.styleable.RingProgressBar_irOutColor, mOutColor);
        mTextSize = (int) array.getDimension(R.styleable.RingProgressBar_irTextSize,
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 18, getResources().getDisplayMetrics()));
        mBorderWidth = array.getDimensionPixelOffset(R.styleable.RingProgressBar_irBorderWidth,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics()));
        mProgress = array.getInt(R.styleable.RingProgressBar_irProgress, mProgress);
        mMaxProgress = array.getInt(R.styleable.RingProgressBar_irMaxProgressValue, mMaxProgress);
        mTextColor = array.getColor(R.styleable.RingProgressBar_irTextColor, mTextColor);
        array.recycle();
    }

    private void initPaint() {
        mOutPaint = new Paint();
        mOutPaint.setAntiAlias(true);
        mOutPaint.setColor(mOutColor);
        mOutPaint.setStyle(Paint.Style.STROKE);
        mOutPaint.setStrokeWidth(mBorderWidth);

        mInPaint = new Paint();
        mInPaint.setAntiAlias(true);
        mInPaint.setColor(mInColor);
        mInPaint.setStyle(Paint.Style.STROKE);
        mInPaint.setStrokeWidth(mBorderWidth);

        mTextPaint = new Paint();
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(mTextSize);

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode == MeasureSpec.AT_MOST) {
            width = Math.min(width, DEFAULT_SIZE);
        }

        if (heightMode == MeasureSpec.AT_MOST) {
            height = Math.min(height, DEFAULT_SIZE);
        }

        width = width - getPaddingLeft() - getPaddingRight();
        height = height - getPaddingTop() - getPaddingBottom();
        setMeasuredDimension(Math.min(width, height), Math.min(width, height));

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        RectF outRectF = new RectF(mBorderWidth / 2, mBorderWidth / 2,
                getWidth() - mBorderWidth / 2, getHeight() - mBorderWidth / 2);
        canvas.drawArc(outRectF, 0, 360, false, mOutPaint);

        RectF inRectF = new RectF(mBorderWidth / 2, mBorderWidth / 2,
                getWidth() - mBorderWidth / 2, getHeight() - mBorderWidth / 2);

        float progress = 1.0f * mProgress / mMaxProgress;

        canvas.drawArc(inRectF, 0, progress * 360, false, mInPaint);

        Rect textRect = new Rect();
        String str = mProgress+"%";
        mTextPaint.getTextBounds(str,0,str.length(),textRect);
        int startX= (getWidth() - textRect.width())/2;
        Paint.FontMetricsInt fontMetricsInt = mTextPaint.getFontMetricsInt();
        int dy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom;
        canvas.drawText(mProgress+"%",startX,getHeight()/2+dy,mTextPaint);

    }


    public void setMaxProgress(int maxProgress) {
        this.mMaxProgress = maxProgress;
    }

    public void setProgress(int progress) {
        this.mProgress = progress;
        invalidate();
    }

}
