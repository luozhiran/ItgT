package com.sup.itg.itgt.customView.dir5;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.sup.itg.itgt.R;

import java.util.ArrayList;
import java.util.List;

public class RadarViewbak extends View {

    private int mDefaultSize = 300;
    private float mRadius = 0;
    private int mEdgeNum = 6;//边的数量
    private int mLevel = 3;// 嵌套层数
    private float mCenterX, mCenterY;
    private int mWidth, mHeight;
    private List<Paint> mLevelPaint;
    private int mLevelColor;
    private int mRadiusColor;
    private Paint mRadiusPaint;
    private List<String> mAngelName;
    private Paint mAngeNamePaint;
    private float mAngleTextSize;
    //    private Rect mTextRect;
    private float mTextSpace;


    public RadarViewbak(Context context) {
        this(context, null);
    }

    public RadarViewbak(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mAngelName = new ArrayList<>();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RadarView);
        mEdgeNum = typedArray.getInt(R.styleable.RadarView_edgeNum, 6);
        mLevel = typedArray.getInt(R.styleable.RadarView_level, 4);
        mLevelColor = typedArray.getColor(R.styleable.RadarView_levelColor, getResources().getColor(R.color.teal));
        mRadiusColor = typedArray.getColor(R.styleable.RadarView_radiusLineColor, Color.WHITE);
        mAngleTextSize = typedArray.getDimension(R.styleable.RadarView_angelNameTextSize, diptoPx(16));
        mTextSpace = typedArray.getDimension(R.styleable.RadarView_horizontalTextSpace, 0);
        typedArray.recycle();
        initLevelPoints();
        mRadiusPaint = new Paint();
        mRadiusPaint.setAntiAlias(true);
        mRadiusPaint.setColor(mRadiusColor);

        for (int i = 0; i < mEdgeNum; i++) {
            mAngelName.add("语句语句语句");
        }

        mAngeNamePaint = new Paint();
        mAngeNamePaint.setAntiAlias(true);
        mAngeNamePaint.setColor(Color.BLACK);
        mAngeNamePaint.setTextSize(mAngleTextSize);
//        mTextRect = new Rect();
//        mAngeNamePaint.getTextBounds(mAngelName.get(0), 0, mAngelName.get(0).length(), mTextRect);

        setBackgroundColor(getResources().getColor(R.color.white));

        mDefaultSize = diptoPx(mDefaultSize);
    }

    private int diptoPx(int value) {
        float scale = getResources().getDisplayMetrics().density;
        value = (int) (value * scale + 0.5f);
        return value;
    }

    private void initLevelPoints() {
        mLevelPaint = new ArrayList<>();
        //初始化 N 层多边形画笔
        for (int i = mLevel; i > 0; i--) {
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            int scale = mLevel * 10 / 11;
            if (scale < 1) {
                scale = 1;
            }
            paint.setColor(changeColorAlpha(mLevelColor, (255 / (mLevel + 1) * (mLevel - i - 1) + 255 / scale) % 255));
            //设置实心
            paint.setStyle(Paint.Style.FILL);
            mLevelPaint.add(paint);
        }
    }

    /**
     * 修改颜色透明度
     *
     * @param color
     * @param alpha
     * @return
     */
    public int changeColorAlpha(int color, int alpha) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);

        return Color.argb(alpha, red, green, blue);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int wSize = MeasureSpec.getSize(widthMeasureSpec);
        int hMode = MeasureSpec.getMode(heightMeasureSpec);
        int hSize = MeasureSpec.getSize(heightMeasureSpec);
        int width = 0, height;
        if (wMode == MeasureSpec.EXACTLY) {//match_parent或者具体大小
            width = wSize;
        } else {//wrap_content
            width = Math.min(wSize, mDefaultSize);
        }
        if (hMode == MeasureSpec.EXACTLY) {
            height = hSize;
        } else {
            height = Math.min(hSize, mDefaultSize);
        }
        mWidth = width;
        mHeight = height;


        mCenterX = (mWidth - getPaddingLeft() - getPaddingRight()) / 2f;
        mCenterY = (mHeight - getPaddingTop() - getPaddingBottom()) / 2f;
