package com.sup.itg.itgt.java.customView.dir16;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sup.itg.base.ItgHandlerActivity;
import com.sup.itg.itgt.R;

public class ViewDrawViewActivity extends ItgHandlerActivity {


    private RecyclerView mRecyclerview;

    @Override
    public int getLayoutId() {
        return R.layout.activity_view_draw_view;
    }

    @Override
    public void initView() {
        mRecyclerview = findViewById(R.id.recyclerview);
        Adapter adapter = new Adapter();
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerview.setAdapter(adapter);
    }

    @Override
    public void initData() {

    }

    @Override
    public void handleMessage(Message msg) {

    }


    public static class Adapter extends RecyclerView.Adapter<ViewHolder> {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.draw_view_item, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 20;
        }
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
