package com.sup.itg.itgt.dir3;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.sup.itg.itgt.R;

import java.util.Arrays;

public class SortActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mSortBtn;
    private int[] array = {4, 34, 42, 5, 453, 23, 232, 42, 23, 13, 53, 5, 2, 7, 8, 46, 3, 322, 54, 12, 32, 21, 324, 54, 6, 767, 67, 343, 23, 6, 7, 424, 13, 65, 76, 878, 45345, 414, 242, 6546, 786, 8678, 3, 535, 24, 657, 989, 756, 19, 8};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort);
        initView();
    }

    private void initView() {
        mSortBtn = findViewById(R.id.sortBtn);
        mSortBtn.setOnClickListener(this);
    }

    //冒泡排序
    private void bubbleSort(int[] sort) {
        int arr[] = Arrays.copyOf(sort, sort.length);
        for (int i = 0; i < arr.length; i++) {
            for (int j = i+1; j < arr.length; j++) {
                int temp;
                if (arr[i] <= arr[j]) {

                } else {
                    temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
        }
        for (int i : arr) {
            Log.e("sort",i+"");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sortBtn:
                bubbleSort(array);
                break;
        }
    }
}
