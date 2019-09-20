package com.sup.itg.itgr.holder;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItgHolder extends RecyclerView.ViewHolder {
    public Context context;
    private SparseArray<View> mViews;

    public ItgHolder(@NonNull View itemView) {
        super(itemView);
    }


    public <V extends View> V getView(int id) {
        if (mViews == null) {
            mViews = new SparseArray<>();
        }
        View view = mViews.get(id);
        if (view == null) {
            view = itemView.findViewById(id);
            mViews.put(id, view);
        }
        return (V) view;
    }
}
