package com.sup.itg.itgt.dir9;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class TextView extends View {


    /**
     * 在代码中调用
     * @param context
     */
    public TextView(Context context) {
        super(context);
    }


    /**
     * 在布局layout中使用
     * @param context
     * @param attrs
     * <com.sup.itg.itgt.dir7.TextView
     *    android:layout_height="wrap_content"
     *    android:layout_width="wrap_content"/>
     */
    public TextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    /**
     * 在布局layout中使用，但是会有style
     * @param context
     * @param attrs
     * @param defStyleAttr
     *
     *  <com.sup.itg.itgt.dir7.TextView
     *     android:layout_height="wrap_content"
     *     android:layout_width="wrap_content"
     *     style="@style/default_TextView"/>
     */
    public TextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    /**
     * 自定义view的测量方法
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     *
     * scrollView+listVew 显示不全i
     * ScrollView 和ListView使用UNSPECIFIED
     *
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //布局的宽高由这个方法指定
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY){//match_content

        }if (widthMode == MeasureSpec.AT_MOST){//wrap_content

        }else if (widthMode == MeasureSpec.UNSPECIFIED){

        }
    }
}
