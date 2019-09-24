package com.sup.itg.itgt.customView.dir13;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.sup.itg.itgt.R;

import static com.sup.itg.itgt.customView.dir13.ItgHorizontalProgressBar.Cap.*;

public class ItgHorizontalProgressBar extends View {

    private Paint mPaint;
    private Paint mBackgroundPaint;
    private Paint mSpacePaint;
    private int mBarColor = Color.parseColor("#8A2BE2");
    private int mBarBackgroundColor = Color.parseColor("#808080");
    private int mMax = 100;
    private int mProgress = 0;
    private float mSpaceRat = 0;
    private final int MIN_HEIGHT = 20;
    private int DEFAULT_CAP = 1;
    private Cap mCap = ROUND;


    public ItgHorizontalProgressBar(Context context) {
        this(context, null);
    }

    public ItgHorizontalProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ItgHorizontalProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attributeSet) {
        TypedArray ta = getContext().obtainStyledAttributes(attributeSet, R.styleable.ItgHorizontalProgressBar);
        mBarColor = ta.getColor(R.styleable.ItgHorizontalProgressBar_iBarColor, mBarColor);
        mBarBackgroundColor = ta.getColor(R.styleable.ItgHorizontalProgressBar_iBarBackgroundColor, mBarBackgroundColor);
        mMax = ta.getInt(R.styleable.ItgHorizontalProgressBar_iMax, mMax);
        mProgress = ta.getInt(R.styleable.ItgHorizontalProgressBar_iProgress, mProgress);
        int cap = ta.getInt(R.styleable.ItgHorizontalProgressBar_iCap, DEFAULT_CAP);
        if (cap == 0) {
            mCap = RECT;
        } else if (cap == 1) {
            mCap = ROUND;
        }
        ta.recycle();
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(mBarColor);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        mBackgroundPaint = new Paint();
        mBackgroundPaint.setAntiAlias(true);
        mBackgroundPaint.setColor(mBarBackgroundColor);
        mBackgroundPaint.setStrokeCap(Paint.Cap.ROUND);


    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (heightMode == MeasureSpec.AT_MOST) {
            height = MIN_HEIGHT;
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth() - getPaddingLeft() - getPaddingRight();
        int height = getHeight() - getPaddingTop() - getPaddingBottom();
        drawProgress(canvas, width, height, mBackgroundPaint);
        float rat = 1.0f * mProgress / mMax;
        drawProgress(canvas, width * rat, height, mPaint);
        if (mSpacePaint != null) {
            drawProgress(canvas, width * mSpaceRat, height, mSpacePaint);
        }

    }

    private void drawProgress(Canvas canvas, float width, int height, Paint mBackgroundPaint) {
        float rx = 0, ry = 0;
        if (mCap == ROUND) {
            rx = getHeight() / 2f;
            ry = getHeight() / 2f;
        }
        RectF backgroundRect = new RectF();
        backgroundRect.set(getPaddingLeft(), getPaddingTop(), getPaddingLeft() + width, getPaddingTop() + height);
        canvas.drawRoundRect(backgroundRect, rx, ry, mBackgroundPaint);
    }


    public void setProgress(int value) {
        if (mProgress == value) return;
        mProgress = value;
        invalidate();
    }

    public void setMax(int value) {
        mMax = value;
    }

    public void setSpace(int value, int color) {
        if (mSpacePaint == null) {
            mSpacePaint = new Paint();
            mSpacePaint.setAntiAlias(true);
        }
        float rat = 1.0f * value / mMax;
        if (mSpaceRat == rat) return;
        mSpacePaint.setColor(color);
        mSpaceRat = rat;
        invalidate();
    }


    public enum Cap {RECT, ROUND}
}
