package com.sup.itg.itgr;

import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sup.itg.itgr.holder.ItgHolder;

import java.util.ArrayList;
import java.util.List;

public abstract class ItgBaseAdapter<D, H extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<H> {
    private static int BASE_ITEM_TYPE_HEADER = 10000000;
    private static int BASE_ITEM_TYPE_FOOTER = 20000000;

    private SparseArray<View> mHeaderView;
    private SparseArray<View> mFootView;
    protected List<D> mList;
    protected int mResId;

    public ItgBaseAdapter(int resId) {
        mHeaderView = new SparseArray<>();
        mFootView = new SparseArray<>();
        mList = new ArrayList<>();
        mResId = resId;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public H onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (isHeaderViewType(viewType)) {
            return (H) createViewHolder(mHeaderView.get(viewType));
        } else if (isFooterViewType(viewType)) {
            return (H) createViewHolder(mFootView.get(viewType));
        }
        return onCreateViewHolderAbstract(parent, viewType);
    }


    @Override
    public int getItemViewType(int position) {
        if (isHeader(position)) {
            return mHeaderView.keyAt(position);
        } else if (isFooter(position)) {
            position = position - mHeaderView.size() - getItemCount();
            return mFootView.keyAt(position);
        }
        return getItemViewTypeAbstract(position);
    }


    public abstract int getItemViewTypeAbstract(int position);

    public abstract H onCreateViewHolderAbstract(@NonNull ViewGroup parent, int viewType);

    @Override
     public int getItemCount() {
        return mList.size() + mFootView.size() + mHeaderView.size();
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
        int totalNum = mHeaderView.size() + getItemCount() + mFootView.size();
        if (position >= mHeaderView.size() + getItemCount() && position < totalNum) {
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


    private ItgHolder createViewHolder(View view) {
        return new ItgHolder(view);
    }

    public void addData(D d) {
        mList.add(d);
        notifyItemChanged(mList.size() - 1 + getHeadersCount());
    }


    public void addData(int position, D d) {
        if (d != null) {
            mList.add(position, d);
            notifyItemInserted(position + getHeadersCount());
        }
    }

    public void addData(int start, List<D> ds) {
        if (ds != null && ds.size() > 0) {
            mList.addAll(start, ds);
            notifyItemRangeInserted(start, ds.size());
        }
    }


    public void addData(List<? extends D> ds) {
        if (ds != null) {
            int oldPosition = mList.size() + getHeadersCount();
            mList.addAll(ds);
            notifyItemRangeChanged(oldPosition, ds.size());
        }
    }

    public void remove(int position) {
        int count = mList.size();
        if (position >= 0 && position < count) {
            mList.remove(position + getHeadersCount());
            notifyItemRemoved(position + getHeadersCount());
        }
    }

    public void remove(int position, int count) {
        int oldStart = mList.size();
        if (position >= 0 && position < oldStart) {
            int removeCount = 0;
            for (int i = position; i < oldStart; i++) {
                removeCount++;
                if (removeCount <= count) {
                    mList.remove(i);
                }
            }
            notifyItemRangeRemoved(position, removeCount);
        }
    }

    public void replaceData(List<D> ds) {
        mList.clear();
        addData(ds);
    }

    public void replaceData(int position, D d) {
        if (position >= 0 && position < mList.size()) {
            mList.set(position, d);
            notifyItemChanged(position);
        }
    }

    public D getData(int position) {
        if (position >= 0 && position < mList.size()) {
            return mList.get(position);
        } else {
            return null;
        }
    }


}
