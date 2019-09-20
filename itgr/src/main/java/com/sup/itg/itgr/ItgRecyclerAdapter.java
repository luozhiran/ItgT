package com.sup.itg.itgr;

import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.sup.itg.itgr.holder.ItgHolder;

public abstract class ItgRecyclerAdapter<D> extends ItgBaseAdapter<D, ItgHolder> {

    public ItgRecyclerAdapter(int resId) {
        super(resId);
    }


    @Override
    public int getItemViewTypeAbstract(int position) {
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public ItgHolder onCreateViewHolderAbstract(@NonNull ViewGroup parent, int viewType) {
        ItgHolder itgHolder = new ItgHolder(wrapContainView(mResId, parent, parent.getContext()));
        return itgHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull ItgHolder holder, int position) {
        if (!isHeader(position) && !isFooter(position)) {
            bind(mList.get(position - getHeadersCount()), holder);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private View wrapContainView(int resId, ViewGroup parent, Context context) {
        RelativeLayout root = new RelativeLayout(context);

        View menuView = LayoutInflater.from(context).inflate(getSlideView(), parent, false);
        View subView = LayoutInflater.from(context).inflate(resId, parent, false);

        RelativeLayout.LayoutParams rootParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, menuView.getLayoutParams().height);
        root.setLayoutParams(rootParams);

        RelativeLayout.LayoutParams menuLayout = new RelativeLayout.LayoutParams(menuView.getLayoutParams());
        menuLayout.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        root.addView(menuView,menuLayout);

        RelativeLayout.LayoutParams subLayout = new RelativeLayout.LayoutParams(subView.getLayoutParams());
        subLayout.addRule(RelativeLayout.CENTER_VERTICAL);
        root.addView(subView,subLayout);

        return root;
    }

    public abstract void bind(D data, ItgHolder help);

    public int getSlideView() {
        return 0;
    }


}
