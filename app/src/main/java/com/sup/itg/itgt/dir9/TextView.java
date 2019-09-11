package com.sup.itg.itgt.dir9;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.sup.itg.itgt.R;

public class TextView extends View {


    private Paint mContentPaint;
    private String mContent;

    /**
     * 在代码中调用
     *
     * @param context
     */
    public TextView(Context context) {
        this(context, null);
    }


    /**
     * 在布局layout中使用
     *
     * @param context
     * @param attrs   <com.sup.itg.itgt.dir7.TextView
     *                android:layout_height="wrap_content"
     *                android:layout_width="wrap_content"/>
     */
    public TextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }


    /**
     * 在布局layout中使用，但是会有style
     *
     * @param context
     * @param attrs
     * @param defStyleAttr <com.sup.itg.itgt.dir7.TextView
     *                     android:layout_height="wrap_content"
     *                     android:layout_width="wrap_content"
     *                     style="@style/default_TextView"/>
     */
    public TextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    private void init(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TextView);
        int color = array.getColor(R.styleable.TextView_iTextColor, Color.parseColor("#000000"));
        float textSize = array.getDimension(R.styleable.TextView_iTextSize, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 18, getResources().getDisplayMetrics()));
        mContent = array.getString(R.styleable.TextView_iText);
        array.recycle();
        mContentPaint = new Paint();
        mContentPaint.setAntiAlias(true);
        mContentPaint.setTextSize(textSize);
        mContentPaint.setColor(color);
    }


    /**
     * 自定义view的测量方法
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec scrollView+listVew 显示不全i
     *                          ScrollView 和ListView使用UNSPECIFIED
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //布局的宽高由这个方法指定
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        Rect contentRect = null;
        if (mContent != null && mContent.length() > 0) {
            contentRect = new Rect();
            mContentPaint.getTextBounds(mContent, 0, mContent.length(), contentRect);
        }


        if (widthMode == MeasureSpec.EXACTLY) {

        } else if (widthMode == MeasureSpec.AT_MOST) {
            width = contentRect.width()+getPaddingLeft()+getPaddingRight();
        }

        if (heightMode == MeasureSpec.EXACTLY) {

        } else if (heightMode == MeasureSpec.AT_MOST) {
            height = contentRect.height()+getPaddingTop()+getPaddingBottom();
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mContent != null && mContent.length() > 0) {
            canvas.drawText(mContent, 0, getHeight() / 2 + getBaseLine(), mContentPaint);
        }
    }

    private int getBaseLine() {
        Paint.FontMetricsInt fontMetrics = mContentPaint.getFontMetricsInt();
        int dy = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
        return dy;
    }
}
