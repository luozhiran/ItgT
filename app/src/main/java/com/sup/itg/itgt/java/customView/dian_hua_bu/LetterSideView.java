package com.sup.itg.itgt.java.customView.dian_hua_bu;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.sup.itg.itgt.R;

public class LetterSideView extends View {

    private String[] WORDS = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N",
            "O","P","Q","R","S","T","U","V","W","X","Y","Z","#"};

    private int mTextSize;
    private int mTextColor = Color.parseColor("#6A5ACD");
    private int mLightTextColor = Color.parseColor("#B22222");
    private OnTouchLetterListener mTouchLetterListener;

    private Paint mTextPaint;

    public LetterSideView(Context context) {
        this(context,null);
    }

    public LetterSideView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LetterSideView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(mTextSize);
    }

    private void init(AttributeSet attrs){
        TypedArray array = getContext().obtainStyledAttributes(attrs,R.styleable.LetterSideView);
        mTextSize = (int) array.getDimension(R.styleable.LetterSideView_lsTextSize,
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,18,getResources().getDisplayMetrics()));
        mTextColor = array.getColor(R.styleable.LetterSideView_lsTextColor,mTextColor);
        mLightTextColor = array.getColor(R.styleable.LetterSideView_lsLightTextColor,mLightTextColor);
        array.recycle();


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        float letterWidth = mTextPaint.measureText("A");
        width = (int) (getPaddingLeft()+getPaddingRight()+letterWidth);

        setMeasuredDimension(width,height);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int dy =0;
        Paint.FontMetricsInt fontMetricsInt =null;

        int itemHeight = (getHeight()-getPaddingTop()-getPaddingBottom())/WORDS.length;
        int mCurrentItemLength;
        int x = 0;
        Rect rect = new Rect();
        for (int i=0;i<WORDS.length;i++){
            mCurrentItemLength = i*itemHeight+getPaddingTop()  +itemHeight/2;
            fontMetricsInt = mTextPaint.getFontMetricsInt();
            dy = (fontMetricsInt.bottom-fontMetricsInt.top)/2-fontMetricsInt.bottom;
            mTextPaint.getTextBounds(WORDS[i],0,WORDS[i].length(),rect);
            x = (getWidth() - rect.width())/2;
            if (mCurrentTouchWordPosition == i){
                mTextPaint.setColor(mLightTextColor);
                canvas.drawText(WORDS[i],x,mCurrentItemLength+dy,mTextPaint);
            }else {
                mTextPaint.setColor(mTextColor);
                canvas.drawText(WORDS[i],x,mCurrentItemLength+dy,mTextPaint);

            }
        }
    }


    private int mCurrentTouchWordPosition = 0;
    private String mCurrentTouchWord = "";
    private int mPreTouchWordPosition = 0;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                int itemHeight = (getHeight()-getPaddingTop()-getPaddingBottom())/WORDS.length;
                mCurrentTouchWordPosition = (int) (event.getY() / itemHeight);
                if (mCurrentTouchWordPosition<0){
                    mCurrentTouchWordPosition = 0;
                }
                if (mCurrentTouchWordPosition>WORDS.length-1){
                    mCurrentTouchWordPosition = WORDS.length -1;
                }
                mCurrentTouchWord = WORDS[mCurrentTouchWordPosition];

                if (mPreTouchWordPosition == mCurrentTouchWordPosition){
                    return true;
                }
                if (mTouchLetterListener!=null) {
                    mTouchLetterListener.touchLetter(mCurrentTouchWord);
                }
                mPreTouchWordPosition = mCurrentTouchWordPosition;
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                break;
        }

        return true;
    }

    public interface OnTouchLetterListener{
        void touchLetter(String touch);
    }

    public void addOnTouchLetterListener(OnTouchLetterListener touch){
        mTouchLetterListener = touch;
    }
}
