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
import java.util.Random;

public class RadarView extends View {

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
    private float mHorizontalSpace;
    private List<Float> mChartData;
    private boolean mVertex;//箭头向上
    private Paint mChartPaint;
    private Paint mPointPaint;
    private Paint mChartStrokePoint;
    private int mChartFillColor;
    private int mChartStrokeColor;
    private int mMaxProgress;
    private int mPointColor;
    private float mPointRadius;
    private boolean mDebug;


    public RadarView(Context context) {
        this(context, null);
    }

    public RadarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mAngelName = new ArrayList<>();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RadarView);
        mEdgeNum = typedArray.getInt(R.styleable.RadarView_edgeNum, 6);
        mLevel = typedArray.getInt(R.styleable.RadarView_level, 4);
        mLevelColor = typedArray.getColor(R.styleable.RadarView_levelColor, Color.parseColor("#008080"));
        mRadiusColor = typedArray.getColor(R.styleable.RadarView_radiusLineColor, Color.WHITE);
        mAngleTextSize = typedArray.getDimension(R.styleable.RadarView_angelNameTextSize, diptoPx(16));
        mHorizontalSpace = typedArray.getDimension(R.styleable.RadarView_horizontalTextSpace, 0);
        mVertex = typedArray.getBoolean(R.styleable.RadarView_vertex, true);
        mChartFillColor = typedArray.getColor(R.styleable.RadarView_chartFillColor, Color.parseColor("#C71585"));
        mChartStrokeColor = typedArray.getColor(R.styleable.RadarView_chartStrokeColor, Color.parseColor("#000333"));
        mDebug = typedArray.getBoolean(R.styleable.RadarView_debug, false);
        mMaxProgress = typedArray.getInt(R.styleable.RadarView_maxProgress, 100);
        mPointColor = typedArray.getColor(R.styleable.RadarView_pointColor, Color.parseColor("#00FF7F"));
        mPointRadius = typedArray.getDimension(R.styleable.RadarView_pointRadius, diptoPx(2));
        typedArray.recycle();
        initLevelPoints();
        mRadiusPaint = new Paint();
        mRadiusPaint.setAntiAlias(true);
        mRadiusPaint.setColor(mRadiusColor);


        mAngeNamePaint = new Paint();
        mAngeNamePaint.setAntiAlias(true);
        mAngeNamePaint.setColor(Color.BLACK);
        mAngeNamePaint.setTextSize(mAngleTextSize);

