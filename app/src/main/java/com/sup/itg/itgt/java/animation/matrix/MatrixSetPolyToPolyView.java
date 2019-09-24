package com.sup.itg.itgt.java.animation.matrix;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.sup.itg.itgt.R;

public class MatrixSetPolyToPolyView extends View {

    private Matrix mMatrix;
    private Bitmap mBitmap;
    private Paint pointPaint;
    private float[] src = new float[8];
    private float[] dst = new float[8];
    private int testPoint = 4;

    public MatrixSetPolyToPolyView(Context context) {
        this(context, null);
    }

    public MatrixSetPolyToPolyView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MatrixSetPolyToPolyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        pointPaint = new Paint();
        pointPaint.setAntiAlias(true);
        pointPaint.setStrokeWidth(50);
        pointPaint.setColor(0xffd19165);
        pointPaint.setStrokeCap(Paint.Cap.ROUND);


        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.guest);
        mMatrix = new Matrix();

        float temp[] = {0, 0
                , mBitmap.getWidth(), 0
                , mBitmap.getWidth(), mBitmap.getHeight()
                , 0, mBitmap.getHeight()};

        src = temp.clone();
        dst = temp.clone();

        mMatrix.setPolyToPoly(src, 0, dst, 0, 4);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mBitmap, mMatrix, pointPaint);
        float[] dst = new float[8];
        mMatrix.mapPoints(dst,src);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            float tempX = event.getX();
            float tempY = event.getY();

            for (int i = 0; i < testPoint * 2; i += 2) {
                if (Math.abs(dst[i] - tempX) < 180 && Math.abs(dst[i + 1]-tempY) < 180) {
                    dst[i] = tempX - 100;
                    dst[i + 1] = tempY - 100;
                }
            }
            resetMatrix();
            invalidate();
        }
        return true;
    }

    private void resetMatrix() {
        mMatrix.reset();
        mMatrix.setPolyToPoly(src, 0, dst, 0, 4);

    }
}
