package com.sup.itg.itgr;

import android.util.SparseArray;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.sup.itg.itgr.help.ViewHelp;

import java.util.List;

public abstract class ItgBaseAdapter<D,H extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<H>  {
    private static int BASE_ITEM_TYPE_HEADER = 10000000;
    private static int BASE_ITEM_TYPE_FOOTER = 20000000;

    private SparseArray<View> mHeaderView;
    private SparseArray<View> mFootView;
    protected List<D> mList;
    protected int mResId;
    protected ViewHelp mViewHelp;

    public ItgBaseAdapter() {
    }
}
