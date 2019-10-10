package com.sup.itg.itgt.java.customView.heng_progress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.Nullable;

import com.sup.itg.itgt.R;

public class HorProgressView extends View {

    private static int DEFALT_MIN_HEIGHT = 10;
    private int mBackgroundColor = Color.parseColor("#696969");
    private int mColor = Color.parseColor("#FF6347");
    private int mTextColor = Color.parseColor("#000000");
    private int mTextSize;
    private boolean mCap;
    private int mMaxProgress = 100;
    private int mProgress = 20;

    private Paint mTextPaint,mBackgoundPaint,mColorPaint;

    public HorProgressView(Context context) {
        this(context,null);
    }

    public HorProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HorProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array =  context.obtainStyledAttributes(attrs,R.styleable.HorProgressView);
        mBackgroundColor = array.getColor(R.styleable.HorProgressView_hpBackground,mBackgroundColor);
        mColor = array.getColor(R.styleable.HorProgressView_hpColor,mColor);
        mTextColor = array.getColor(R.styleable.HorProgressView_hpTextColor,mTextColor);
        mTextSize = (int) array.getDimension(R.styleable.HorProgressView_hpTextSize,
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,18,getResources().getDisplayMetrics()));
        mCap = array.getBoolean(R.styleable.HorProgressView_hpCap,true);
        mMaxProgress = array.getInt(R.styleable.HorProgressView_hpMaxProgress,mMaxProgress);
        mProgress = array.getInt(R.styleable.HorProgressView_hpProgress,mProgress);
        array.recycle();

        mBackgoundPaint = new Paint();
        mBackgoundPaint.setColor(mBackgroundColor);
        mBackgoundPaint.setStrokeCap(Paint.Cap.ROUND);
        mBackgoundPaint.setStrokeJoin(Paint.Join.ROUND);


        mColorPaint = new Paint();
        mColorPaint.setColor(mColor);
        mColorPaint.setStrokeCap(Paint.Cap.ROUND);
        mColorPaint.setStrokeJoin(Paint.Join.ROUND);


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (heightMode == MeasureSpec.AT_MOST){
            height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,10,getResources().getDisplayMetrics());
        }

        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int rx = 0,ry = 0;
        if (mCap){
            ry = rx = getHeight()/2;
        }
        drawProgress(canvas,0,0,getWidth(),getHeight(),rx,ry,mBackgoundPaint);
        float progress = 1.0f*mProgress/mMaxProgress;
        drawProgress(canvas,0,0,(int) (getWidth()*progress), getHeight(),rx,ry,mColorPaint);

    }

    private void drawProgress(Canvas canvas,int startx,int starty,int width,int height,int rx,int xy,Paint paint){
        RectF rect = new RectF(startx,starty,width,height);
        canvas.drawRoundRect(rect,rx,xy,paint);
    }

    public void setMaxProgress(int max){
        mMaxProgress = max;
    }

    public void setProgress(int progress){
        mProgress = progress;
        invalidate();
    }
}
