package com.sup.itg.itgt;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.sup.itg.itgt.dir1.MatrixActivity;
import com.sup.itg.itgt.dir2.GestureActivity;
import com.sup.itg.itgt.dir3.SortActivity;
import com.sup.itg.itgt.dir4.InjectActivity;
import com.sup.itg.viewinject.ContentView;
import com.sup.itg.viewinject.ViewInject;
import com.sup.itg.viewinject.ViewInjectUtils;

import java.util.List;

@ContentView(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    private MainAdapter mMainAdapter;
    @ViewInject(R.id.recyclerview)
    private RecyclerView mRecyclerview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewInjectUtils.inject(this);
        initView();
    }

    private void initView() {
        mMainAdapter = new MainAdapter();
        mRecyclerview.setLayoutManager(new GridLayoutManager(this, 4));
        mRecyclerview.setAdapter(mMainAdapter);

        String str = Utils.readAssertDir(this);

        List<HomeData> homeDataList = JSON.parseArray(str, HomeData.class);
        for (HomeData data : homeDataList) {
            switch (data.name) {
                case "Matrix":
                    data.resId = R.mipmap.matrix;
                    break;
                case "Gesture":
                    data.resId = R.mipmap.guest;
                    break;
                case "leetCode":
                    data.resId = R.mipmap.ic_launcher;
                    break;
                case "inject":
                    data.resId = R.mipmap.ic_launcher;
                    break;
            }
        }
        mMainAdapter.addData(homeDataList);
        mMainAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                HomeData data = (HomeData) adapter.getItem(position);
                switch (data.name) {
                    case "Matrix":
                        gotoActivity(MatrixActivity.class);
                        break;
                    case "Gesture":
                        gotoActivity(GestureActivity.class);
                        break;
                    case "leetCode":
                        gotoActivity(SortActivity.class);
                        break;
                    case "inject":
                        gotoActivity(InjectActivity.class);
                        break;
                }
            }
        });
    }


    private void gotoActivity(Class cla) {
        Intent intent = new Intent(this, cla);
        startActivity(intent);
    }
}
