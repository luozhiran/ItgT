package com.sup.itg.itgt.java.animation.matrix;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.sup.itg.base.ItgL;
import com.sup.itg.itgt.R;

import java.util.Arrays;
import java.util.Locale;

public class MatrixActivity extends AppCompatActivity implements View.OnClickListener {

    private View mIvImg;
    private ImageView mIvTurnLeft;
    private ImageView mIvTurnRight;
    private int degree = 0;
    private Bitmap mBitmap;
    private Matrix mMatrix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrx);
        initView();
        mMatrix = new Matrix();
        RectF rect = new RectF(400, 400, 1000, 800);
        mMatrix.setScale(0.5f, 1f);
        mMatrix.preSkew(1,0);
        mMatrix.mapRect(rect);
        ItgL.e(rect.toString());
        ItgL.e(mMatrix.toShortString());
    }

    private void initView() {
        mIvImg = findViewById(R.id.ivImg);
        mIvTurnLeft = findViewById(R.id.ivTurnLeft);
        mIvTurnRight = findViewById(R.id.ivTurnRight);
        mIvTurnRight.setOnClickListener(this);
        mIvTurnLeft.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivTurnLeft:

                break;
            case R.id.ivTurnRight:

                break;
        }


    }


}
