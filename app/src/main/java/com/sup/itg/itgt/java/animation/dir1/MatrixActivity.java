package com.sup.itg.itgt.java.animation.dir1;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.sup.itg.itgt.R;

public class MatrixActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mIvImg;
    private ImageView mIvTurnLeft;
    private ImageView mIvTurnRight;
    private int degree = 0;
    private Bitmap mBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrx);
        initView();

    }

    private void initView() {
        mIvImg = findViewById(R.id.ivImg);
        mIvTurnLeft = findViewById(R.id.ivTurnLeft);
        mIvTurnRight = findViewById(R.id.ivTurnRight);
        mIvTurnRight.setOnClickListener(this);
        mIvTurnLeft.setOnClickListener(this);
        BitmapDrawable bitmapDrawable = (BitmapDrawable) mIvImg.getDrawable();
        mBitmap = bitmapDrawable.getBitmap();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivTurnLeft:
                degree = degree + 90;
                break;
            case R.id.ivTurnRight:
                degree = degree - 90;
                break;
        }

        mIvImg.setRotation(degree);
    }


}
