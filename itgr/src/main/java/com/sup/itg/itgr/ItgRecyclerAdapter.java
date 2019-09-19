package com.sup.itg.itgr;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sup.itg.itgr.help.ViewHelp;
import com.sup.itg.itgr.holder.ItgHolder;

import java.util.ArrayList;
import java.util.List;

public abstract class ItgRecyclerAdapter<D> extends ItgBaseAdapter<D,ItgHolder> {



    public ItgRecyclerAdapter(int resId) {
        mHeaderView = new SparseArray<>();
        mFootView = new SparseArray<>();
        mList = new ArrayList<>();
        mResId = resId;
        mViewHelp = new ViewHelp();
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public ItgHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (isHeaderViewType(viewType)) {
            return createViewHolder(mHeaderView.get(viewType));
        } else if (isFooterViewType(viewType)) {
            return createViewHolder(mFootView.get(viewType));
        }
        mViewHelp.context = parent.getContext();
        ItgHolder itgHolder = new ItgHolder(LayoutInflater.from(mViewHelp.context).inflate(mResId, parent, false));
        mViewHelp.setHolder(itgHolder);
        return itgHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItgHolder holder, int position) {
        if (!isHeader(position) && !isFooter(position)) {
            bind(mList.get(position - getHeadersCount()), mViewHelp);
        }
    }

    public abstract void bind(D data, ViewHelp help);


    @Override
    public int getItemCount() {
        return mAdapter.getItemCount() + mFootView.size() + mHeaderView.size();
    }

    public int getHeadersCount() {
        return mHeaderView.size();
    }

    public int getFootersCount() {
        return mFootView.size();
    }

    public boolean isHeader(int position) {
        if (position < mHeaderView.size()) {
            return true;
        }
        return false;
    }

    public boolean isFooter(int position) {
        int totalNum = mHeaderView.size() + mAdapter.getItemCount() + mFootView.size();
        if (position >= mHeaderView.size() + mAdapter.getItemCount() && position < totalNum) {
            return true;
        }
        return false;
    }

    private boolean isHeaderViewType(int itemType) {
        int position = mHeaderView.indexOfKey(itemType);
        return position >= 0;
    }

    private boolean isFooterViewType(int itemType) {
        return mFootView.indexOfKey(itemType) >= 0;
    }


    public boolean removeHeader(View v) {
        int position = mHeaderView.indexOfValue(v);
        if (position < 0) return false;
        mHeaderView.remove(position);
        notifyItemChanged(position);
        return true;
    }

    public boolean removeFooter(View v) {
        int position = mFootView.indexOfValue(v);
        if (position < 0) return false;
        mFootView.remove(position);
        notifyItemChanged(position);
        return true;
    }


    @Override
    public int getItemViewType(int position) {
        if (isHeader(position)) {
            return mHeaderView.keyAt(position);
        } else if (isFooter(position)) {
            position = position - mHeaderView.size() - mAdapter.getItemCount();
            return mFootView.keyAt(position);
        }
        return super.getItemViewType(position);
    }

    private ItgHolder createViewHolder(View view) {
        return new ItgHolder(view);
    }
}
