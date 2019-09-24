package com.sup.itg.itgt.customView.dir6;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.sup.itg.itgt.R;

public class ShadowLayout extends FrameLayout {

    public static final int ALL = 1;
    public static final int LEFT = 2;
    public static final int TOP = 3;
    public static final int RIGHT = 4;
    public static final int BOTTOM = 5;

    public static final int SHADOW_OFFSET = 0;

    private float mShadowDx, mShadowDy;
    private float mShadowRadius;
    private int mShadowColor;
    private int mShadowPosition;
    private RectF mRectF;
    private Paint mShadowPaint;

    public ShadowLayout(Context context) {
        this(context, null);
    }

    public ShadowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        attrs(attrs);
        mRectF = new RectF();
        mShadowPaint = new Paint();
        mShadowPaint.setColor(mShadowColor);
        mShadowPaint.setTextSize(dp2px(25));
        mShadowPaint.setStrokeWidth(2);
        mShadowPaint.setAntiAlias(true);
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        mShadowPaint.setShadowLayer(mShadowRadius, mShadowDx, mShadowDy, mShadowColor);
        setWillNotDraw(false);
    }

    private void attrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ShadowLayout);
        mShadowDx = typedArray.getDimension(R.styleable.ShadowLayout_shadowDx, dp2px(0));
        mShadowDy = typedArray.getDimension(R.styleable.ShadowLayout_shadowDy, dp2px(0));
        mShadowRadius = typedArray.getDimension(R.styleable.ShadowLayout_shadowRadius, dp2px(0));
        mShadowColor = typedArray.getColor(R.styleable.ShadowLayout_shadowColor, Color.parseColor("#00000000"));
        mShadowPosition = typedArray.getInt(R.styleable.ShadowLayout_shadowPosition, ALL);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        float rectLeft = 0;
        float rectTop = 0;
        float rectRight = getMeasuredWidth();
        float rectBottom = getMeasuredHeight();

        int paddingLeft = 0;
        int paddingTop = 0;
        int paddingRight = 0;
        int paddingbottom = 0;

        int offset = (int) (SHADOW_OFFSET + mShadowRadius);

        switch (mShadowPosition) {
            case ALL:
                paddingLeft = offset;
                paddingTop = offset;
                paddingRight = offset;
                paddingbottom = offset;
                break;
            case LEFT:
                paddingLeft = offset;
                rectLeft = offset;
                break;
            case TOP:
                rectTop = offset;
                paddingTop = offset;
                break;
            case RIGHT:
                rectRight = getMeasuredWidth() - offset;
                paddingRight = offset;
                break;
            case BOTTOM:
                rectBottom = getMeasuredHeight() - offset;
                paddingbottom = offset;
                break;
        }
        if (mShadowDy != 0.0f) {
            rectBottom = rectBottom - mShadowDy;
            paddingbottom = paddingbottom + (int) mShadowDy;
        }
        if (mShadowDx != 0.0f) {
            rectRight = rectRight - mShadowDx;
            paddingRight = paddingRight + (int) mShadowDx;
        }
        mRectF.left = rectLeft;
        mRectF.top = rectTop;
        mRectF.right = rectRight;
        mRectF.bottom = rectBottom;

        setPadding(paddingLeft, paddingTop, paddingRight, paddingbottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(mRectF, mShadowPaint);
    }

    private float dp2px(float value) {
        float scale = getResources().getDisplayMetrics().density;
        value = value * scale + 0.5f;
        return value;
    }
}