//        mRadius = Math.min(mCenterX, mCenterY - 2 * mTextRect.height() - mTextSpace);
        float textWidth = mAngeNamePaint.measureText(mAngelName.get(0));
        Paint.FontMetrics fontMetrics = mAngeNamePaint.getFontMetrics();
        float textHeight = fontMetrics.descent - fontMetrics.ascent;
        mRadius = Math.min(mCenterX, mCenterY - 2 * textHeight - mTextSpace);

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawLevel(canvas);
        drawRadiusLine(canvas);
        drawAngelName(canvas);
    }


    private void drawLevel(Canvas canvas) {
        float averageAngle = 360 / mEdgeNum;
        float radius;
        for (int i = 0; i < mLevel; i++) {
            radius = (i + 1) * mRadius / mLevel;
            Path path = generated(averageAngle, radius);
            canvas.drawPath(path, mLevelPaint.get(mLevel - 1 - i));
        }
    }


    /**
     * 定位几何的顶点，通过Path返回
     *
     * @param averageAngle
     * @param radius
     * @return
     */
    private Path generated(float averageAngle, float radius) {
        Path path = new Path();
        float initAngle = averageAngle / 2;
        float nextAngle = 0;
        float x, y;
        for (int i = 0; i < mEdgeNum; i++) {
            nextAngle = initAngle + i * averageAngle;
            x = (float) (mCenterX + radius * Math.sin(Math.toRadians(nextAngle)));
            y = (float) (mCenterY - radius * Math.cos(Math.toRadians(nextAngle)));
            if (i == 0) {
                path.moveTo(x, y);
            } else {
                path.lineTo(x, y);
            }
        }
        path.close();
        return path;
    }

    private void drawRadiusLine(Canvas canvas) {
        float averageAngle = 360 / mEdgeNum;
        float initAngle = averageAngle / 2;
        float nextAngle = 0;
        float x, y;
        int radius = (int) (mLevel * mRadius / mLevel);//最大半径
        for (int i = 0; i < mEdgeNum; i++) {
            nextAngle = initAngle + i * averageAngle;
            x = (float) (mCenterX + radius * Math.sin(Math.toRadians(nextAngle)));
            y = (float) (mCenterY - radius * Math.cos(Math.toRadians(nextAngle)));
            canvas.drawLine(mCenterX, mCenterY, x, y, mRadiusPaint);
        }
    }


    private void drawAngelName(Canvas canvas) {
        float averageAngle = 360 / mEdgeNum;
        float initAngle = averageAngle / 2;
        float nextAngle = 0;
        float x = 0, y = 0;
        int radius = (int) (mLevel * mRadius / mLevel);//最大半径
        for (int i = 0; i < mEdgeNum; i++) {
            nextAngle = initAngle + i * averageAngle;
            String text = mAngelName.get(i);

            float textWidth = mAngeNamePaint.measureText(text);
            Paint.FontMetrics fontMetrics = mAngeNamePaint.getFontMetrics();
            float textHeight = fontMetrics.descent - fontMetrics.ascent;

//            x = (float) (mCenterX + (radius + mTextRect.height() + mTextSpace) * Math.sin(Math.toRadians(nextAngle)) - textWidth / 2);
//            y = (float) (mCenterY - (radius + mTextRect.height() + mTextSpace) * Math.cos(Math.toRadians(nextAngle)) + textHeight / 4);

            x = (float) (mCenterX + (radius + textHeight + mTextSpace) * Math.sin(Math.toRadians(nextAngle)) - textWidth / 2);
            y = (float) (mCenterY - (radius + textHeight + mTextSpace) * Math.cos(Math.toRadians(nextAngle)) + textHeight / 4);
            canvas.drawText(text, x, y, mAngeNamePaint);
        }
    }


}
