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
import com.sup.itg.itgt.java.animation.matrix.MatrixActivity;
import com.sup.itg.itgt.java.customView.dir13.ProgressActivity;
import com.sup.itg.itgt.java.customView.dir14.ScrollviewActivity;
import com.sup.itg.itgt.java.customView.dir10.ProgressBarActivity;
import com.sup.itg.itgt.java.customView.dir11.HorProgressActivity;
import com.sup.itg.itgt.java.customView.wu58Loading.Wu58Activity;
import com.sup.itg.itgt.java.customView.dir13.RatingActivity;
import com.sup.itg.itgt.java.customView.dir15.LetterSideActivity;
import com.sup.itg.itgt.java.animation.QiCheZhiJia.ViewDrawViewActivity;
import com.sup.itg.itgt.java.customView.dir17.TestItgrActivity;
import com.sup.itg.itgt.java.component.dir18.ManifestActivity;
import com.sup.itg.itgt.java.struct.linkStruct.BaseLinkActivity;
import com.sup.itg.itgt.java.touch.dir19.TouchTestActivity;
import com.sup.itg.itgt.java.touch.dir2.GestureActivity;
import com.sup.itg.itgt.java.struct.sort.SortActivity;
import com.sup.itg.itgt.java.inject.dir4.InjectActivity;
import com.sup.itg.itgt.java.customView.dir5.RadarActivity;
import com.sup.itg.itgt.java.customView.dir6.ShadowActivity;
import com.sup.itg.itgt.java.animation.material.CoordinatorLayoutActivity;
import com.sup.itg.itgt.java.customView.dir8.FileProvideActivity;
import com.sup.itg.itgt.java.customView.dir9.ViewActivity;
import com.sup.itg.itgt.java.mvvm.KMvvmActivity;
import com.sup.itg.itgt.java.mvvm.MvvmActivity;
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
            data.resId = getResources().getIdentifier(data.pic, "mipmap", getPackageName());
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
                    case "radar":
                        gotoActivity(RadarActivity.class);
                        break;
                    case "shadow":
                        gotoActivity(ShadowActivity.class);
                        break;
                    case "coordinator":
                        gotoActivity(CoordinatorLayoutActivity.class);
                        break;
                    case "fileProvider":
                        gotoActivity(FileProvideActivity.class);
                        break;
                    case "textview":
                        gotoActivity(ViewActivity.class);
                        break;
                    case "progressbar":
                        gotoActivity(ProgressActivity.class);
                        break;
                    case "slideMenu":
                        gotoActivity(ScrollviewActivity.class);
                        break;
                    case "Ring":
                        gotoActivity(ProgressBarActivity.class);
                        break;
                    case "hprogress":
                        gotoActivity(HorProgressActivity.class);
                        break;
                    case "58同城":
                        gotoActivity(Wu58Activity.class);
                        break;
                    case "rating":
                        gotoActivity(RatingActivity.class);
                        break;
                    case "letter":
                        gotoActivity(LetterSideActivity.class);
                        break;
                    case "viewDragView":
                        gotoActivity(ViewDrawViewActivity.class);
                        break;
                    case "mvvm":
                        gotoActivity(MvvmActivity.class);
                        break;
                    case "kotmvvm":
                        gotoActivity(KMvvmActivity.class);
                        break;
                    case "touchTest":
                        gotoActivity(TouchTestActivity.class);
                        break;
                    case "itgr":
                        gotoActivity(TestItgrActivity.class);
                        break;
                    case "manifest":
                        gotoActivity(ManifestActivity.class);
                        break;
                    case "链表":
                        gotoActivity(BaseLinkActivity.class);
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