//        setBackgroundColor(getResources().getColor(R.color.white));


        mDefaultSize = diptoPx(mDefaultSize);
        mChartPaint = new Paint();
        mChartPaint.setColor(changeColorAlpha(mChartFillColor, 122));
        mChartPaint.setStyle(Paint.Style.FILL);


        mChartStrokePoint = new Paint();
        mChartStrokePoint.setColor(changeColorAlpha(mChartStrokeColor, 122));
        mChartStrokePoint.setStyle(Paint.Style.STROKE);
        mChartStrokePoint.setStrokeWidth(4);

        mPointPaint = new Paint();
        mPointPaint.setColor(changeColorAlpha(mPointColor, 255));
        mPointPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        if (mDebug) {
            for (int i = 0; i < mEdgeNum; i++) {
                mAngelName.add("语句语句");
            }
            mAngelName.set(mEdgeNum - 1, "语句");

            mChartData = new ArrayList<>();
            Random random = new Random();
            for (int i = 0; i < mEdgeNum; i++) {
                mChartData.add(random.nextFloat() * 100);
            }
        } else {
            for (int i = 0; i < mEdgeNum; i++) {
                mAngelName.add(" ");
            }
            mChartData = new ArrayList<>();
            for (int i = 0; i < mEdgeNum; i++) {
                mChartData.add(0f);
            }
        }
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
        float textWidth = mAngeNamePaint.measureText(mAngelName.get(0));
        Paint.FontMetrics fontMetrics = mAngeNamePaint.getFontMetrics();
        float textHeight = fontMetrics.descent - fontMetrics.ascent;
        mRadius = Math.min(mCenterX - textWidth - mHorizontalSpace, mCenterY - textHeight - mHorizontalSpace);

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawLevel(canvas);
        drawRadiusLine(canvas);
        drawAngelName(canvas);
        drawChart(canvas);
    }


    private float getInitAngle(float averageAngle) {
        float initAngle = 0;
        if (mEdgeNum % 2 == 0 && mVertex) {
            initAngle = 0;
        } else if (mEdgeNum % 2 == 0 && !mVertex) {
            initAngle = averageAngle / 2;
        } else {
            initAngle = 0;
        }
        return initAngle;
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
        float initAngle = getInitAngle(averageAngle);
        float nextAngle = 0;
        float x, y;
        for (int i = 0; i < mEdgeNum; i++) {
            nextAngle = initAngle + i * averageAngle;
            x = (float) (getPaddingLeft() + mCenterX + radius * Math.sin(Math.toRadians(nextAngle)));
            y = (float) (getPaddingTop() + mCenterY - radius * Math.cos(Math.toRadians(nextAngle)));
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
        float initAngle = getInitAngle(averageAngle);
        float nextAngle = 0;
        float x, y;
        float radius = mLevel * mRadius / mLevel;//最大半径
        for (int i = 0; i < mEdgeNum; i++) {
            nextAngle = initAngle + i * averageAngle;
            x = (float) (getPaddingLeft()+mCenterX + radius * Math.sin(Math.toRadians(nextAngle)));
            y = (float) (getPaddingTop()+mCenterY - radius * Math.cos(Math.toRadians(nextAngle)));
            canvas.drawLine(mCenterX, mCenterY, x, y, mRadiusPaint);
        }
    }


    private void drawAngelName(Canvas canvas) {
        float averageAngle = 360 / mEdgeNum;
        float initAngle = getInitAngle(averageAngle);
        float nextAngle = 0;
        float x = 0, y = 0;
        int radius = (int) (mLevel * mRadius / mLevel);//最大半径
        for (int i = 0; i < mEdgeNum; i++) {
            nextAngle = initAngle + i * averageAngle;
            String text = mAngelName.get(i);
            float sin = (float) Math.sin(Math.toRadians(nextAngle));
            float cos = (float) Math.cos(Math.toRadians(nextAngle));
            x =getPaddingLeft()+ mCenterX + (radius + mHorizontalSpace) * sin;
            y = getPaddingTop()+mCenterY - (radius + mHorizontalSpace) * cos;

            float textWidth = mAngeNamePaint.measureText(text);
            Paint.FontMetrics fontMetrics = mAngeNamePaint.getFontMetrics();
            float textHeight = fontMetrics.descent - fontMetrics.ascent;

            if (sin > 0) {
                if (cos > 0) {
                    canvas.drawText(text, x + mHorizontalSpace, y + textHeight / 4, mAngeNamePaint);
                } else {
                    canvas.drawText(text, x + mHorizontalSpace, y + textHeight / 4, mAngeNamePaint);
                }
            } else {
                if (cos > 0) {
                    canvas.drawText(text, x - textWidth - mHorizontalSpace, y + textHeight / 4, mAngeNamePaint);
                } else {
                    canvas.drawText(text, x - textWidth - mHorizontalSpace, y + textHeight / 4, mAngeNamePaint);

                }
            }
        }
    }

    private void drawChart(Canvas canvas) {
        if (mChartData != null && !mChartData.isEmpty() && mChartData.size() == mEdgeNum) {
            float averageAngle = 360 / mEdgeNum;
            float initAngle = getInitAngle(averageAngle);
            float nextAngle = 0;
            float x = 0, y = 0;
            float radius = mLevel * mRadius / mLevel;//最大半径
            Path path = new Path();
            float currentRadius = 0;
            float scale = 1;
            float[] xs = new float[mEdgeNum];
            float[] ys = new float[mEdgeNum];
            for (int i = 0; i < mEdgeNum; i++) {
                scale = mChartData.get(i) / mMaxProgress;
                currentRadius = radius * scale;
                nextAngle = initAngle + i * averageAngle;
                x = (float) (getPaddingLeft()+mCenterX + currentRadius * Math.sin(Math.toRadians(nextAngle)));
                y = (float) (getPaddingTop()+mCenterY - currentRadius * Math.cos(Math.toRadians(nextAngle)));
                if (i == 0) {
                    path.moveTo(x, y);
                } else {
                    path.lineTo(x, y);
                }
                xs[i] = x;
                ys[i] = y;

            }
            path.close();
            canvas.drawPath(path, mChartPaint);
            canvas.drawPath(path, mChartStrokePoint);
            for (int i = 0; i < xs.length; i++) {
                canvas.drawCircle(xs[i], ys[i], mPointRadius, mPointPaint);
            }
        }
    }


    public void setList(List<Float> chartData, List<String> names, int edgeNum) {
        if (chartData != null && chartData.size() == edgeNum && names != null && names.size() == edgeNum) {
            mEdgeNum = edgeNum;
            mChartData = chartData;
            mAngelName = names;
            invalidate();
        }
    }


}
