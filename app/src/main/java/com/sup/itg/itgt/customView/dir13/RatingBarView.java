package com.sup.itg.itgt.customView.dir13;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.sup.itg.base.ItgL;
import com.sup.itg.itgt.R;

public class RatingBarView extends View {
    private int mRatNum = 0;
    private int mGrade = 0;
    private Bitmap mZeroGradeBitmap;
    private Bitmap mGradeBitmap;
    private int mCurrentClickGrade;
    private int mPreClickGrade;

    public RatingBarView(Context context) {
        this(context, null);
    }

    public RatingBarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RatingBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.RatingBarView);
        mRatNum = array.getInt(R.styleable.RatingBarView_rbRatingNum, mRatNum);
        mGrade = array.getInt(R.styleable.RatingBarView_rbGrade, mGrade);
        int zeroGradeIcon = array.getResourceId(R.styleable.RatingBarView_rbZeroGradeIcon, 0);
        int gradeIcon = array.getResourceId(R.styleable.RatingBarView_rbGradeIcon, 0);
        array.recycle();
        mZeroGradeBitmap = BitmapFactory.decodeResource(getResources(),zeroGradeIcon);
        mGradeBitmap = BitmapFactory.decodeResource(getResources(),gradeIcon);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int iconWidth = mZeroGradeBitmap.getWidth();
        int iconHeight = mZeroGradeBitmap.getHeight();

        width = mRatNum *(iconWidth+getPaddingLeft()+getPaddingRight());
        height = iconHeight+getPaddingTop()+getPaddingBottom();

        setMeasuredDimension(width,height);

    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int x = 0;
        for (int i =0;i<mRatNum;i++){
            x = getPaddingLeft()+(mZeroGradeBitmap.getWidth()+getPaddingRight()+getPaddingLeft())*i;
            if (mCurrentClickGrade<=i) {
                canvas.drawBitmap(mZeroGradeBitmap, x, getPaddingTop(), null);
            }else {
                canvas.drawBitmap(mGradeBitmap,x,getPaddingTop(),null);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
                float x = event.getX();
                mCurrentClickGrade = (int) (x/(mZeroGradeBitmap.getWidth()+getPaddingRight()+getPaddingRight())+1);
                if (mCurrentClickGrade<0){
                    mCurrentClickGrade = 0;
                }
                if (mCurrentClickGrade>mRatNum){
                    mCurrentClickGrade = mRatNum;
                }
                break;
        }
        ItgL.e(event.getAction()+"");


        if (mCurrentClickGrade == mPreClickGrade){
            return true;
        }
        mPreClickGrade = mCurrentClickGrade;
        invalidate();
        return true;
    }
}
