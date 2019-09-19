package com.sup.itg.itgr.help;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;

import com.sup.itg.itgr.holder.ItgHolder;

public class ViewHelp {
    public View itemView;
    public Context context;
    private ItgHolder holder;
    private SparseArray<View> mViews;

    public void setHolder(ItgHolder holder) {
        this.holder = holder;

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
